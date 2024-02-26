package com.market.controller;

import com.market.dto.request.ReqProductCategory;
import com.market.dto.response.RespProductCategory;
import com.market.dto.response.Response;
import com.market.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/list")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    }
//    }
    public Response<List<RespProductCategory>> getProductCategoryList(@RequestBody ReqProductCategory reqProductCategory) {
        return productCategoryService.getProductCategoryList(reqProductCategory);
    }

    @PostMapping("/create")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "name":"??????????"
//    }
    public Response create(@RequestBody ReqProductCategory reqProductCategory) {
        return productCategoryService.create(reqProductCategory);
    }

    @PostMapping("/delete")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "categoryId": 2
//    }
    public Response delete(@RequestBody ReqProductCategory reqProductCategory) {
        return productCategoryService.delete(reqProductCategory);
    }

    @PostMapping("/update")
//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "categoryId": ??,
//        "name": "????"
//    }

    public Response update(@RequestBody ReqProductCategory reqProductCategory) {
        return productCategoryService.update(reqProductCategory);
    }


}
