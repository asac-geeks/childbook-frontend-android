package com.example.childandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childandroid.modules.AppUser;

import java.util.List;

public class ParentPageAdapter extends RecyclerView.Adapter<ParentPageAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView childName;
        public ViewHolder (View itemView){
            super(itemView);
            childName = itemView.findViewById(R.id.com_body);
        }
    }

    private Context context;
    private List<AppUser> appUsers;
    public ParentPageAdapter(Context context,  List<AppUser> appUsers){
        this.context =context;
        this.appUsers = appUsers;
    }

    public ParentPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.child, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentPageAdapter.ViewHolder holder, int position) {
        AppUser child = appUsers.get(position);
        holder.childName.setText(child.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, ChildActivity.class);
                TextView childName = (TextView) v.findViewById(R.id.name);

                k.putExtra("childName",childName.getText().toString());
                context.startActivity(k);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appUsers.size();
    }

}
