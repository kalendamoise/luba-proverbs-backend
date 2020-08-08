package com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String categoryId;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @NotEmpty(message = "The post message cannot be empty.")
    private String name;


    public Category(String categoryId, String name, Set<Proverb> proverbs) {
        this.categoryId = categoryId;
        this.name = name;
        //this.proverbs = proverbs;
    }

    public Category() {
        // Needed for JPA
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String id) {
        this.categoryId = id;
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
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                //", proverbs=" + proverbs +
                '}';
    }
}
