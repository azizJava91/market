package com.market.controller;

import com.market.dto.request.ReqCart;
import com.market.dto.response.RespCart;
import com.market.dto.response.Response;
import com.market.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/create")

//    {
//        "products": [
//        {
//            "productId": 1,
//            "totalUnit": 1
//        },
//        {
//            "productId": 2,
//             "totalUnit": 1
//        },
//        {
//            "productId": 3,
//            "totalUnit": 1
//        }
//    ],
//                "reqToken": {
//                "userId": ??,
//                "token": "??????????????????????????/"
//    }
//    }
    public Response<RespCart> createCart(@RequestBody ReqCart reqCart){
        return cartService.createCart(reqCart);
    }

    @GetMapping("/getById")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "???????????????????"
//    },
//        "cartId": ??
//    }
//
    public Response<RespCart> getCartById(@RequestBody ReqCart reqCart){
        return cartService.getCartById(reqCart);
    }

}
