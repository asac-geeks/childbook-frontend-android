package com.example.childandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.TemporaryComment;
import com.example.childandroid.modules.TemporaryPost;

import java.util.List;

public class ParentTemporaryCommentsAdapter extends RecyclerView.Adapter<ParentTemporaryCommentsAdapter.ViewHolder>{
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        public ViewHolder (View itemView){
            super(itemView);
            body = itemView.findViewById(R.id.com_body);
        }
    }

    private Context context;
    private List<TemporaryComment> comments;
    public ParentTemporaryCommentsAdapter(Context context,  List<TemporaryComment> comments){
        this.context =context;
        this.comments = comments;
    }

    public ParentTemporaryCommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.temp_comment_parent, parent, false);

        return new ParentTemporaryCommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentTemporaryCommentsAdapter.ViewHolder holder, int position) {
        TemporaryComment comment = comments.get(position);
        holder.body.setText(comment.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
