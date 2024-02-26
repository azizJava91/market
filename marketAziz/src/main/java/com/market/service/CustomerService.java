package com.market.service;

import com.market.dto.request.ReqCustomer;
import com.market.dto.response.RespCart;
import com.market.dto.response.Response;
import com.market.entity.Cart;

import java.util.List;

public interface CustomerService {


    Response createProfile(ReqCustomer reqCustomer);

    Response<List<RespCart>> getAllCarts(ReqCustomer reqCustomer);
}
