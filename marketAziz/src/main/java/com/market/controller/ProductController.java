package com.market.controller;

import com.market.dto.request.ReqProduct;
import com.market.dto.response.RespProduct;
import com.market.dto.response.Response;
import com.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    }
//    }

    public Response<List<RespProduct>> getProductList(@RequestBody ReqProduct reqProduct) {
        return productService.getProductList(reqProduct);
    }


    @PostMapping("/create")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//            "name": "?????",
//            "expirationDate": "2024-07-09",
//            "measureUnit": "kg",
//            "salePrice": 14,
//            "purchasePrice": 7,
//            "manufacturedDate": "2024-07-09",
//            "brand": "?????",
//            "productCategory": {
//        "categoryId": 1
//    }
//    }

    public Response createProduct(@RequestBody ReqProduct reqProduct) {
        return productService.createProduct(reqProduct);
    }

    @PostMapping("/update")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//            "productId":5,
//            "name": "??????????",
//            "expirationDate": "2024-07-09",
//            "measureUnit": "??????????",
//            "salePrice": 14,
//            "purchasePrice": 7,
//            "manufacturedDate": "2024-07-09",
//            "brand": "????????",
//            "productCategory": {
//        "categoryId": 1
//    }
//    }

    public Response updateProduct(@RequestBody ReqProduct reqProduct) {
        return productService.updateProduct(reqProduct);
    }

    @PostMapping("/delete")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "productId": 5
//    }
    public Response deleteProduct(@RequestBody ReqProduct reqProduct) {
        return productService.deleteProduct(reqProduct);
    }

    @GetMapping("/getById")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "productId": 4
//    }
    public Response<RespProduct> getById(@RequestBody ReqProduct reqProduct) {
        return productService.getById(reqProduct);
    }

    @GetMapping("/getByCategory")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "productCategory": {
//        "categoryId": 2
//    }
//    }

    public Response<List<RespProduct>> productsByCategory(@RequestBody ReqProduct reqProduct) {
        return productService.productsByCategory(reqProduct);
    }

    @GetMapping("getByBrand")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "brand": "goycay"
//    }

    public Response<List<RespProduct>> productsByBrand(@RequestBody ReqProduct reqProduct) {
        return productService.productsByBrand(reqProduct);
    }

    @GetMapping("getByPrice")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//            "name": "nar",
//            "minPrice": 4,
//            "maxPrice": 13
//    }

    public Response<List<RespProduct>> productsByPrice(@RequestBody ReqProduct reqProduct) {
        return productService.productsByPrice(reqProduct);
    }

}
