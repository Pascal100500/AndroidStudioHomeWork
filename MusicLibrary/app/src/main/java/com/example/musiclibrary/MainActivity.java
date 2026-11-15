package com.example.musiclibrary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadSongs();

        ListView songListView = findViewById(R.id.songListView);
        SongAdapter songAdapter = new SongAdapter(this, songs);
        songListView.setAdapter(songAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadSongs() {
        songs = new ArrayList<>();

        songs.add(new Song(
                "Соната № 14 «Лунная»",
                "Людвиг ван Бетховен",
                1801,
                "Фортепианная соната № 14 до-диез минор, сочинение 27 № 2.",
                R.raw.beethoven_sonata_no14
        ));

        songs.add(new Song(
                "Соната № 14 — другая часть",
                "Людвиг ван Бетховен",
                1801,
                "Ещё одна часть знаменитой сонаты Бетховена.",
                R.raw.beethoven_fantasy
        ));

        songs.add(new Song(
                "Симфония № 55 «Школьный учитель»",
                "Йозеф Гайдн",
                1774,
                "Симфония № 55 ми-бемоль мажор, известная под названием «Школьный учитель».",
                R.raw.haydn_symphony_no55
        ));

        songs.add(new Song(
                "Соната соль минор",
                "Антонио Вивальди",
                0,
                "Произведение Антонио Вивальди. Точный год создания уточним позже.",
                R.raw.vivaldi_sonata_g_minor
        ));
    }
}