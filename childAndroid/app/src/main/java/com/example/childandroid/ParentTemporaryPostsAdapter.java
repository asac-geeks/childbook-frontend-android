package com.example.childandroid;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.childandroid.modules.TemporaryPost;

import java.util.List;

public class ParentTemporaryPostsAdapter extends RecyclerView.Adapter<ParentTemporaryPostsAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        public ViewHolder (View itemView){
            super(itemView);

            body = itemView.findViewById(R.id.body);
        }
    }

    private Context context;
    private List<TemporaryPost> posts;
    public ParentTemporaryPostsAdapter(Context context,  List<TemporaryPost> posts){
        this.context =context;
        this.posts = posts;
    }

    public ParentTemporaryPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.temp_post_parent, parent, false);

        return new ParentTemporaryPostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentTemporaryPostsAdapter.ViewHolder holder, int position) {
        TemporaryPost post = posts.get(position);
        holder.body.setText(post.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, TempPostDetailsActivity.class);
                TextView post = (TextView) v.findViewById(R.id.id_post);

                k.putExtra("id",post.getText().toString());
                context.startActivity(k);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
