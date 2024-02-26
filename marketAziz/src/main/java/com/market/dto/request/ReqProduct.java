package com.market.dto.request;

import com.market.entity.ProductCategory;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ReqProduct {
    private Long productId;
    private String name;
    private Date expirationDate;
    private String measureUnit;
    private Double totalUnit;
    private Double salePrice;
    private Double purchasePrice;
    private ProductCategory productCategory;
    private ReqToken reqToken;
    private Date manufacturedDate;
    private Date dataDate;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
}

