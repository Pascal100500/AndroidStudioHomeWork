package com.example.flashlight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Bitmap originalImage, newImage;
    ImageView ImageView;
    Boolean isSwitcherOn;
    int newWidth, newHeight;

    CameraManager cameraManager;
    String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            cameraId =cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

        setStartPosition();

        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSwitcherOn = !isSwitcherOn;
                if (isSwitcherOn) {
                    newImage = Bitmap.createBitmap(originalImage, 0, 0, newWidth, newHeight);
                    ImageView.setImageBitmap(newImage);


                } else {
                    newImage = Bitmap.createBitmap(originalImage, newWidth, 0, newWidth, newHeight);
                    ImageView.setImageBitmap(newImage);

                    try {
                        cameraManager.setTorchMode(cameraId,isSwitcherOn);
                    } catch (CameraAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    private void setStartPosition() {

        ImageView = findViewById(R.id.imageView);

        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.switcher);

        newWidth = originalImage.getWidth()/2;
        newHeight = originalImage.getHeight();

        newImage = Bitmap.createBitmap(originalImage, newWidth, 0, newWidth, newHeight);
        ImageView.setImageBitmap(newImage);

        isSwitcherOn = false;

    }
}