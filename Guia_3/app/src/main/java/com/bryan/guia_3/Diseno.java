package com.bryan.guia_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Diseno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseno);

        Intent intent = getIntent();
        String message = intent.getStringExtra("email");

        TextView textView = findViewById(R.id.txtemail);
        textView.setText(message);
    }

    public void sendMessage2(View view){
        Intent intent= new Intent(this, DisplayMessageActivity.class);

        EditText editText=(EditText) findViewById(R.id.txtPrueba);

        String message= editText.getText().toString();

        intent.putExtra("mensaje",message);

        startActivity(intent);
    }

    public void openCamera(View view){
        Intent intent= new Intent(this, Camara.class);

        startActivity(intent);
    }

}