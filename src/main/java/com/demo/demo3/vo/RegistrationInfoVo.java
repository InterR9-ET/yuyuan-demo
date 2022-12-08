package com.demo.demo3.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegistrationInfoVo {

    private Integer id;

    private String name;

    private Integer age;
    @NotNull
    private Integer height;
}
