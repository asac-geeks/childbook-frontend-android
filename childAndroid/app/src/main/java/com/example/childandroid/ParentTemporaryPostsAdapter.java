package com.example.childandroid;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.childandroid.modules.Post;
import com.example.childandroid.modules.TemporaryPost;

import java.util.List;
import java.util.Objects;

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
        System.out.println(post);
        holder.body.setText(post.getBody());
        System.out.println(post.getBody());
        System.out.println(post.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, TempPostDetailsActivity.class);
                TextView post =  v.findViewById(R.id.body);
                System.out.println(post.getText().toString());
                k.putExtra("id",1);
                context.startActivity(k);
            }
        });

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentTemporaryPostsAdapter that = (ParentTemporaryPostsAdapter) o;
        return Objects.equals(context, that.context) &&
                Objects.equals(posts, that.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, posts);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
