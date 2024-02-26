package com.market.service;

import com.market.dto.request.ReqCart;
import com.market.dto.response.RespCart;
import com.market.dto.response.Response;

public interface CartService {
    Response<RespCart> createCart(ReqCart reqCart);

    Response<RespCart> getCartById(ReqCart reqCart);
}
