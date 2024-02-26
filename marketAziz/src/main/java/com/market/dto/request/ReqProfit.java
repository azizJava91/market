package com.market.dto.request;

import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Builder
@Data
public class ReqProfit {
    private Long profitId;
    private Double amount;
    private Date dataDate;
    private Integer active;
    private Long cartId;
    private Long userId;
    private ReqToken reqToken;
}
