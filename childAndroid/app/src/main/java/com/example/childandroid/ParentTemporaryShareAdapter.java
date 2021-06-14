package com.example.childandroid;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.TemporaryShare;

import java.util.List;

public class ParentTemporaryShareAdapter extends RecyclerView.Adapter<ParentTemporaryShareAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        public ViewHolder (View itemView){
            super(itemView);

            body = itemView.findViewById(R.id.body);
        }
    }

    private Context context;
    private List<TemporaryShare> shares;
    public ParentTemporaryShareAdapter(Context context,  List<TemporaryShare> shares){
        this.context =context;
        this.shares = shares;
    }

    public ParentTemporaryShareAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.temp_share_parent, parent, false);

        return new ParentTemporaryShareAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentTemporaryShareAdapter.ViewHolder holder, int position) {
        TemporaryShare share = shares.get(position);
        holder.body.setText(share.getPost().getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return shares.size();
    }
}
