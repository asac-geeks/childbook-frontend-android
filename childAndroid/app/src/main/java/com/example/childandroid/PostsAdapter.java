package com.example.childandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.Post;
import com.example.childandroid.modules.TemporaryComment;

import java.util.List;

public class PostsAdapter  extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        public ViewHolder (View itemView){
            super(itemView);
            body = itemView.findViewById(R.id.com_body);
        }
    }

    private Context context;
    private List<Post> posts;
    public PostsAdapter(Context context,  List<Post> posts){
        this.context =context;
        this.posts = posts;
    }

    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.temp_comment_parent, parent, false);

        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.body.setText(post.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
