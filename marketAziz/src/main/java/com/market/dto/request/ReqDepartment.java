package com.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDepartment {
    private Long departmentId;
    private String name;
    private Date dataDate;
    private ReqToken reqToken;
}