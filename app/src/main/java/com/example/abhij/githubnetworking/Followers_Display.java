package com.example.abhij.githubnetworking;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abhij.githubnetworking.MyApi.getInstance;

public class Followers_Display extends AppCompatActivity {

    RecyclerView recycleView ;
    RecyclerAdapter adapter;
    ArrayList<User> followers;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers__display);

        recycleView = findViewById(R.id.listView_followers);
        followers= new ArrayList<>();
        progressBar =findViewById(R.id.progressBar_followers);
        adapter= new RecyclerAdapter(this, followers, new RecyclerAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {

                followers.remove(position);
                adapter.notifyItemRemoved(position);

            }
        });

        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                Collections.swap(followers,from,to);
                adapter.notifyItemMoved(from,to);
                return true;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                followers.remove(position);
                adapter.notifyItemRemoved(position);

                Snackbar.make(recycleView,"User Removed",Snackbar.LENGTH_SHORT).show();
            }

        });

        itemTouchHelper.attachToRecyclerView(recycleView);
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);

        Intent intent = getIntent();
        String username = intent.getStringExtra("UserDetails");

        Call<ArrayList<User>> call =MyApi.getInstance().getUserApi().getFollowersList(username);

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {


                progressBar.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);

                followers.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                Snackbar.make(recycleView,"Couldnot get followers ",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
