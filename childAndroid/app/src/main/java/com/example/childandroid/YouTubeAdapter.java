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

        for(YouTubeItems video: videos){
            Log.d("response structure", "youtube id: "+ video.getId().getVideoId());
        }

//        TextView title = (TextView) view.findViewById(R.id.videoTitleRow);
//        String titleValue = videos.get(0).getItems().get(position).getSnippet().getTitle();
//        title.setText(titleValue);

 // ----------------------- video call -------------------------------------- //
        MediaController mediaController = new MediaController(parent.getContext());
        VideoView video =  (VideoView) view.findViewById(R.id.youTubeVideoRowItem);

        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);

        String id =  videos.get(position).getId().getVideoId();
//        Uri uri = Uri.parse("https://www.youtube.com/watch?v="+id);

//        video.setVideoURI(uri);
        video.requestFocus();
        video.start();
 // ----------------------- video call -------------------------------------- /l
        return view;
    }
}
