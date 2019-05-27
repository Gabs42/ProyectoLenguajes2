package com.example.appproyectolenguajes;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;;


public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        RequestOptions requestOptions = new RequestOptions() ;
        builder.setDefaultRequestOptions(requestOptions.format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
