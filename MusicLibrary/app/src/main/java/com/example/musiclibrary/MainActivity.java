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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Arrays;
import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {

    private List<Song> songs;
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadSongs();

        ListView songListView = findViewById(R.id.songListView);
        List<Song> displayedSongs = new ArrayList<>(songs);
        songAdapter = new SongAdapter(this, displayedSongs);
        songListView.setAdapter(songAdapter);

        Spinner genreSpinner = findViewById(R.id.genreSpinner);

        List<String> genres = Arrays.asList(
                "Все композиции",
                "Классическая музыка",
                "Рок"
        );

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                this,
                R.layout.item_genre,
                genres
        );

        genreAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        genreSpinner.setAdapter(genreAdapter);

        genreSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedGenre =
                                parent.getItemAtPosition(position).toString();

                        List<Song> filteredSongs = new ArrayList<>();

                        for (Song song : songs) {

                            if (selectedGenre.equals("Все композиции")
                                    || song.getGenre().equals(selectedGenre)) {

                                filteredSongs.add(song);
                            }
                        }

                        songAdapter.stopPlayback();
                        displayedSongs.clear();
                        displayedSongs.addAll(filteredSongs);
                        songAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

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
                R.raw.beethoven_sonata_no14,
                "Классическая музыка"
        ));

        songs.add(new Song(
                "Соната № 14 — другая часть",
                "Людвиг ван Бетховен",
                1801,
                "Ещё одна часть знаменитой сонаты Бетховена.",
                R.raw.beethoven_fantasy,
                "Классическая музыка"
        ));

        songs.add(new Song(
                "Симфония № 55 «Школьный учитель»",
                "Йозеф Гайдн",
                1774,
                "Симфония № 55 ми-бемоль мажор, известная под названием «Школьный учитель».",
                R.raw.haydn_symphony_no55,
                "Классическая музыка"
        ));

        songs.add(new Song(
                "Соната соль минор",
                "Антонио Вивальди",
                1708,
                "Классическое произведение для скрипки и бассо континуо (1708 год), состоящее из четырех частей (Прелюдия: Анданте, Жига: Аллегро, Сарабанда: Ларго и др.).",
                R.raw.vivaldi_sonata_g_minor,
                "Классическая музыка"
        ));

        songs.add(new Song(
                "Walk on water",
                "Ozzy Osbourne",
                1996,
                "Трек был изначально написан и выпущен как часть саундтрека к полнометражному мультфильму «Бивис и Баттхед уделывают Америку».",
                R.raw.osbourne_walk_on_water,
                "Рок"
        ));
    }
    @Override
    protected void onDestroy() {
        if (songAdapter != null) {
            songAdapter.stopPlayback();
        }

        super.onDestroy();
    }
}