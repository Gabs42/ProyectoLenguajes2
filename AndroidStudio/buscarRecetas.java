package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class buscarRecetas extends AppCompatActivity {

    private EditText nombre;
    private EditText tipo;
    private EditText ingrediente;
    private TextView error;
    private Button btnNombre;
    private Button btnTipo;
    private Button btnIngrediente;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    public static List<PostReceta> result;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_recetas);
        nombre = (EditText) findViewById(R.id.editRecetaNombre);
        tipo = (EditText) findViewById(R.id.reditRecetaTipo);
        ingrediente = (EditText) findViewById(R.id.editRecetaIngrediente);
        btnNombre = (Button) findViewById(R.id.buttonBuscarNombre);
        btnTipo = (Button) findViewById(R.id.buttonBuscarTipo);
        btnIngrediente = (Button) findViewById(R.id.buttonBuscarIngrediente);
        error = (TextView) findViewById(R.id.txtBuscarError);

        btnNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().matches("")){
                    error.setText("El campo de nombre debe estar lleno");
                }
                else{
                    busquedaNombre(nombre.getText().toString().toLowerCase());
                }

            }
        });

        btnTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.getText().toString().matches("")){
                    error.setText("El campo de tipo debe estar lleno");
                }
                else{
                    busquedaTipo(tipo.getText().toString().toLowerCase());
                }
            }
        });

        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingrediente.getText().toString().matches("")){
                    error.setText("El campo de tipo debe estar lleno");
                }
                else{
                    busquedaIngrediente(ingrediente.getText().toString().toLowerCase());
                }
            }
        });
    }

    private void busquedaNombre(String nombre) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        PostReceta post = new PostReceta(nombre,"","","","");
        Call<List<PostReceta>> call = jsonPlaceHolderApi.buscarRecetaNombre(post);
        call.enqueue(new Callback<List<PostReceta>>() {
            @Override
            public void onResponse(Call<List<PostReceta>> call, Response<List<PostReceta>> response) {
                if (!response.isSuccessful()) {
                    error.setText("No hay resultados");
                    return;
                }
                result = response.body();
                if(result.size()==0){
                    error.setText("No hay resultados");
                    return;
                }
                Intent intent = new Intent(buscarRecetas.this,resultadoBusqueda.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<PostReceta>> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });
    }

    private void busquedaTipo(String tipo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        PostReceta post = new PostReceta("",tipo,"","","");
        Call<List<PostReceta>> call = jsonPlaceHolderApi.buscarRecetaTipo(post);
        call.enqueue(new Callback<List<PostReceta>>() {
            @Override
            public void onResponse(Call<List<PostReceta>> call, Response<List<PostReceta>> response) {
                if (!response.isSuccessful()) {
                    error.setText("No hay resultados");
                    return;
                }
                result = response.body();
                if(result.size()==0){
                    error.setText("No hay resultados");
                    return;
                }
                Intent intent = new Intent(buscarRecetas.this,resultadoBusqueda.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<PostReceta>> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });
    }

    private void busquedaIngrediente(String Ingrediente) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        PostReceta post = new PostReceta("","","",Ingrediente,"");
        Call<List<PostReceta>> call = jsonPlaceHolderApi.buscarRecetaIngrediente(post);
        call.enqueue(new Callback<List<PostReceta>>() {
            @Override
            public void onResponse(Call<List<PostReceta>> call, Response<List<PostReceta>> response) {
                if (!response.isSuccessful()) {
                    error.setText("No hay resultados");
                    return;
                }
                result = response.body();
                if(result.size()==0){
                    error.setText("No hay resultados");
                    return;
                }
                Intent intent = new Intent(buscarRecetas.this,resultadoBusqueda.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<PostReceta>> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });
    }
}
