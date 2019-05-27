package com.example.appproyectolenguajes;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;

public class pruebaImagenes extends AppCompatActivity {

    private ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_imagenes);
        test = (ImageView) findViewById(R.id.testImageView);
        LoadImageFromWebOperations("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/s3Folder/s3Key.txt");
    }
    public void LoadImageFromWebOperations(String url) {
        Glide.with(pruebaImagenes.this)
                .asBitmap()
                .load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/s3Folder/s3Key.txt")
                .into(test);


    }
}
