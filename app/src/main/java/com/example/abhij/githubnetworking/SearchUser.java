package com.example.abhij.githubnetworking;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchUser extends AppCompatActivity {


    EditText editText_userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);


        editText_userName  = findViewById(R.id.editText_userName);





    }

    public void search(View view) {

        String userName= editText_userName.getText().toString();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UserApi userApi = retrofit.create(UserApi.class);

        UserApi userApi =MyApi.getInstance().getUserApi();
        Call<User> call =userApi.getUserInfo(userName);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user= response.body();
                Gson gson = new Gson();
                String json = gson.toJson(user);

                Intent intent = new Intent(editText_userName.getContext(),UserProfile.class);
                intent.putExtra("UserDetails",json);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Snackbar.make(editText_userName,"Wrong UserName",Snackbar.LENGTH_SHORT).show();
            }
        });


    }
}
