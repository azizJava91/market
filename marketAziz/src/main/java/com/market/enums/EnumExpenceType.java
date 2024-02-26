package com.market.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumExpenceType {
    EMPLOYEE_SALARY_PAY("SALARY"), PURCHASING("PURCHASING"), TAX("TAX");
    public String value;
}
