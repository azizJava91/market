package com.market.service;

import com.market.dto.request.ReqProduct;
import com.market.dto.response.RespProduct;
import com.market.dto.response.Response;

import java.util.List;

public interface ProductService {
    Response<RespProduct> getById(ReqProduct reqProduct);

    Response<List<RespProduct>> getProductList(ReqProduct reqProduct);

    Response createProduct(ReqProduct reqProduct);

    Response updateProduct(ReqProduct reqProduct);

    Response deleteProduct(ReqProduct reqProduct);

    Response<List<RespProduct>> productsByCategory(ReqProduct reqProduct);

    Response<List<RespProduct>> productsByBrand(ReqProduct reqProduct);

    Response<List<RespProduct>> productsByPrice(ReqProduct reqProduct);
}
