package com.example.mejoradorcoppel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class CambiarTema extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_tema);

        findViewById(R.id.btn_default).setOnClickListener(this);
        findViewById(R.id.btn_default2).setOnClickListener(this);
        findViewById(R.id.btn_rojo).setOnClickListener(this);
        findViewById(R.id.btn_cafe).setOnClickListener(this);
        findViewById(R.id.btn_celeste).setOnClickListener(this);
        findViewById(R.id.btn_morado).setOnClickListener(this);
        findViewById(R.id.btn_naranja).setOnClickListener(this);
        findViewById(R.id.btn_rosa).setOnClickListener(this);
        findViewById(R.id.btn_gris).setOnClickListener(this);
        findViewById(R.id.btn_amarillo).setOnClickListener(this);
        findViewById(R.id.btn_verde).setOnClickListener(this);
        findViewById(R.id.btn_azul).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


    }
}