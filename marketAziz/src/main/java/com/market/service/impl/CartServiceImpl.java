package com.market.service.impl;

import com.market.dto.request.ReqCart;
import com.market.dto.request.ReqCartProduct;
import com.market.dto.request.ReqToken;
import com.market.dto.response.*;
import com.market.dto.response.RespCartProductDetails;
import com.market.entity.*;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumBalanceConstantId;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.*;
import com.market.service.CartService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final Utility utility;
    private final CartRepository cartRepository;
    private final ProfitRepository profitRepository;
    private final ProductRepository productRepository;
    private final CartProductDetailRepository cartProductDetailRepository;
    private final CustomerRepository customerRepository;
    private final MarketTotalBalanceRepository marketTotalBalanceRepository;


    /**
     *
     * @param reqCart
     *
     * @return respCart
     *
     */
    @Override
    public Response<RespCart> createCart(ReqCart reqCart) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqCart.getReqToken();

            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (userToken == null) {
                throw new MarketException(ExceptionConstants.USER_TOKEN_NOT_FOUND, "Token expired or deactive");
            }
            List<ReqCartProduct> reqCartProducts = reqCart.getProducts();


            if (reqCartProducts == null) {
                throw new MarketException(ExceptionConstants.CART_IS_EMPTY, "Cart is empty");
            }



            List<Product> products = reqCartProducts.stream()
                    .map(product -> productRepository.findProductByProductIdAndActive(product.getProductId(),
                            EnumAvailableStatus.ACTIVE.value))
                    .filter(Objects::nonNull)
                    .toList();

            ProductPriceDTO productPriceDTO = reqCartProducts.stream().map(request -> {
                        Product product = productRepository.findProductByProductIdAndActive(
                                request.getProductId(),
                                EnumAvailableStatus.ACTIVE.value
                        );
                        if (product == null) {
                            throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found with id:  " + request.getProductId());
                        }

                        if (request.getTotalUnit() == null) {
                            throw new MarketException(ExceptionConstants.TOTAL_UNIT_IS_REQUIRED, "Unit is required");
                        }

                        Double salePrice = request.getTotalUnit() * product.getSalePrice();
                        Double purchasePrice = request.getTotalUnit() * product.getPurchasePrice();
                        return new ProductPriceDTO(salePrice, purchasePrice);
                    })
                    .reduce(new ProductPriceDTO(0.0, 0.0), (subtotal, element) ->
                            new ProductPriceDTO(subtotal.getSalePrice() + element.getSalePrice(),
                                    subtotal.getPurchasePrice() + element.getPurchasePrice()));


            List<CartProductDetail> cartProductDetails = new ArrayList<>();
            List<RespCartProductDetails> respCartProductDetailsList = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                CartProductDetail cartProductDetail = convertRespCartProduct(products.get(i), reqCartProducts.get(i));
                cartProductDetails.add(cartProductDetail);
                cartProductDetailRepository.save(cartProductDetail);

                RespCartProductDetails respCartProductDetails = convertDetails(cartProductDetail);
                respCartProductDetailsList.add(respCartProductDetails);
            }
            Customer customer = customerRepository.findByUserAndActive(userToken.getUser(), EnumAvailableStatus.ACTIVE.value);


            Cart cart = new Cart();
            cart.setCartProductDetails(cartProductDetails);
            cart.setTotalSalePrice(productPriceDTO.getSalePrice());
            cart.setTotalPurchasePrice(productPriceDTO.getPurchasePrice());
            cart.setProfit(productPriceDTO.getSalePrice() - productPriceDTO.getPurchasePrice());
            cart.setCartProductDetails(cartProductDetails);
            cart.setUser(userToken.getUser());
            cart.setCustomer(customer);

            cartRepository.save(cart);


            if (customer.getBalance() < cart.getTotalSalePrice()) {

                throw new MarketException(ExceptionConstants.BALANCE_NOT_ENOUGHT, "Balance not enoght");
            }

            customer.setBalance(customer.getBalance() - cart.getTotalSalePrice());
            System.err.println("balans" + customer.getBalance());
            System.err.println("sebet" + cart.getTotalSalePrice());
            customerRepository.save(customer);


            Profit profit = Profit.builder()
                    .cartId(cart.getCartId())
                    .amount(cart.getProfit())
                    .userId(userToken.getUser().getUserId())
                    .build();

            profitRepository.save(profit);
            System.err.println(profit);

            MarketTotalBalance marketTotalBalance = marketTotalBalanceRepository.findByIdAndActive(EnumBalanceConstantId.ID_CONSTANT.value,
                    EnumAvailableStatus.ACTIVE.value);
            marketTotalBalance.setTotalBalance(marketTotalBalance.getTotalBalance() + cart.getTotalSalePrice());
            marketTotalBalanceRepository.save(marketTotalBalance);

            RespCart respCart = RespCart.builder()
                    .respCartProductDetailsList(respCartProductDetailsList)
                    .userName(cart.getUser().getFullName())
                    .totalPrice(cart.getTotalSalePrice())
                    .balance(customer.getBalance())
                    .build();


            response.setT(respCart);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCart> getCartById(ReqCart reqCart) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqCart.getReqToken();
            Long cartId = reqCart.getCartId();
            if (reqToken == null || cartId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (userToken == null) {
                throw new MarketException(ExceptionConstants.USER_TOKEN_NOT_FOUND, "Token expired or deactive");
            }
            Cart cart = cartRepository.findCartByCartIdAndActive(cartId, EnumAvailableStatus.ACTIVE.value);
            if (cart == null) {
                throw new MarketException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            Customer customer = customerRepository.findByUserAndActive(userToken.getUser(),EnumAvailableStatus.ACTIVE.value);
            if (customer==null){
                throw new MarketException(ExceptionConstants.ACCOUNT_NOT_MATCH_CUSTOMER,"Account not match customer");
            }
            List<RespCartProductDetails> respCartProductDetailsList = cart.getCartProductDetails().stream().map(this::convertDetails).toList();

            RespCart respCart = RespCart.builder()
                    .respCartProductDetailsList(respCartProductDetailsList)
                    .userName(cart.getUser().getFullName())
                    .totalPrice(cart.getTotalSalePrice())
                    .balance(cart.getCustomer().getBalance())
                    .build();
            response.setT(respCart);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    private CartProductDetail convertRespCartProduct(Product product, ReqCartProduct reqProduct) {

        return CartProductDetail.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .salePrice(product.getSalePrice())
                .totalUnit(reqProduct.getTotalUnit() + " " + product.getMeasureUnit())
                .totalPrice(product.getSalePrice() * reqProduct.getTotalUnit())
                .category(product.getProductCategory().getName())
                .build();
    }

    private RespCartProductDetails convertDetails(CartProductDetail cartProductDetail) {
        return RespCartProductDetails.builder()
                .name(cartProductDetail.getName())
                .brand(cartProductDetail.getBrand())
                .salePrice(cartProductDetail.getSalePrice())
                .totalUnit(cartProductDetail.getTotalUnit())
                .totalPrice(cartProductDetail.getTotalPrice())
                .category(cartProductDetail.getCategory())
                .build();
    }

}