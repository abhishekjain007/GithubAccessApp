package com.example.abhij.githubnetworking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class UserProfile extends AppCompatActivity implements Callback<ArrayList<Repo>>{

    ImageView avatar ;
    TextView name;
    TextView username;
    TextView email;

    ArrayList<Repo> repos_List;
    ArrayList<String> repos_Name_List;
    ListView repos_listView;
    ArrayAdapter<String> arrayAdapter ;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        avatar = findViewById(R.id.avatar_UserProfile);
        name= findViewById(R.id.name);
        username= findViewById(R.id.username);
        email=findViewById(R.id.email);
        repos_listView=findViewById(R.id.repos_list);

        repos_Name_List =new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,repos_Name_List );
        repos_listView.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        String userDetails_Json =intent.getStringExtra("UserDetails");

        Gson gson = new Gson();
        user = gson.fromJson(userDetails_Json,User.class);


        Picasso.get().load(user.getAvatar_url()).into(avatar);
        name.setText(user.getName());
        username.setText(user.getUsername());
        email.setText(user.getEmail());


        repos_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent shareIntent= new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                String url= repos_List.get(i).getHtml_url();
                shareIntent.setData(Uri.parse(url));
                startActivity(Intent.createChooser(shareIntent,"Open Repository"));

            }
        });


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/users/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UserApi userApi = retrofit.create(UserApi.class);
        UserApi userApi =MyApi.getInstance().getUserApi();

        Call<ArrayList<Repo>> call = userApi.getReposList(user.getUsername());

        call.enqueue(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Could not get Repos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {

        repos_List = response.body();

        for(int i=0;i<repos_List.size();i++)
        {
            repos_Name_List.add(repos_List.get(i).getName());
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {

        Snackbar.make(repos_listView,"Couldnot get Users",Snackbar.LENGTH_SHORT);
    }

    public void follower(View view) {
        Intent intent = new Intent(this,Followers_Display.class);
        intent.putExtra("UserDetails",user.getUsername());
        startActivity(intent);
    }
}
