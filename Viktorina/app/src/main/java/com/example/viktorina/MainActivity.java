package com.example.viktorina;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvQuestion;
    private Button[] buttons = new Button[4];
    private String[] questions = {
            "Столица Франции?",
            "2 + 2 * 2 = ?",
            "Кто написал 'Отцы и дети' ?",
            "Самая большая планета"
    };

    private String[][] answers = {
            {"Париж", "Милан", "Москва", "Уссурийск"},
            {"1", "4", "6", "8"},
            {"Достоевский", "Тургенев", "Блок", "Македонский"},
            {"Земля", "Сатурн", "Юпитер", "Марс"}
    };

    private int[] correctAnswers = {0, 2, 1, 2};
    private int currentQuestion = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    tvQuestion = findViewById(R.id.tvQuestion);
    buttons[0] = findViewById(R.id.btnAnswer1);
    buttons[1] = findViewById(R.id.btnAnswer2);
    buttons[2] = findViewById(R.id.btnAnswer3);
    buttons[3] = findViewById(R.id.btnAnswer4);

        for (int i = 0; i < 4; i++) {
          final int index = i;
          buttons[i].setOnClickListener(v -> checkAnswer(index));
        }
    showQuestion();
    }

    private void showQuestion() {
        if (currentQuestion >= questions.length) {
            Toast.makeText(this, "Викторина окончена! Счет: " + score + "/" + questions.length, Toast.LENGTH_LONG).show();
            tvQuestion.setText("Игра завершена");
            for (Button b : buttons) {b.setEnabled(false);}
            return;
        }

        tvQuestion.setText(questions[currentQuestion]);
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(answers[currentQuestion][i]);
        }
    }

    private void checkAnswer(int chosen) {
        if (chosen == correctAnswers[currentQuestion]) {
            score++;
            Toast.makeText(this, "Правильно", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show();
        }
        currentQuestion++;
        showQuestion();
    }
}