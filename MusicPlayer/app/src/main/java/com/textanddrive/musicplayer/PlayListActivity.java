package com.textanddrive.musicplayer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayListActivity extends ListActivity {
    private static final int PICKFILE_REQUEST_CODE = 1;
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private AppCompatImageButton add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });
        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
        SongsManager plm = new SongsManager();
        this.songsList = plm.getPlayList();
        Log.d("TEST", "Total Songs : " + songsList.size());
        Log.d("TEST", "Songs : " + songsList);
        for (int i = 0; i < songsList.size(); i++) {
            HashMap<String, String> song = songsList.get(i);
            songsListData.add(song);
        }
        ListAdapter adapter = new SimpleAdapter(this, songsListData, R.layout.playlist_item, new String[]{"songNumber", "songTitle"}, new int[]{R.id.tv_song_number, R.id.tv_song_title});
        setListAdapter(adapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int songIndex = position;
                Intent in = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                finish();
            }
        });
    }
}
