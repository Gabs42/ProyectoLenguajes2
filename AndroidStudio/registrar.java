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

public class registrar extends AppCompatActivity {

    private EditText usuario;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private TextView txt;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        usuario = (EditText) findViewById(R.id.usuarioTxt2);
        password = (EditText) findViewById(R.id.passText2);
        confirmPassword = (EditText) findViewById(R.id.confirmPass);
        register = (Button) findViewById(R.id.register);
        txt = (TextView) findViewById(R.id.displayTxt);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario.getText().toString().matches("") || password.getText().toString().matches("")){
                    txt.setText("Porfavor llenar todos los espacios");
                }
                else if(!(password.getText().toString().matches(confirmPassword.getText().toString()))){
                    txt.setText("Las passwords no concuerdan");
                }
                else{
                    registrarUsuario(usuario.getText().toString(),password.getText().toString());
                }

            }
        });
    }
    private void registrarUsuario(String username,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Post post = new Post(username,password);
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt.setText("Code" + response.code());
                    return;
                }
                txt.setText("Usuario agregado");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt.setText(t.getMessage());
            }
        });
    }
}
