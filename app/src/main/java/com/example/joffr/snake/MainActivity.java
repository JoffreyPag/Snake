package com.example.joffr.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void NovoJogo(View v){
        Intent i = new Intent(this, Configuracoes.class);
        startActivity(i);
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();

    }

    public void Continuar(View v){
        Intent i = new Intent(this, Configuracoes.class);
        startActivity(i);
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();

    }
    public void Config(View v){
        Intent i = new Intent(this, Configuracoes.class);
        startActivity(i);
    }
}