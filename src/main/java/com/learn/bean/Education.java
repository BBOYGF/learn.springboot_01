package com.learn.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Education {
    private int id;
    private String name;

    public Education(int i, String name) {
        this.id=i;
        this.name=name;
    }

}
