package com.market.dto.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Data
public class ReqProductCategory {
    private Long categoryId;
    private String name;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;
    private ReqToken reqToken;
}
