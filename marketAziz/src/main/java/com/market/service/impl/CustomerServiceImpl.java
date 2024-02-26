package com.market.service.impl;

import com.market.dto.request.ReqCustomer;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespCart;
import com.market.dto.response.RespCartProductDetails;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.*;
import com.market.enums.EnumAvailableStatus;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.CartRepository;
import com.market.repository.CustomerRepository;
import com.market.service.CustomerService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Utility utility;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Override
    public Response createProfile(ReqCustomer reqCustomer) {
        Response response = new Response();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            String address = reqCustomer.getAddress();
            String phone = reqCustomer.getPhone();
            Double balance = reqCustomer.getBalance();
            ReqToken reqToken = reqCustomer.getReqToken();
            if (name == null || surname == null || address == null || phone == null || balance == null || reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!(userToken.getUser().getRole().equalsIgnoreCase("customer"))) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized");
            }
            Customer customer = customerRepository.findByUserAndActive(userToken.getUser(), EnumAvailableStatus.ACTIVE.value);
            System.err.println("user "+userToken.getUser());
            System.err.println("customer "+customer);
            if (customer != null) {
                throw new MarketException(ExceptionConstants.YOU_ALREADY_HAVE_ACCOUNT, "You already have account");
            }
           Customer customer1 = Customer.builder()
                    .name(name)
                    .surname(surname)
                    .phone(phone)
                    .address(address)
                    .balance(balance)
                    .user(userToken.getUser())
                    .build();

            customerRepository.save(customer1);

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
    public Response<List<RespCart>> getAllCarts(ReqCustomer reqCustomer) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqCustomer.getReqToken();
            Long customerId = reqCustomer.getCustomerId();
            if (reqToken == null || customerId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);

            if (customer == null) {
                throw new MarketException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            if (!customer.getUser().getUserId().equals(reqToken.getUserId())) {
                throw new MarketException(ExceptionConstants.ACCOUNT_NOT_MATCH_CUSTOMER, "Account not match customer");
            }

            List<Cart> cartList = cartRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
            if (cartList.isEmpty()) {
                throw new MarketException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<RespCart> respCartList = cartList.stream().map(this::convert).toList();

            response.setT(respCartList);
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

    private RespCart convert(Cart cart) {
        return RespCart.builder()
                .userName(cart.getUser().getFullName())
                .respCartProductDetailsList(cart.getCartProductDetails().stream().map(this::convertRespcart).toList())
                .totalPrice(cart.getTotalSalePrice())
                .build();
    }

    private RespCartProductDetails convertRespcart(CartProductDetail cartProductDetail) {
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
