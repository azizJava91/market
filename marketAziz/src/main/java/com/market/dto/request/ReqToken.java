package com.market.dto.request;

import lombok.Data;

@Data
public class ReqToken {
    private Long userId;
    private String token;
}