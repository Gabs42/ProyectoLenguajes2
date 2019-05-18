package com.example.appproyectolenguajes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nuevaReceta extends AppCompatActivity {

    private EditText nombre;
    private EditText tipo;
    private EditText ingrediente;
    private EditText paso;
    private EditText foto;
    private Button agregar;
    private TextView error;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);
        nombre = (EditText) findViewById(R.id.nombreTxt);
        tipo = (EditText) findViewById(R.id.tipoTxt);
        ingrediente = (EditText) findViewById(R.id.ingredientesTxt);
        paso = (EditText) findViewById(R.id.instruccionesTxt);
        error = (TextView)findViewById(R.id.errorText);
        foto = (EditText) findViewById(R.id.fotosTxt);

        agregar = (Button) findViewById(R.id.buttonAgregar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().matches("") || tipo.getText().toString().matches("")||ingrediente.getText().toString().matches("")||paso.getText().toString().matches("")||foto.getText().toString().matches("")){
                    error.setText("Porfavor llenar todos los espacios");
                }
                else{
                    agregarReceta(nombre.getText().toString(),tipo.getText().toString(),ingrediente.getText().toString(),paso.getText().toString(),foto.getText().toString());
                }

            }
        });

    }

    private void agregarReceta(String nombre, String tipo, String ingrediente, String paso, String foto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        PostReceta post = new PostReceta(nombre,tipo,ingrediente,paso,foto);
        Call<PostReceta> call = jsonPlaceHolderApi.createReceta(post);
        call.enqueue(new Callback<PostReceta>() {
            @Override
            public void onResponse(Call<PostReceta> call, Response<PostReceta> response) {
                if (!response.isSuccessful()) {
                    error.setText("Code" + response.code());
                    return;
                }
                PostReceta result = response.body();
                error.setText(result.getNombre());
            }

            @Override
            public void onFailure(Call<PostReceta> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });
    }
}
