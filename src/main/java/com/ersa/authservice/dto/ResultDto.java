package com.ersa.authservice.dto;

public class ResultDto {
    private Object result;
    private String info;
    private String error;

    public static ResultDto build(){
        return new ResultDto();
    }

    public Object getResult() {
        return result;
    }

    public ResultDto setResult(Object result) {
        this.result = result;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public ResultDto setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getError() {
        return error;
    }

    public ResultDto setError(String error) {
        this.error = error;
        return this;
    }

}
