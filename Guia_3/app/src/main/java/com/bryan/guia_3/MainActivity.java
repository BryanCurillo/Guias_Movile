package com.bryan.guia_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "Esta es mi primera APP";
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;

    //Declaramos un objeto firebase Auth
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.txtEmail);
        TextPassword = (EditText) findViewById(R.id.txtPassword);

        btnRegistrar = (Button) findViewById(R.id.btnSend);
        btnLogin = (Button) findViewById(R.id.btn_IniciarSession);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    public void registrarUsuario(View view) {
        //Obtenemos el id y contraseña
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Comprobamos que no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registrando, por favor espere...");
        progressDialog.show();

        //crear nuevo usuario

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registro completado", Toast.LENGTH_LONG).show();
//                           sendMessage(view);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {// si hay coincidencia
                                Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSend:
                registrarUsuario(view);
                break;

            case R.id.btn_IniciarSession:
                loginUser(view);
                break;
        }
    }


    public void loginUser(View view) {
        //Obtenemos el id y contraseña
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Comprobamos que no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingrese un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Iniciando Sesion, por favor espere...");
        progressDialog.show();

        //logear usuario

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "BIENVENIDO", Toast.LENGTH_LONG).show();
                            sendMessage(view);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {// si hay coincidencia
                                Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, Diseno.class);

        EditText editText = (EditText) findViewById(R.id.txtEmail);
//        int pos = editText.toString().indexOf("@");
//        String user = editText.toString().substring(0, pos);
//        System.out.println(user);
        String message = "Hola " + editText.getText().toString();

        intent.putExtra("email", message);

        startActivity(intent);
    }


}