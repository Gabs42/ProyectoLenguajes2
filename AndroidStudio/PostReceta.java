package com.example.appproyectolenguajes;

public class PostReceta {
    String nombre;
    String tipo;
    String pasos;
    String ingredientes;
    String foto;
    String token;

    public PostReceta(String nombre, String tipo, String pasos, String ingredientes, String foto,String token) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
        this.foto = foto;
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPasos() {
        return pasos;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getFoto() {
        return foto;
    }

    public String getToken() {
        return token;
    }

}
