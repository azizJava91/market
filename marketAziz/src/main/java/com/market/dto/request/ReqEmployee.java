package com.market.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqEmployee {
    private Long employeeId;
    private String name;
    private String surname;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date dob;
    private String phone;
    private String pin;
    private Double salary;
    private String seria;
    private String position;
    private Double balance;
    private Department department;
    private ReqToken reqToken;
}
