package com.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCustomer {
    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Double balance;
    private ReqToken reqToken;
}
