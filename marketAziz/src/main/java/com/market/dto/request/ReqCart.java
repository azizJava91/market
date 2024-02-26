package com.market.dto.request;

import com.market.entity.Product;
import com.market.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCart {
    private Long cartId;
    private ReqToken reqToken;
    private List<ReqCartProduct> products;


}
