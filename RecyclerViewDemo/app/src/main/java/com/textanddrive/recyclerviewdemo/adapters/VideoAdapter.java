package com.textanddrive.recyclerviewdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.textanddrive.recyclerviewdemo.R;
import com.textanddrive.recyclerviewdemo.models.Video;

import java.util.List;

/**
 * Created by Niraj on 21-02-2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> videoList;
    private Context context;

    public VideoAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final Video video = videoList.get(position);
        holder.mTvVideoTitle.setText(video.getName());
        holder.mTvVideoViews.setText(video.getChannelName());
        //Picasso.with(context).load("https://img.youtube.com/vi/UnMTJ0cwTwc/0.jpg").into(holder.mIvThumb);
        Picasso.with(context).load(video.getThumbURL()).into(holder.mIvThumb);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvThumb;
        private ImageView mIvSmallThumb;
        private TextView mTvVideoTitle;
        private TextView mTvVideoViews;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mIvThumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
            mIvSmallThumb = (ImageView) itemView.findViewById(R.id.civ_small_thumb);
            mTvVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            mTvVideoViews = (TextView) itemView.findViewById(R.id.tv_views);
        }
    }
}
