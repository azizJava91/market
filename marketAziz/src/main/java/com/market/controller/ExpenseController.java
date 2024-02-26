package com.market.controller;

import com.market.dto.request.ReqExpense;
import com.market.dto.response.RespExpense;
import com.market.dto.response.Response;
import com.market.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("getExpenceByType")
//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "type": "salary"
//    }
    public Response<List<RespExpense>>getExpenceByType(@RequestBody ReqExpense reqExpense){
        return expenseService.getExpenceByType(reqExpense);
    }
}
