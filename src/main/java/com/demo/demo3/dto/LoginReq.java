package com.demo.demo3.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginReq {
    /**
     * 校验码
     */
    private String content;

}
