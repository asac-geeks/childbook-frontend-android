package com.example.childandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.childandroid.modules.youtube.YouTubeItems;

import java.util.ArrayList;
import java.util.List;


public class YouTubeAdapter extends BaseAdapter {
    List<YouTubeItems> videos = new ArrayList<>();

    public YouTubeAdapter(List<YouTubeItems> youTubeApi) {
        this.videos = youTubeApi;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position).getSnippet().getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.you_tube_row, null);
        // ----------------------- video call -------------------------------------- //
        String id =  videos.get(position).getId().getVideoId();

        ImageView image = view.findViewById(R.id.youTubeItemImage);
        TextView title = view.findViewById(R.id.youTubeItemTitle);
        TextView disc = view.findViewById(R.id.youTubeItemDisc);

        title.setText(videos.get(position).getSnippet().getTitle());
        disc.setText(videos.get(position).getSnippet().getDescription());
                   Glide.with(parent.getContext())
                                .load("https://img.youtube.com/vi/"+id+"/0.jpg")
                                .into(image);

        image.setOnClickListener(v -> {
            Intent videoIntentActivity = new Intent(parent.getContext(), YouTubePlayerLocally.class);
                videoIntentActivity.putExtra("id",id);
                videoIntentActivity.putExtra("disc", videos.get(position).getSnippet().getDescription());
                videoIntentActivity.putExtra("title", videos.get(position).getSnippet().getTitle());

            parent.getContext().startActivity(videoIntentActivity);
        });
        return view;
    }  }
    