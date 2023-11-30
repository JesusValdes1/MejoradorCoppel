package com.example.mejoradorcoppel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CrearCuentaActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Button createCuentaBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createCuentaBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);

        createCuentaBtn.setOnClickListener(v -> crearCuenta());
        loginBtnTextView.setOnClickListener(v -> finish());
    }
    void crearCuenta(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean esValido = validacionDatos(email,password,confirmPassword);
        if(!esValido){
            return;
        }
        CrearCuentaEnFirebase(email,password);
    }
    void CrearCuentaEnFirebase(String email,String password){
        cambioProceso(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CrearCuentaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Utility.showToast(CrearCuentaActivity.this,"Cuenta creada, verifica correo email");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else{
                    Utility.showToast(CrearCuentaActivity.this,task.getException().getLocalizedMessage());
                }
            }
        }
        );
    }
    void cambioProceso(boolean enProceso){
        if(enProceso){
            progressBar.setVisibility(View.VISIBLE);
            createCuentaBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            createCuentaBtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validacionDatos(String email,String password,String confirmPassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo invalido");
            return false;
        }
        if(password.length()<6){
            passwordEditText.setError("Contraseña no coincide");
            return false;
        }
        if(!password.equals(confirmPassword)){
            passwordEditText.setError("Contraseña no coincide");
            return false;
        }
        return true;
    }
 }
