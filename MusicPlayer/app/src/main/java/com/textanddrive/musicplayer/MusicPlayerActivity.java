package com.textanddrive.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MusicPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private AppCompatImageButton btnPlay;
    private AppCompatImageButton btnForward;
    private AppCompatImageButton btnBackward;
    private AppCompatImageButton btnNext;
    private AppCompatImageButton btnPrevious;
    private AppCompatImageButton btnPlaylist;
    private AppCompatImageButton btnRepeat;
    private AppCompatImageButton btnShuffle;
    private AppCompatSeekBar songProgressBar;
    private AppCompatTextView songTitleLabel;
    private AppCompatTextView songCurrentDurationLabel;
    private AppCompatTextView songTotalDurationLabel;
    private MediaPlayer mp;
    private Handler mHandler = new Handler();
    private SongsManager songsManager;
    private Utilities utils;
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private static final int PERMISSION_REQUEST_CODE = 1;

    private void initViews() {
        btnPlay = (AppCompatImageButton) findViewById(R.id.btn_play);
        btnForward = (AppCompatImageButton) findViewById(R.id.btn_forward);
        btnBackward = (AppCompatImageButton) findViewById(R.id.btn_backward);
        btnNext = (AppCompatImageButton) findViewById(R.id.btn_next);
        btnPrevious = (AppCompatImageButton) findViewById(R.id.btn_previous);
        btnPlaylist = (AppCompatImageButton) findViewById(R.id.btn_playlist);
        btnRepeat = (AppCompatImageButton) findViewById(R.id.btn_repeat);
        btnShuffle = (AppCompatImageButton) findViewById(R.id.btn_shuffle);
        songProgressBar = (AppCompatSeekBar) findViewById(R.id.sb_song_progress);
        songTitleLabel = (AppCompatTextView) findViewById(R.id.tv_song_title);
        songCurrentDurationLabel = (AppCompatTextView) findViewById(R.id.tv_current_duration);
        songTotalDurationLabel = (AppCompatTextView) findViewById(R.id.tv_total_duration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        initViews();

        mp = new MediaPlayer();
        songsManager = new SongsManager();
        utils = new Utilities();
        songProgressBar.setOnSeekBarChangeListener(this);
        mp.setOnCompletionListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                player();
            } else {
                requestPermission();
            }
        } else {
            player();
        }
    }

    public void player() {
        songsList = songsManager.getPlayList();
        Log.d("TEST", "Total Songs : " + songsList.size());
        playSong(0);
        mp.pause();
        btnPlay.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);

        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicPlayerActivity.this, PlayListActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mp.getCurrentPosition();
                if (currentPosition + seekForwardTime <= mp.getDuration()) {
                    mp.seekTo(currentPosition + seekForwardTime);
                } else {
                    mp.seekTo(mp.getDuration());
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int currentPosition = mp.getCurrentPosition();
                if (currentPosition - seekBackwardTime >= 0) {
                    mp.seekTo(currentPosition - seekBackwardTime);
                } else {
                    mp.seekTo(0);
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    Random rand = new Random();
                    currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
                    playSong(currentSongIndex);
                } else {
                    if (currentSongIndex < (songsList.size() - 1)) {
                        playSong(currentSongIndex + 1);
                        currentSongIndex = currentSongIndex + 1;
                    } else {
                        playSong(0);
                        currentSongIndex = 0;
                    }
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        btnPlay.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                    }
                } else {
                    if (mp != null) {
                        mp.start();
                        btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_48dp);
                    }
                }
            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.ic_repeat_black_36dp);
                } else {
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.ic_repeat_one_black_36dp);
                    btnShuffle.setImageResource(R.drawable.ic_shuffle_grey_400_36dp);
                }
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.ic_shuffle_grey_400_36dp);
                } else {
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.ic_shuffle_black_36dp);
                    btnRepeat.setImageResource(R.drawable.ic_repeat_black_36dp);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            playSong(currentSongIndex);
        }
    }

    public void playSong(int songIndex) {
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();
            String songTitle = songsList.get(songIndex).get("songTitle");
            songTitleLabel.setText(songTitle);
            btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_48dp);
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            songProgressBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (isRepeat) {
            playSong(currentSongIndex);
        } else if (isShuffle) {
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            if (currentSongIndex < (songsList.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
        mp.seekTo(currentPosition);
        updateProgressBar();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MusicPlayerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MusicPlayerActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MusicPlayerActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MusicPlayerActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
    }


}
