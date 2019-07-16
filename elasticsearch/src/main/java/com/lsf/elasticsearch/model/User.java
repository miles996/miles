package com.lsf.elasticsearch.model;

import org.springframework.stereotype.Component;

import java.util.List;

public class User {
    private int id;
    private String name;
    int age;
    List<phone> phones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<phone> getPhones() {
        return phones;
    }

    public void setPhones(List<phone> phones) {
        this.phones = phones;
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {
        super();
    }
}
