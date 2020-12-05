package com.learn.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String password;
    private int age;
    private int sex;
    private int education;
    public int id;
    private String birthday;
}
