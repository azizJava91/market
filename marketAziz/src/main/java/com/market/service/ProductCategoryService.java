package com.market.service;

import com.market.dto.request.ReqProductCategory;
import com.market.dto.response.RespProductCategory;
import com.market.dto.response.Response;

import java.util.List;

public interface ProductCategoryService {
    Response<List<RespProductCategory>> getProductCategoryList(ReqProductCategory reqProductCategory);

    Response create(ReqProductCategory reqProductCategory);

    Response delete(ReqProductCategory reqProductCategory);

    Response update(ReqProductCategory reqProductCategory);
}
