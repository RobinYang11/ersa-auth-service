package com.ersa.authservice.dto;

import lombok.Data;

@Data
public class ResultDto {
    private Object result;
    private String info;
    private String error;
}
