package com.example.abhij.githubnetworking;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by abhij on 17-03-2018.
 */

public interface UserApi {

    @GET("/users")
    Call<ArrayList<User>> getUsersList();


    @GET("/users/{uname}/repos")
    Call<ArrayList<Repo>> getReposList(@Path("uname") String username);

    @GET("/users/{uname}")
    Call<User> getUserInfo(@Path("uname") String username);

    @GET("/users/{uname}/followers")
    Call<ArrayList<User>> getFollowersList(@Path("uname") String username);
}
