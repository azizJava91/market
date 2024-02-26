package com.market.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class RespCart {
    @JsonProperty("Cart")
    private List<RespCartProductDetails>respCartProductDetailsList;
    private Double totalPrice;
    @JsonProperty("Customer")
    private String userName;
    private Double balance;
}
