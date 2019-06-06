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
    private TextView showTxt2;
    private TextView showTxt3;
    private TextView showTxt4;
    private TextView showTxt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_recetas);
        showTxt = (TextView) findViewById(R.id.listarTxt);
        showTxt2 = (TextView) findViewById(R.id.listarTxt2);
        showTxt3 = (TextView) findViewById(R.id.listarTxt3);
        showTxt4 = (TextView) findViewById(R.id.listarTxt4);
        showTxt5 = (TextView) findViewById(R.id.listarTxt5);
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
        imagen1.setVisibility(View.INVISIBLE);
        imagen2.setVisibility(View.INVISIBLE);
        imagen3.setVisibility(View.INVISIBLE);
        imagen4.setVisibility(View.INVISIBLE);
        imagen5.setVisibility(View.INVISIBLE);
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
                        imagen1.setVisibility(View.VISIBLE);
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                        receta1.setVisibility(View.VISIBLE);
                        String content = "";
                        content += "Nombre: "+receta.getNombre().replaceAll("space"," ");
                        showTxt.setText(content);
                    }
                    if(contador==2){
                        imagen2.setVisibility(View.VISIBLE);
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen2);
                        receta2.setVisibility(View.VISIBLE);
                        String content = "";
                        content += "Nombre: "+receta.getNombre().replaceAll("space"," ");
                        showTxt2.setText(content);
                    }
                    if(contador==3){
                        imagen3.setVisibility(View.VISIBLE);
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen3);
                        receta3.setVisibility(View.VISIBLE);
                        String content = "";
                        content += "Nombre: "+receta.getNombre().replaceAll("space"," ");
                        showTxt3.setText(content);
                    }
                    if(contador==4){
                        imagen4.setVisibility(View.VISIBLE);
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen4);
                        receta4.setVisibility(View.VISIBLE);
                        String content = "";
                        content += "Nombre: "+receta.getNombre().replaceAll("space"," ");
                        showTxt4.setText(content);
                    }
                    if(contador==5){
                        imagen5.setVisibility(View.VISIBLE);
                        Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen5);
                        receta5.setVisibility(View.VISIBLE);
                        String content = "";
                        content += "Nombre: "+receta.getNombre().replaceAll("space"," ");
                        showTxt5.setText(content);
                    }
                    contador+=1;
                    //String content = "";
                    //content += "Nombre: "+receta.getNombre() + "\n \n \n";
                    //showTxt.append(content);
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
        showTxt2.setText("");
        showTxt3.setText("");
        showTxt4.setText("");
        showTxt5.setText("");
        multiplicador +=1;
        contador = 1;
        receta1.setVisibility(View.INVISIBLE);
        receta2.setVisibility(View.INVISIBLE);
        receta3.setVisibility(View.INVISIBLE);
        receta4.setVisibility(View.INVISIBLE);
        receta5.setVisibility(View.INVISIBLE);
        imagen1.setVisibility(View.INVISIBLE);
        imagen2.setVisibility(View.INVISIBLE);
        imagen3.setVisibility(View.INVISIBLE);
        imagen4.setVisibility(View.INVISIBLE);
        imagen5.setVisibility(View.INVISIBLE);
        for(int index = 0+(5*multiplicador);index<recetas.size();index++){
            String[] fotos=recetas.get(index).getFoto().split(",");
            if(contador >5){
                break;
            }
            if(contador==1){
                imagen1.setVisibility(View.VISIBLE);
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta1.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre().replaceAll("space"," ");
                showTxt.setText(content);
            }
            if(contador==2){
                imagen2.setVisibility(View.VISIBLE);
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen2);
                receta2.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre().replaceAll("space"," ");
                showTxt2.setText(content);
            }
            if(contador==3){
                imagen3.setVisibility(View.VISIBLE);
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen3);
                receta3.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre().replaceAll("space"," ");
                showTxt3.setText(content);
            }
            if(contador==4){
                imagen4.setVisibility(View.VISIBLE);
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen4);
                receta4.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre().replaceAll("space"," ");
                showTxt4.setText(content);
            }
            if(contador==5){
                imagen5.setVisibility(View.VISIBLE);
                Glide.with(listarRecetas.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen5);
                receta5.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre().replaceAll("space"," ");
                showTxt5.setText(content);
            }
            contador+=1;
            //String content = "";
            //content += "Nombre: "+recetas.get(index).getNombre() + "\n \n \n";
            //showTxt.append(content);
        }
    }

    private void detalle(int posicion){
        String content = "";
        content += "Nombre: "+recetas.get(posicion).getNombre().replaceAll("space"," ") + "\n";
        content += "Tipo: "+recetas.get(posicion).getTipo().replaceAll("space"," ") + "\n";
        content += "Ingredientes: "+recetas.get(posicion).getIngredientes().replaceAll("space"," ") + "\n";
        content += "Pasos: "+recetas.get(posicion).getPasos().replaceAll("space"," ") + "\n";
        //content += "Fotos: "+recetas.get(posicion).getFoto() + "\n";
        Intent intent = new Intent(listarRecetas.this,detalleReceta.class);
        intent.putExtra("Receta",content);
        intent.putExtra("Fotos",recetas.get(posicion).getFoto());
        startActivity(intent);
    }

    public void LoadImageFromWebOperations(String image) {



    }
}
