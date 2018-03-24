package com.example.abhij.githubnetworking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abhij on 18-03-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder>{

    interface  OnClickListener{

         void OnClick(int position);
    }

    Context context ;
    ArrayList<User> followers;
    OnClickListener listener;

    public RecyclerAdapter(Context context, ArrayList<User> followers, OnClickListener listener) {
        this.context = context;
        this.followers = followers;
        this.listener = listener;

    }

    @Override
    public RecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_user,parent,false);

        MyHolder holder = new MyHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyHolder holder, final int position) {

        User user = followers.get(position);

        holder.userName.setText(user.getUsername());
        Picasso.get().load(user.getAvatar_url()).into(holder.avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {

        return followers.size();
    }



    class MyHolder extends RecyclerView.ViewHolder{

        TextView userName ;
        ImageView avatar;
        View itemView ;

        public MyHolder(View itemView) {

            super(itemView);

            userName = itemView.findViewById(R.id.username_row_user);
            avatar = itemView.findViewById(R.id.avatar_row_user);
            this.itemView = itemView;
        }

    }


}

