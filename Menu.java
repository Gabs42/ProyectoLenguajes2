package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    private Button nuevo;
    private Button listar;
    private Button buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        nuevo = (Button) findViewById(R.id.butonNueva);
        listar = (Button) findViewById(R.id.butonList);
        buscar = (Button) findViewById(R.id.butonSearch);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change  = new Intent(Menu.this,nuevaReceta.class);
                startActivity(change);

            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change  = new Intent(Menu.this,listarRecetas.class);
                startActivity(change);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change  = new Intent(Menu.this,buscarRecetas.class);
                startActivity(change);
            }
        });
    }
}
