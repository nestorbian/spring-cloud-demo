package com.nestor.springcloudnacoshystrixconsumer.enums;

public enum InfoStatus {
    SUCCESS(1), FAIL(0);
    private int code;

    InfoStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
