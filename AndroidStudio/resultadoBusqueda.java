package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class resultadoBusqueda extends AppCompatActivity {

    List<PostReceta> recetas = buscarRecetas.result;
    int contador = 1;
    int multiplicador = -1;
    private Button receta1;
    private Button receta2;
    private Button receta3;
    private Button receta4;
    private Button receta5;
    private Button siguiente;
    private TextView txt;
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
        setContentView(R.layout.activity_resultado_busqueda);
        txt = (TextView) findViewById(R.id.listarResultadotxt);
        showTxt2 = (TextView) findViewById(R.id.listarResultadoTxt2);
        showTxt3 = (TextView) findViewById(R.id.listarResultadoTxt3);
        showTxt4 = (TextView) findViewById(R.id.listarResultadoTxt4);
        showTxt5 = (TextView) findViewById(R.id.listarResultadoTxt5);
        receta1 = (Button) findViewById(R.id.buttonResultado1);
        receta2 = (Button) findViewById(R.id.buttonResultado2);
        receta3 = (Button) findViewById(R.id.buttonResultado3);
        receta4 = (Button) findViewById(R.id.buttonResultado4);
        receta5 = (Button) findViewById(R.id.buttonResultado5);
        siguiente = (Button) findViewById(R.id.buttonResultadoSiguiente);
        imagen1 = (ImageView) findViewById(R.id.imageResultado1);
        imagen2 = (ImageView) findViewById(R.id.imageResultado2);
        imagen3 = (ImageView) findViewById(R.id.imageResultado3);
        imagen4 = (ImageView) findViewById(R.id.imageResultado4);
        imagen5 = (ImageView) findViewById(R.id.imageResultado5);
        receta1.setVisibility(View.INVISIBLE);
        receta2.setVisibility(View.INVISIBLE);
        receta3.setVisibility(View.INVISIBLE);
        receta4.setVisibility(View.INVISIBLE);
        receta5.setVisibility(View.INVISIBLE);

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
        mostrarSiguientes();

    }

    private void mostrarSiguientes() {
        txt.setText("");
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
        for(int index = 0+(5*multiplicador);index<recetas.size();index++){
            String[] fotos = recetas.get(index).getFoto().split(",");
            if(contador >5){
                break;
            }
            if(contador==1){
                Glide.with(resultadoBusqueda.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen1);
                receta1.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre();
                txt.setText(content);
            }
            if(contador==2){
                Glide.with(resultadoBusqueda.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen2);
                receta2.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre();
                showTxt2.setText(content);
            }
            if(contador==3){
                Glide.with(resultadoBusqueda.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen3);
                receta3.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre();
                showTxt3.setText(content);
            }
            if(contador==4){
                Glide.with(resultadoBusqueda.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen4);
                receta4.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre();
                showTxt4.setText(content);
            }
            if(contador==5){
                Glide.with(resultadoBusqueda.this).asBitmap().load("https://s3.amazonaws.com/proyectolenguajes-userfiles-mobilehub-1737542905/public/"+fotos[0]).into(imagen5);
                receta5.setVisibility(View.VISIBLE);
                String content = "";
                content += "Nombre: "+recetas.get(index).getNombre();
                showTxt5.setText(content);
            }
            contador+=1;
            //String content = "";
            //content += "Nombre: "+recetas.get(index).getNombre() + "\n \n \n";
            //txt.append(content);
        }
    }
    private void detalle(int posicion){
        String content = "";
        content += "Nombre: "+recetas.get(posicion).getNombre() + "\n";
        content += "Tipo: "+recetas.get(posicion).getTipo() + "\n";
        content += "Ingredientes: "+recetas.get(posicion).getIngredientes() + "\n";
        content += "Pasos: "+recetas.get(posicion).getPasos() + "\n";
        //content += "Fotos: "+recetas.get(posicion).getFoto() + "\n";
        Intent intent = new Intent(resultadoBusqueda.this,detalleReceta.class);
        intent.putExtra("Receta",content);
        intent.putExtra("Fotos",recetas.get(posicion).getFoto());
        startActivity(intent);
    }
}
