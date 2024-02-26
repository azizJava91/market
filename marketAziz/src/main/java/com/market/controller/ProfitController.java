package com.market.controller;

import com.market.dto.request.ReqProfit;
import com.market.dto.response.Response;
import com.market.service.ProfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profit")
@RequiredArgsConstructor
public class ProfitController {

    private final ProfitService profitService;

//   userToken.getUser().getRole() must be ACCOUNTANT or BOSS
//
//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    }
//    }

    @GetMapping("totalProfit")
    public Response totalProfit(@RequestBody ReqProfit reqProfit){
        return profitService.totalProfit(reqProfit);
    }

}
