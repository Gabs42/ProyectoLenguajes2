package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listarRecetas extends AppCompatActivity {
    private TextView showTxt;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    private String imageUrl="https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/s3Folder/";
    List<PostReceta> recetas;
    int contador = 1;
    int multiplicador = 0;
    private Button receta1;
    private Button receta2;
    private Button receta3;
    private Button receta4;
    private Button receta5;
    private Button siguiente;
    private ImageView imagen1;
    private ImageView imagen2;
    private ImageView imagen3;
    private ImageView imagen4;
    private ImageView imagen5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_recetas);
        showTxt = (TextView) findViewById(R.id.listarTxt);
        receta1 = (Button) findViewById(R.id.buttonReceta1);
        receta2 = (Button) findViewById(R.id.buttonReceta2);
        receta3 = (Button) findViewById(R.id.buttonReceta3);
        receta4 = (Button) findViewById(R.id.buttonReceta4);
        receta5 = (Button) findViewById(R.id.buttonReceta5);
        siguiente = (Button) findViewById(R.id.buttonSiguiente);
        imagen1 = (ImageView) findViewById(R.id.imageView1);
        imagen2 = (ImageView) findViewById(R.id.imageView2);
        imagen3 = (ImageView) findViewById(R.id.imageView3);
        imagen4 = (ImageView) findViewById(R.id.imageView4);
        imagen5 = (ImageView) findViewById(R.id.imageView5);
        receta1.setVisibility(View.INVISIBLE);
        receta2.setVisibility(View.INVISIBLE);
        receta3.setVisibility(View.INVISIBLE);
        receta4.setVisibility(View.INVISIBLE);
        receta5.setVisibility(View.INVISIBLE);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<PostReceta>> call = jsonPlaceHolderApi.getRecetas();
        call.enqueue(new Callback<List<PostReceta>>() {
            @Override
            public void onResponse(Call<List<PostReceta>> call, Response<List<PostReceta>> response) {
                if(!response.isSuccessful()){
                    showTxt.setText("No hay recetas registradas");
                    return;
                }
                showTxt.setText("");
                recetas = response.body();

                for(PostReceta receta : recetas){
                    String[] fotos = receta.getFoto().split(",");
                    if(contador >5){
                        break;
                    }
                    if(contador==1){
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                        receta1.setVisibility(View.VISIBLE);
                    }
                    if(contador==2){
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen2);
                        receta2.setVisibility(View.VISIBLE);
                    }
                    if(contador==3){
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen3);
                        receta3.setVisibility(View.VISIBLE);
                    }
                    if(contador==4){
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen4);
                        receta4.setVisibility(View.VISIBLE);
                    }
                    if(contador==5){
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen5);
                        receta5.setVisibility(View.VISIBLE);
                    }
                    contador+=1;
                    String content = "";
                    content += "Nombre: "+receta.getNombre() + "\n \n \n";
                    showTxt.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostReceta>> call, Throwable t) {
                showTxt.setText(t.getMessage());

            }
        });
        receta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle(0 + (5*multiplicador));
            }
        });

        receta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle(1+(5*multiplicador));
            }
        });

        receta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle(2 +(5*multiplicador));
            }
        });

        receta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle(3+(5*multiplicador));
            }
        });

        receta5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle(4+(5*multiplicador));
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSiguientes();
            }
        });
    }

    private void mostrarSiguientes() {
        showTxt.setText("");
        multiplicador +=1;
        contador = 1;
        receta1.setVisibility(View.INVISIBLE);
        receta2.setVisibility(View.INVISIBLE);
        receta3.setVisibility(View.INVISIBLE);
        receta4.setVisibility(View.INVISIBLE);
        receta5.setVisibility(View.INVISIBLE);
        for(int index = 0+(5*multiplicador);index<recetas.size();index++){
            String[] fotos=recetas.get(index).getFoto().split(",");
            if(contador >5){
                break;
            }
            if(contador==1){
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta1.setVisibility(View.VISIBLE);
            }
            if(contador==2){
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta2.setVisibility(View.VISIBLE);
            }
            if(contador==3){
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta3.setVisibility(View.VISIBLE);
            }
            if(contador==4){
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta4.setVisibility(View.VISIBLE);
            }
            if(contador==5){
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta5.setVisibility(View.VISIBLE);
            }
            contador+=1;
            String content = "";
            content += "Nombre: "+recetas.get(index).getNombre() + "\n \n \n";
            showTxt.append(content);
        }
    }

    private void detalle(int posicion){
        String content = "";
        content += "Nombre: "+recetas.get(posicion).getNombre() + "\n";
        content += "Tipo: "+recetas.get(posicion).getTipo() + "\n";
        content += "Ingredientes: "+recetas.get(posicion).getIngredientes() + "\n";
        content += "Pasos: "+recetas.get(posicion).getPasos() + "\n";
        //content += "Fotos: "+recetas.get(posicion).getFoto() + "\n";
        Intent intent = new Intent(listarRecetas.this,detalleReceta.class);
        intent.putExtra("Receta",content);
        intent.putExtra("Fotos",recetas.get(posicion).getFoto());
        startActivity(intent);
    }

    public void LoadImageFromWebOperations(String image) {



    }
}
