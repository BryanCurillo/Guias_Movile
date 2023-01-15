package com.bryan.guia_3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class Camara extends AppCompatActivity {

    String rutaImagen;
    Button btnCamara;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        btnCamara = findViewById(R.id.btnFoto);
        imgView = findViewById(R.id.imgCamara);
         btnCamara.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 tomarFoto(view);
             }
         });
    }

    @SuppressWarnings("deprecation")

    public void tomarFoto(View view) {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamara.resolveActivity(getPackageManager()) != null) {

            File imagenArchivo = null;

            try {
                imagenArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("ERROR: ", ex.toString());
            }

            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "com.bryan.guia_3.fileprovider", imagenArchivo);
                intentCamara.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamara, 1);
            }
        }
    }

//    public void tomarFoto() throws IOException {
//        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intentCamara.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intentCamara, 1);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

//            Bundle info = data.getExtras();
//            Bitmap imagen = (Bitmap) info.get("data");
            Bitmap imagen = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imagen);
//            ImageView imageView = findViewById(R.id.imgCamara);
//            imageView.setImageBitmap(imagen);
        }
    }


    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
//        try {
//            imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

}