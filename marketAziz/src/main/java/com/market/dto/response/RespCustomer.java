package com.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCustomer {
    private Long id;
    private String fullname;
    List<RespCart> respCartList;
    private Double balance;
}
