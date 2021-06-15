package com.example.childandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.Post;
import com.example.childandroid.modules.Share;

import java.util.List;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
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
    private List<Share> shares;
    public ShareAdapter(Context context,   List<Share> shares){
        this.context =context;
        this.shares = shares;
    }

    public ShareAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.temp_comment_parent, parent, false);

        return new ShareAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareAdapter.ViewHolder holder, int position) {
        Share share = shares.get(position);
        holder.body.setText(share.getPost().getBody());
        holder.title.setText(share.getPost().getPostTitle());
        holder.username.setText(share.getPost().getAppUser().getUserName());
        holder.id.setText(share.getPost().getId());
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
        return shares.size();
    }
}
