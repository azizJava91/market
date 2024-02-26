package com.market.service;

import com.market.dto.request.ReqProfit;
import com.market.dto.response.Response;

public interface ProfitService {
    Response totalProfit(ReqProfit reqProfit);
}
