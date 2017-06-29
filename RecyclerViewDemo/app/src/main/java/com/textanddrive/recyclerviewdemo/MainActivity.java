package com.textanddrive.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.textanddrive.recyclerviewdemo.adapters.VideoAdapter;
import com.textanddrive.recyclerviewdemo.models.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Video> videoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_video_list);
        createDummyVideoList();
        videoAdapter = new VideoAdapter(videoList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoAdapter);
    }

    private void createDummyVideoList() {

        //String id = "UnMTJ0cwTwc";
        String id;// = "Z75mcxJrDZU";

        for (int i = 0; i < 50; i++) {
            if (i%3==2){
                id = "nwBp_OW_wkg";
            }
            else if (i%3==1){
                id = "Z75mcxJrDZU";
            }
            else {
                id = "UnMTJ0cwTwc";
            }
            String image_url = "https://img.youtube.com/vi/"+id+"/0.jpg";
            Video video = new Video(i + 1, "Video " + (i + 1), "MovieCon English", 123456,image_url);
            videoList.add(video);
        }
    }
}
