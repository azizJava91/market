package com.market.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumTokenAvailableStatus {
    DEACTIVE(0), ACTIVE(1),EXPIRED(2);

    public Integer value;
}
