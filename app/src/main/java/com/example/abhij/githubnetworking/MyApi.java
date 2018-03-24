package com.example.abhij.githubnetworking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhij on 19-03-2018.
 */

public class MyApi {

    private static MyApi INSTANCE;

    private UserApi userApi;

    private MyApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);

    }

    public static MyApi getInstance()
    {
        if(INSTANCE==null)
        {
            INSTANCE= new MyApi();
        }

        return INSTANCE;
    }

    public UserApi getUserApi()
    {
        return userApi;
    }

}
