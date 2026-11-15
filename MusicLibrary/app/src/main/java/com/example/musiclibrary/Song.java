package com.example.musiclibrary;

public class Song {
    private String title;
    private String composer;
    private int year;
    private String description;
    private int audioResourceId;

    private String genre;

    public Song(String title, String composer, int year,
                String description, int audioResourceId, String genre ) {

        this.title = title;
        this.composer = composer;
        this.year = year;
        this.description = description;
        this.audioResourceId = audioResourceId;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {
        return composer;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public String getGenre() {
        return genre;
    }
}
