package com.market.controller;

import com.market.dto.request.ReqCustomer;
import com.market.dto.response.RespCart;
import com.market.dto.response.Response;
import com.market.entity.Cart;
import com.market.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping ("/createProfile")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "?????????????????????"
//    },
//            "name": "?????",
//            "surname": "??????",
//            "address": "?????",
//            "phone": "????????????",
//            "balance": ?????????
//    }

    public Response createProfile(@RequestBody ReqCustomer reqCustomer){
        return customerService.createProfile(reqCustomer);
    }

    @GetMapping("/getAllCarts")

//    {
//        "customerId": 1,
//            "reqToken": {
//        "userId": ?,
//                "token": "???????????????????"
//    }
//    }

    public Response<List<RespCart>>getAllCarts(@RequestBody ReqCustomer reqCustomer){
        return customerService.getAllCarts(reqCustomer);
    }
}
