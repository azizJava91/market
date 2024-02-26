package com.market.enums;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumUserRoles {

    BOSS("BOSS"),
    HR("HR"),
    EXECUTIVE("EXECUTIVE"),
    ACCAUNTANT("ACCAUNTANT"),
    PROVIDER("PROVIDER"),
    CASHIER("CASHIER"),
    CUSTOMER("CUSTOMER");
    public String value;
}
