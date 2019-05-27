package com.example.appproyectolenguajes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;


public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText password;
    private Button login;
    private Button register;
    private TextView txt;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        usuario = (EditText) findViewById(R.id.usuarioTxt);
        password = (EditText) findViewById(R.id.passText);
        login = (Button) findViewById(R.id.loginButton);
        txt = (TextView) findViewById(R.id.loginTxt);
        register = (Button) findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario(usuario.getText().toString(), password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario.getText().toString().matches("") || password.getText().toString().matches("")){
                    txt.setText("Porfavor llenar todos los espacios");
                }
                else{
                    registrarUsuario(usuario.getText().toString(),password.getText().toString());
                }

            }
        });
    }

    private void loginUsuario(String username,String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Post post = new Post(username,password);
        Call <Post> call = jsonPlaceHolderApi.createLogin(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt.setText("Code" + response.code());
                    return;
                }
                Post login = response.body();
                if(login.getUsername().equals("Accepted")){
                    Intent intent = new Intent(MainActivity.this,Menu.class);
                    startActivity(intent);
                }
                if(login.getUsername().equals("Rejected")){
                    //txt.setText("Yikes");
                }
                else{
                    txt.setText("Login boys");
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt.setText(t.getMessage());
            }
        });
        txt.setText("No existe usuario o contraseña incorrecta");
    }

    private void registrarUsuario(String username,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Post post = new Post(username,password);
        Call <Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt.setText("Code" + response.code());
                    return;
                }
                txt.setText("Usuario agregado 2");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt.setText(t.getMessage());
            }
        });
    }
}
