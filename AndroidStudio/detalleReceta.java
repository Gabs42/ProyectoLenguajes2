package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detalleReceta extends AppCompatActivity {

    ImageView img;
    Button sigImagen;
    int contadorImagen = 1;
    String[] fotos;
    private String imageUrl="https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/s3Folder/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);
        img = (ImageView) findViewById(R.id.imageView);
        sigImagen= (Button) findViewById(R.id.buttonSigFoto);
        Intent intent = getIntent();
        String receta = intent.getStringExtra("Receta");
        fotos = intent.getStringExtra("Fotos").split(",");
        TextView txtReceta = (TextView) findViewById(R.id.txtDetalle);
        txtReceta.setText(receta);
        Glide.with(detalleReceta.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(img);

        sigImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteImagen();
            }
        });
    }
    private void siguienteImagen(){
        if(contadorImagen== fotos.length){
            return;
        }
        else{
            Glide.with(detalleReceta.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[contadorImagen]).into(img);
            contadorImagen++;
        }
    }
}
