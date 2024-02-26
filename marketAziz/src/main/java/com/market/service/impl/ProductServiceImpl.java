package com.market.service.impl;

import com.market.dto.request.ReqProduct;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespProduct;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.Product;
import com.market.entity.ProductCategory;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.ProductCategoryRepository;
import com.market.repository.ProductRepository;
import com.market.service.ProductService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final Utility utility;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public Response<List<RespProduct>> getProductList(ReqProduct reqProduct) {
        Response<List<RespProduct>> response = new Response();

        try {
            ReqToken reqToken = reqProduct.getReqToken();
            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.BOSS.value.toLowerCase(),
                    EnumUserRoles.EXECUTIVE.value.toLowerCase()).contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }

            List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (productList.isEmpty()) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }

            List<RespProduct> products = productList.stream().map(this::convert).toList();
            response.setT(products);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    public Response createProduct(ReqProduct reqProduct) {
        Response response = new Response();

        try {
            ReqToken reqToken = reqProduct.getReqToken();
            String name = reqProduct.getName();
            ProductCategory productCategory = reqProduct.getProductCategory();
            Double salePrice = reqProduct.getSalePrice();
            if (reqToken == null || name == null || productCategory == null || salePrice == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }

            Product product = Product.builder()
                    .name(name)
                    .expirationDate(reqProduct.getExpirationDate())
                    .measureUnit(reqProduct.getMeasureUnit())
                    .salePrice(salePrice)
                    .purchasePrice(reqProduct.getPurchasePrice())
                    .manufacturedDate(reqProduct.getManufacturedDate())
                    .productCategory(productCategory)
                    .brand(reqProduct.getBrand())
                    .build();
            productRepository.save(product);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    public Response updateProduct(ReqProduct reqProduct) {
        Response response = new Response();

        try {
            ReqToken reqToken = reqProduct.getReqToken();
            Long productId = reqProduct.getProductId();
            ProductCategory productCategory = reqProduct.getProductCategory();
            if (reqToken == null || productId == null || productCategory == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            productCategory = productCategoryRepository.findProductCategoryByCategoryIdAndActive
                    (reqProduct.getProductCategory().getCategoryId(), EnumAvailableStatus.ACTIVE.value);
            if (productCategory == null) {
                throw new MarketException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }

            Product product = productRepository.findProductByProductIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if (product == null) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }


            product.setName(reqProduct.getName());
            product.setProductCategory(reqProduct.getProductCategory());
            product.setExpirationDate(reqProduct.getExpirationDate());
            product.setManufacturedDate(reqProduct.getManufacturedDate());
            product.setPurchasePrice(reqProduct.getPurchasePrice());
            product.setMeasureUnit(reqProduct.getMeasureUnit());
            product.setSalePrice(reqProduct.getSalePrice());
            product.setBrand(reqProduct.getBrand());
            productRepository.save(product);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    public Response deleteProduct(ReqProduct reqProduct) {
        Response response = new Response();

        try {
            ReqToken reqToken = reqProduct.getReqToken();
            Long productId = reqProduct.getProductId();
            if (reqToken == null || productId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase()).contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }

            Product product = productRepository.findProductByProductIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if (product == null) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }

            product.setActive(EnumAvailableStatus.DEACTIVE.value);
            productRepository.save(product);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    public Response<RespProduct> getById(ReqProduct reqProduct) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqProduct.getReqToken();
            Long productId = reqProduct.getProductId();
            if (reqToken == null || productId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(),
                    EnumUserRoles.BOSS.value.toLowerCase()).contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Product product = productRepository.findProductByProductIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if (product == null) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            RespProduct respProduct = RespProduct.builder()
                    .name(product.getName())
                    .expirationDate(product.getExpirationDate())
                    .measureUnit(product.getMeasureUnit())
                    .salePrice(product.getSalePrice())
                    .productCategory(product.getProductCategory().getName())
                    .manufacturedDate(product.getManufacturedDate())
                    .dataDate(product.getDataDate())
                    .brand(product.getBrand())
                    .build();
            response.setT(respProduct);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    public Response<List<RespProduct>> productsByCategory(ReqProduct reqProduct) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqProduct.getReqToken();
            ProductCategory productCategory = reqProduct.getProductCategory();
            if (reqToken == null || productCategory == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.CUSTOMER.value.toLowerCase(),
                    EnumUserRoles.BOSS.value.toLowerCase()).contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            productCategory = productCategoryRepository.findProductCategoryByCategoryIdAndActive
                    (reqProduct.getProductCategory().getCategoryId(), EnumAvailableStatus.ACTIVE.value);
            if (productCategory == null) {
                throw new MarketException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            List<Product> productList = productRepository.findAllByProductCategoryAndActive(productCategory, EnumAvailableStatus.ACTIVE.value);
            if (productList.isEmpty()) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            List<RespProduct> respProducts = productList.stream().map(this::convert).toList();
            response.setT(respProducts);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    public Response<List<RespProduct>> productsByPrice(ReqProduct reqProduct) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqProduct.getReqToken();
            Double minPrice = reqProduct.getMinPrice();
            Double maxPrice = reqProduct.getMaxPrice();
            String name = reqProduct.getName();
            if (reqToken == null || minPrice == null || maxPrice == null || name == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.CUSTOMER.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            List<Product> productList = productRepository.findAllByNameAndActive(name, EnumAvailableStatus.ACTIVE.value);
            if (productList.isEmpty()) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            List<RespProduct> products = productList.stream().filter(product -> product.getSalePrice() <= maxPrice
                    && product.getSalePrice() >= minPrice).map(this::convert).toList();
            if (products.isEmpty()) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            response.setT(products);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    public Response<List<RespProduct>> productsByBrand(ReqProduct reqProduct) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqProduct.getReqToken();
            String brand = reqProduct.getBrand();
            if (reqToken == null || brand == null || brand.isEmpty()) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.EXECUTIVE.value.toLowerCase(), EnumUserRoles.CUSTOMER.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            List<Product> productList = productRepository.findAllByBrandAndActive(brand, EnumAvailableStatus.ACTIVE.value);
            if (productList.isEmpty()) {
                throw new MarketException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            List<RespProduct> respProducts = productList.stream().map(this::convert).toList();
            response.setT(respProducts);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException ma) {
            ma.printStackTrace();
            response.setRespons_Status(new RespStatus(ma.getCode(), ma.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    private RespProduct convert(Product product) {
        return RespProduct.builder()
                .name(product.getName())
                .productCategory(product.getProductCategory().getName())
                .expirationDate(product.getExpirationDate())
                .measureUnit(product.getMeasureUnit())
                .salePrice(product.getSalePrice())
                .manufacturedDate(product.getManufacturedDate())
                .dataDate(product.getDataDate())
                .brand(product.getBrand())
                .build();
    }
}
