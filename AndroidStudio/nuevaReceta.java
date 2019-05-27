package com.example.appproyectolenguajes;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.io.File;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;

public class nuevaReceta extends AppCompatActivity {

    private EditText nombre;
    private EditText tipo;
    private EditText ingrediente;
    private EditText paso;
    private Button agregar;
    private Button btFoto;
    private  Button subir;
    private  Button tomarFoto;
    private TextView error;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private String url = "https://dry-lake-72290.herokuapp.com/";
    private String imagePath;
    private String fotos = "";
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_TAKE = 200;
    Uri imageUri;
    Uri cameraUri;
    private  int totalImagenes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);
        if(Build.VERSION.SDK_INT>22){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        AWSMobileClient.getInstance().initialize(this).execute();
        nombre = (EditText) findViewById(R.id.nombreTxt);
        tipo = (EditText) findViewById(R.id.tipoTxt);
        ingrediente = (EditText) findViewById(R.id.ingredientesTxt);
        paso = (EditText) findViewById(R.id.instruccionesTxt);
        error = (TextView)findViewById(R.id.errorText);
        btFoto = (Button) findViewById(R.id.buttonElegitFoto);
        agregar = (Button) findViewById(R.id.buttonAgregar);
        subir = (Button) findViewById(R.id.buttonSubir);
        tomarFoto = (Button) findViewById(R.id.buttonTomarFoto);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().matches("") || tipo.getText().toString().matches("")||ingrediente.getText().toString().matches("")||paso.getText().toString().matches("")||fotos.matches("")){
                    error.setText("Porfavor llenar todos los espacios");
                }
                else{
                    String ingredientes = Arrays.toString(ingrediente.getText().toString().split(","));
                    String pasos = Arrays.toString(paso.getText().toString().split(","));
                    String fotosSend = Arrays.toString(fotos.split(","));
                    agregarReceta(nombre.getText().toString().toLowerCase(),tipo.getText().toString().toLowerCase(),ingredientes.toLowerCase(),pasos.toLowerCase(),fotosSend.toLowerCase());
                }

            }
        });
        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFoto();
            }
        });
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadWithTransferUtility();
            }
        });

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,"Comida");
                values.put(MediaStore.Images.Media.DESCRIPTION,"Foto Comida");
                cameraUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                Intent  cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,cameraUri);
                startActivityForResult(cameraIntent,CAMERA_TAKE);
            }
        });
    }

    private void agregarReceta(String nombre, String tipo, String ingrediente, String paso, String foto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        PostReceta post = new PostReceta(nombre,tipo,paso,ingrediente,foto);
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
                fotos = "";
            }

            @Override
            public void onFailure(Call<PostReceta> call, Throwable t) {
                error.setText(t.getMessage());
            }
        });
    }

    public void uploadWithTransferUtility() {

        String filename = "key"+nombre.getText().toString()+Integer.toString(totalImagenes);
        totalImagenes+=1;
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver = transferUtility.upload("public/"+filename, new File(imagePath));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal());
        fotos += filename;
        fotos+=",";

    }
    public void selectFoto(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageUri = data.getData();
            imagePath = getPath(imageUri)+'/';
            error.setText(imagePath);
            //error.setText(getPath(imageUri));
            //error.setText("asd");
        }
        if(resultCode==RESULT_OK && requestCode==CAMERA_TAKE){
            //imageUri = data.getData();
            imagePath = getPath(cameraUri)+'/';
            error.setText(imagePath);
            //error.setText(getPath(imageUri));
            //error.setText("asd");
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
