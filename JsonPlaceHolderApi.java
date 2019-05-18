package com.example.appproyectolenguajes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("user")
    Call<List<Post>> getPost();

    @POST("user/login")
    Call<Post> createLogin(@Body Post post);

    @POST("user")
    Call<Post> createPost(@Body Post post);

    @POST("receta")
    Call<PostReceta> createReceta(@Body PostReceta receta);
}
