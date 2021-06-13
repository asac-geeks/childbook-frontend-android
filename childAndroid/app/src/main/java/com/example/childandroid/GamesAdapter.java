package com.example.childandroid;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.childandroid.modules.GamesApi;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends BaseAdapter {
    List<GamesApi> games = new ArrayList<GamesApi>() {
    };

    public GamesAdapter(List<GamesApi> games) {
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.game_api_row,null);

        Button viewButton = view.findViewById(R.id.gameViewButtonListViewItem);
        TextView title = view.findViewById(R.id.gameTitleListViewItem);
        TextView genre = view.findViewById(R.id.gameGenreListViewItem);
        TextView description =  view.findViewById(R.id.gameDiscriptionListViewItem);
        ImageView image =  view.findViewById(R.id.gameThumbnailListViewItem);


        title.setText(games.get(position).getTitle());
         genre.setText(games.get(position).getGenre());
         description.setText(games.get(position).getShort_description());
        Glide.with(parent.getContext())
                .load(games.get(position).getThumbnail())
                .into(image);

        viewButton.setOnClickListener(v -> {
          Intent viewDetailsIntent = new Intent(parent.getContext(), GameDetailsActivity.class);
            viewDetailsIntent.putExtra("id", games.get(position).getId());
            Log.d("id success: ", " success sent with id from adapter:"+ games.get(position).getId());
              parent.getContext().startActivity(viewDetailsIntent);
        });

//        Intent intent = new Intent(parent.getContext(), GameDetailsActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("id", games.get(position).getId());
//            parent.getContext().startActivity(intent);
        return view;
    }
}
