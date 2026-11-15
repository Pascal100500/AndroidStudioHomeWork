package com.example.musiclibrary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailComposer = findViewById(R.id.detailComposer);
        TextView detailYear = findViewById(R.id.detailYear);
        TextView detailDescription = findViewById(R.id.detailDescription);
        String title = getIntent().getStringExtra("title");
        String composer = getIntent().getStringExtra("composer");
        int year = getIntent().getIntExtra("year", 0);
        String description = getIntent().getStringExtra("description");
        detailTitle.setText(title);
        detailComposer.setText(composer);
        detailYear.setText("Год создания: " + year);
        detailDescription.setText(description);
    }
}