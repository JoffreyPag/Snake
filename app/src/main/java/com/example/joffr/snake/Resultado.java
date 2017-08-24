package com.example.joffr.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {


    String total;
    TextView pontotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        total = getIntent().getStringExtra("pontuacao");
        pontotal = (TextView)findViewById(R.id.pontuacaodojogo);
        pontotal.setText(total);
    }

    public void Sair(View v){
        finish();
    }
}
