package com.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqExpense {

    private Long expenceId;
    private String type;
    private Double amount;
    private ReqToken reqToken;
}
