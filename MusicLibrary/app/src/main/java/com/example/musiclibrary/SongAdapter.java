package com.example.musiclibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import android.media.MediaPlayer;
import android.widget.ImageButton;

import android.os.Handler;
import android.os.Looper;
import android.widget.SeekBar;
import android.content.Intent;
import android.widget.Button;

public class SongAdapter extends ArrayAdapter<Song> {

    private MediaPlayer mediaPlayer;
    private int currentSongPosition = -1;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                notifyDataSetChanged();
                handler.postDelayed(this, 500);
            }
        }
    };

    public SongAdapter(Context context, List<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_song, parent, false);
        }

        Song song = getItem(position);

        if (song != null) {
            TextView songTitle = view.findViewById(R.id.songTitle);
            TextView songComposer = view.findViewById(R.id.songComposer);
            SeekBar songSeekBar = view.findViewById(R.id.songSeekBar);
            TextView currentTime = view.findViewById(R.id.currentTime);
            TextView totalTime = view.findViewById(R.id.totalTime);

            songTitle.setText(song.getTitle());
            songComposer.setText(song.getComposer());

            if (mediaPlayer != null && currentSongPosition == position) {

                int duration = mediaPlayer.getDuration();
                int currentPosition = mediaPlayer.getCurrentPosition();

                songSeekBar.setMax(duration);
                songSeekBar.setProgress(currentPosition);

                currentTime.setText(formatTime(currentPosition));
                totalTime.setText(formatTime(duration));



            } else {
                songSeekBar.setMax(100);
                songSeekBar.setProgress(0);
                currentTime.setText("0:00");
                totalTime.setText("0:00");
            }

            songSeekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged(
                                SeekBar seekBar,
                                int progress,
                                boolean fromUser) {

                            if (fromUser
                                    && mediaPlayer != null
                                    && currentSongPosition == position) {

                                currentTime.setText(formatTime(progress));
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                            if (mediaPlayer != null
                                    && currentSongPosition == position) {

                                mediaPlayer.seekTo(seekBar.getProgress());
                                notifyDataSetChanged();
                            }
                        }
                    }
            );

            ImageButton playButton = view.findViewById(R.id.playButton);

            if (mediaPlayer != null
                    && currentSongPosition == position
                    && mediaPlayer.isPlaying()) {

                playButton.setImageResource(
                        android.R.drawable.ic_media_pause
                );

            } else {

                playButton.setImageResource(
                        android.R.drawable.ic_media_play
                );
            }

            playButton.setOnClickListener(v -> {
                playSong(song, position);
                notifyDataSetChanged();
            });

            Button detailsButton = view.findViewById(R.id.detailsButton);

            detailsButton.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), PlayerActivity.class);

                intent.putExtra("title", song.getTitle());
                intent.putExtra("composer", song.getComposer());
                intent.putExtra("year", song.getYear());
                intent.putExtra("description", song.getDescription());

                getContext().startActivity(intent);
            });
        }

        return view;
    }
    private void playSong(Song song, int position) {

        if (mediaPlayer != null) {

            if (currentSongPosition == position) {

                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.pause();
                    handler.removeCallbacks(updateProgress);

                } else {

                    mediaPlayer.start();

                    handler.removeCallbacks(updateProgress);
                    handler.post(updateProgress);
                }

                notifyDataSetChanged();
                return;
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(
                getContext(),
                song.getAudioResourceId()
        );

        if (mediaPlayer != null) {
            currentSongPosition = position;

            mediaPlayer.setOnCompletionListener(mp -> {
                handler.removeCallbacks(updateProgress);
                mp.seekTo(0);
                notifyDataSetChanged();
            });

            mediaPlayer.start();

            handler.removeCallbacks(updateProgress);
            handler.post(updateProgress);

            notifyDataSetChanged();

        } else {
            currentSongPosition = -1;
        }
    }
    private String formatTime(int milliseconds) {
        int totalSeconds = milliseconds / 1000;

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format(
                java.util.Locale.getDefault(),
                "%d:%02d",
                minutes,
                seconds
        );
    }
}
