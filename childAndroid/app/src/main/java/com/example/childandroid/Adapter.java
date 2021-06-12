package com.example.childandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.childandroid.modules.GamesApi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Context mContext;
    public List<GamesApi> gamesApiList;

//    public Adapter(Runnable mContext, List<GamesApi> games) {
//        this.mContext = (Context) mContext;
//        this.gamesApiList = games;
//    }

    public Adapter(List<GamesApi> games) {
        this.gamesApiList = games;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
         View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {

        holder.title.setText(gamesApiList.get(position).getTitle());
        Glide.with(mContext)
                .load(gamesApiList.get(position).getThumbnail())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return gamesApiList.size();
    }

   public static class ViewHolder extends RecyclerView.ViewHolder{
       TextView title;
       ImageView img;
       public ViewHolder(@NonNull @NotNull View itemView) {
           super(itemView);
           title =  itemView.findViewById(R.id.gameTitleListViewItem);
           img = itemView.findViewById(R.id.gameThumbnailListViewItem);
       }
    }
}
