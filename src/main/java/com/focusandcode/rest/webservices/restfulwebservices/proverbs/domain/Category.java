package com.focusandcode.rest.webservices.restfulwebservices.proverbs.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Category {
    private String id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @NotEmpty(message = "The post message cannot be empty.")
    private String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
