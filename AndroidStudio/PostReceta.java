package com.example.appproyectolenguajes;

public class PostReceta {
    String nombre;
    String tipo;
    String pasos;
    String ingredientes;
    String foto;

    public PostReceta(String nombre, String tipo, String pasos, String ingredientes, String foto) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
        this.foto = foto;
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
}
