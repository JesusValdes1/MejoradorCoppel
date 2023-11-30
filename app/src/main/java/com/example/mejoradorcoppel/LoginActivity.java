package com.example.mejoradorcoppel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView crearCuentaBtnTextV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        crearCuentaBtnTextV = findViewById(R.id.crear_cuenta_TV_btn);

        loginBtn.setOnClickListener((v) -> ingresoUsuario());
        crearCuentaBtnTextV.setOnClickListener((v) -> startActivity(new Intent(LoginActivity.this, CrearCuentaActivity.class)));
    }

    void ingresoUsuario(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean esValido = validacionDatos(email,password);
        if(!esValido){
            return;
        }
        IngresarUsuarioEnFirebase(email,password);
    }
    void IngresarUsuarioEnFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        cambioProceso(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                cambioProceso(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        Utility.showToast(LoginActivity.this,"Correo no verificado, por favor verificalo");
                    }
                }else{
                    Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void cambioProceso(boolean enProceso){
        if(enProceso){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validacionDatos(String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo invalido");
            return false;
        }
        if(password.length()<6){
            passwordEditText.setError("Contraseña no coincide");
            return false;
        }
        return true;
    }
}