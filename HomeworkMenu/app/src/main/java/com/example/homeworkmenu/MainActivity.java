package com.example.homeworkmenu;

import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editTextMessage = findViewById(R.id.editTextMessage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit) {

            showEditDialog();
            return true;

        }

         else if (id == R.id.action_notify) {

            Toast.makeText(
                    this,
                    "Вы выбрали вызов оповещения",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private EditText editTextMessage;

    private void showEditDialog() {

        EditText input = new EditText(this);
        input.setSingleLine(false);
        input.setHint("Введите сообщение");

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Редактировать текст");
        builder.setView(input);

        builder.setPositiveButton("OK",
                (dialog, which) -> {
                    String message = input.getText().toString();
                    editTextMessage.setText(message);
                });

        builder.setNegativeButton("ОТМЕНА",
                (dialog, which) -> dialog.cancel());

        builder.show();
    }

}