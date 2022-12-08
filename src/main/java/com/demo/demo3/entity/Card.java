package com.demo.demo3.entity;

import lombok.Data;

/**
 * 卡券实体类
 */
@Data
public class Card {
    /**
     * 卡券id
     */
    private Integer id;
    /**
     * 卡券名称
     */
    private String name;

    public Card() {
    }

    public Card(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
