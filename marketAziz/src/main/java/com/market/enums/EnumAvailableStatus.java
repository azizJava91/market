package com.market.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumAvailableStatus {

    ACTIVE (1), DEACTIVE  (0);

    public Integer value;

}
