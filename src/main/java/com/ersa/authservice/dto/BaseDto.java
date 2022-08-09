package com.ersa.authservice.dto;

import lombok.Data;

@Data
public class BaseDto {

    private int page =1;

    private int pageSize =10;

    private int total;

    private boolean hasNext;

}
