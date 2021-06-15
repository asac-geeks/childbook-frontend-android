package com.example.childandroid;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.childandroid.modules.youtube.YouTubeApi;
import com.example.childandroid.modules.youtube.YouTubeItems;
import com.github.nkzawa.socketio.client.Url;
import com.google.android.youtube.player.YouTubeBaseActivity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

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
        MediaController mediaController = new MediaController(parent.getContext());
        String id =  videos.get(position).getId().getVideoId();
        Log.d("my id", ":my id: "+ id);
//......................... youtube player ...........................
        YouTubeFragCreator player = new YouTubeFragCreator();
        player.runVideo(id);
//        .............. end of youtube player .......
        return view;
    }

    static class YouTubeFragCreator extends YouTubeBaseActivity{

        public void runVideo(String id){
            // yazan
//        String api_key = "AIzaSyCl2DUwe_iYExrnevvbsjps2nf2ceTAmo8";
            // husam
            String api_key = "AIzaSyAbytVhdgRyfZDstAIgK2Y5qjNH7O55GY4";

            YouTubePlayerView ytPlayer = (YouTubePlayerView)findViewById(R.id.ytPlayer);

            ytPlayer.initialize(api_key, new YouTubePlayer.OnInitializedListener(){
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(id);
                    youTubePlayer.play();
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                    Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
