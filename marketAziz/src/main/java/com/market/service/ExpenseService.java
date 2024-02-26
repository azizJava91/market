package com.market.service;

import com.market.dto.request.ReqExpense;
import com.market.dto.response.RespExpense;
import com.market.dto.response.Response;

import java.util.List;

public interface ExpenseService {
    Response<List<RespExpense>> getExpenceByType(ReqExpense reqExpense);

}
