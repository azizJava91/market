package com.market.exception;

public class MarketException extends RuntimeException {
    private Integer code;

    public MarketException(String message) {
        super(message);
    }

    public MarketException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {

        return code;
    }
}
