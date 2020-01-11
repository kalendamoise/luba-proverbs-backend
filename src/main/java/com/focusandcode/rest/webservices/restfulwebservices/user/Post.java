package com.focusandcode.rest.webservices.restfulwebservices.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class Post {
    private Integer id;
    @NotNull(message = "the User ID cannot be null.")
    private Integer userId;
    @NotEmpty(message = "The post message cannot be empty.")
    private String message;
    @Past(message = "Date need to be in the past.")
    private Date date;

    public Post(Integer id, Integer userId, String message, Date date) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + userId +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
