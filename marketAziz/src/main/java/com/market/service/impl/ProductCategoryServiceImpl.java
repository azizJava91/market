package com.market.service.impl;

import com.market.dto.request.ReqProductCategory;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespProductCategory;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.ProductCategory;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.ProductCategoryRepository;
import com.market.service.ProductCategoryService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final Utility utility;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Response<List<RespProductCategory>> getProductCategoryList(ReqProductCategory reqProductCategory) {
        Response<List<RespProductCategory>> response = new Response<>();
        try {
            ReqToken reqToken = reqProductCategory.getReqToken();
            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.EXECUTIVE.value.toLowerCase().contains(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You are not authorized this service");
            }
            List<ProductCategory> productCategories = productCategoryRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (productCategories.isEmpty()) {
                throw new MarketException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            List<RespProductCategory> categories = productCategories.stream().map(this::convert).toList();
            response.setT(categories);
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
    public Response create(ReqProductCategory reqProductCategory) {
        Response response = new Response();

        try {
            ReqToken reqToken = reqProductCategory.getReqToken();
            String name = reqProductCategory.getName();
            if (reqToken == null || name == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.EXECUTIVE.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You are not authorized this service");
            }
            List<ProductCategory> productCategorys = productCategoryRepository.findAllByNameAndActive(name, EnumAvailableStatus.ACTIVE.value);
            if (!productCategorys.isEmpty()) {
                throw new MarketException(ExceptionConstants.CATEGORY_ALREADY_EXISTS, "Category already exists");
            }
            ProductCategory productCategory = ProductCategory.builder()
                    .name(reqProductCategory.getName())
                    .dataDate(reqProductCategory.getDataDate())
                    .build();
            productCategoryRepository.save(productCategory);
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
    public Response delete(ReqProductCategory reqProductCategory) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqProductCategory.getReqToken();
            Long categoryId = reqProductCategory.getCategoryId();
            if (reqToken == null || categoryId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.EXECUTIVE.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            ProductCategory productCategory = productCategoryRepository.findProductCategoryByCategoryIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.value);
            if (productCategory == null) {
                throw new MarketException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            productCategory.setActive(EnumAvailableStatus.DEACTIVE.value);
            productCategoryRepository.save(productCategory);
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
    public Response update(ReqProductCategory reqProductCategory) {
        Response response = new Response();

        try {
            ReqToken reqToken = reqProductCategory.getReqToken();
            String name = reqProductCategory.getName();
            Long categoryId = reqProductCategory.getCategoryId();
            if (reqToken == null || name == null || categoryId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.EXECUTIVE.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            ProductCategory productCategory = productCategoryRepository.
                    findProductCategoryByCategoryIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.value);
            if (productCategory == null) {
                throw new MarketException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            productCategory.setName(reqProductCategory.getName());
            productCategoryRepository.save(productCategory);
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


    private RespProductCategory convert(ProductCategory productCategory) {
        return RespProductCategory.builder()
                .categoryId(productCategory.getCategoryId())
                .name(productCategory.getName())
                .dataDate(productCategory.getDataDate())
                .build();
    }
}
