package com.example.childandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.Post;

import java.util.List;

public class PostsAdapter  extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView body,title,username,id;
        public ViewHolder (View itemView){
            super(itemView);
            body = itemView.findViewById(R.id.post_body);
            title = itemView.findViewById(R.id.post_title);
            username= itemView.findViewById(R.id.post_user);
            id = itemView.findViewById(R.id.post_id);
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
        holder.title.setText(post.getPostTitle());
        holder.username.setText(post.getAppUser().getUserName());
        holder.id.setText(post.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, PostDelailsActivity.class);
                TextView id =  v.findViewById(R.id.post_id);
                k.putExtra("id",id.getText().toString());
                context.startActivity(k);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
