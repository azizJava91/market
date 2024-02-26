package com.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCartProductDetails {
    private String name;
    private Double salePrice;
    private String brand;
    private String totalUnit;
    private Double totalPrice;
    private String category;
}
