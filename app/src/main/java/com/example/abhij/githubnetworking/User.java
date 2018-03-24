package com.example.abhij.githubnetworking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhij on 17-03-2018.
 */

public class User {

    @SerializedName("login")
    String username;
    String id;
    String avatar_url;
    String name;
    String company;
    String blog;
    private String email;

    public User(String username, String id, String name, String company, String blog, String email, int bio) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.company = company;
        this.blog = blog;
        this.email = email;

    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getEmail() {
        return email;
    }


}
