package com.example.flashlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "FlashlightSettings";
    Bitmap originalImage, newImage;
    ImageView imageView;
    Boolean isSwitcherOn;
    int newWidth, newHeight;

    CameraManager cameraManager;
    String cameraId;

    SharedPreferences sharedPreferences;
    String typeOfSwitcherView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item1) {
            uploadSwitcherImage();

            typeOfSwitcherView ="Classic";
            saveTypeOfSwitcherView(typeOfSwitcherView);

            if (isSwitcherOn) {
                viewLeftPartOfImage();


            } else {
                viewRightPartOfImage();
            }
            return true;

        } else if (itemId == R.id.menu_item2) {
            uploadFridgeImage();

            typeOfSwitcherView ="Fridge";
            saveTypeOfSwitcherView(typeOfSwitcherView);

            if (isSwitcherOn) {
                viewLeftPartOfImage();
            } else {
                viewRightPartOfImage();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        typeOfSwitcherView = sharedPreferences.getString("typeOfSwitcherView","Classic");

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            String[] cameraIds = cameraManager.getCameraIdList();

            if (cameraIds.length > 0) {
                cameraId = cameraIds[0];
            } else {
                cameraId = null;
            }

        } catch (CameraAccessException e) {
            cameraId = null;
        }

        setStartPosition();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSwitcherOn = !isSwitcherOn;

                if (isSwitcherOn) {
                    viewLeftPartOfImage();
                } else {
                    viewRightPartOfImage();
                }

                if (cameraId != null) {
                    try {
                        cameraManager.setTorchMode(cameraId, isSwitcherOn);
                    } catch (CameraAccessException | IllegalArgumentException e) {
                        android.util.Log.e("FLASHLIGHT", "Не удалось переключить фонарик", e);
                    }
                }
            }
        });

    }
    private void setStartPosition() {
        if (typeOfSwitcherView.equals("Classic")) {
            uploadSwitcherImage();
        } else if (typeOfSwitcherView.equals("Fridge")) {
            uploadFridgeImage();
        }

        viewRightPartOfImage();
        isSwitcherOn = false;

    }

    private void viewRightPartOfImage() {
        newWidth = originalImage.getWidth()/2;
        newHeight = originalImage.getHeight();
        newImage = Bitmap.createBitmap(originalImage, newWidth, 0, newWidth, newHeight);
        imageView.setImageBitmap(newImage);
    }

    private void viewLeftPartOfImage() {
        newWidth = originalImage.getWidth()/2;
        newHeight = originalImage.getHeight();
        newImage = Bitmap.createBitmap(originalImage, 0, 0, newWidth, newHeight);
        imageView.setImageBitmap(newImage);
    }
    private void uploadSwitcherImage() {
        imageView = findViewById(R.id.imageView);
        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.switcher);

    }
    private void uploadFridgeImage() {
        imageView = findViewById(R.id.imageView);
        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.fridge);

    }

    private void saveTypeOfSwitcherView(String typeOfSwitcherView) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("typeOfSwitcherView", typeOfSwitcherView);
        editor.apply();
    }
}