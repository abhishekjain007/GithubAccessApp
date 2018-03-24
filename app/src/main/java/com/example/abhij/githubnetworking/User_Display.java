package com.example.abhij.githubnetworking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_Display extends AppCompatActivity {

    RecyclerView recycleView_User;
    ProgressBar  progressBar_User;
//    ArrayList<String> user_Name_List;
    RecyclerAdapter adapter ;
    ArrayList<User> users_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recycleView_User=findViewById(R.id.recycleList_users);
        progressBar_User = findViewById(R.id.progressBar_users);



        users_list=new ArrayList<>();
//    user_Name_List = new ArrayList<>();
//        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,user_Name_List);

        adapter = new RecyclerAdapter(this, users_list, new RecyclerAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                Gson gson =new Gson();
                String userDetails = gson.toJson(users_list.get(position));
                Intent intent = new Intent(recycleView_User.getContext(),UserProfile.class);
                intent.putExtra("UserDetails",userDetails);

                startActivity(intent);
            }
        });

        recycleView_User.setAdapter(adapter);
        recycleView_User.setLayoutManager(new LinearLayoutManager(this));
        recycleView_User.setItemAnimator(new DefaultItemAnimator());
        recycleView_User.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from =viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                Collections.swap(users_list,from,to);
                adapter.notifyItemMoved(from,to);

                return true;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position =viewHolder.getAdapterPosition();
                users_list.remove(position);

                adapter.notifyItemRemoved(position);
                Snackbar.make(recycleView_User,"User Removed",Snackbar.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recycleView_User);
//        listView_User.setAdapter(arrayAdapter);
//
//        listView_User.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Gson gson =new Gson();
//                String userDetails = gson.toJson(users_list.get(i));
//                Intent intent = new Intent(view.getContext(),UserProfile.class);
//                intent.putExtra("UserDetails",userDetails);
//
//                startActivity(intent);
//
//            }
//        });

        progressBar_User.setVisibility(View.VISIBLE);

        recycleView_User.setVisibility(View.GONE);

//                Retrofit retrofit =new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        UserApi userApi= retrofit.create(UserApi.class);


        UserApi userApi = MyApi.getInstance().getUserApi();
        Call<ArrayList<User>> call = userApi.getUsersList();


        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                progressBar_User.setVisibility(View.GONE);
                recycleView_User.setVisibility(View.VISIBLE);


                users_list.addAll(response.body());
                adapter.notifyDataSetChanged();

                Log.d("data","has been taken");

//                for(int i =0;i<users_list.size();i++)
//                {
//
//                    user_Name_List.add(users_list.get(i).getUsername());
//                }
//                arrayAdapter.notifyDataSetChanged();

            }


            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                progressBar_User.setVisibility(View.GONE);
               recycleView_User.setVisibility(View.VISIBLE);

                Snackbar.make(recycleView_User, "Could not Connect", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Log.d("Data","Couldnot be fetched");
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user__display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent =new Intent(this,SearchUser.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
