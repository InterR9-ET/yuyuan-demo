package com.demo.demo3.entity;

import com.demo.demo3.annotation.SecurityParameter;
import lombok.Data;

@Data
//@AllArgsConstructor
@SecurityParameter
public class UserEntity {

    private String content;

    private Integer id;

    private String name;

    private Integer age;
}
