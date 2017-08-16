package com.example.joffr.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Agrade extends AppCompatActivity {

    ImageButton pausa, continuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrade);

        pausa = (ImageButton)findViewById(R.id.botPausa);
        continuar = (ImageButton)findViewById(R.id.botaoContinua);
    }

    public void ClickPause(View v){
        if(pausa.getVisibility() == View.VISIBLE) {
            pausa.setVisibility(View.INVISIBLE);
            continuar.setVisibility(View.VISIBLE);
        }else{
            pausa.setVisibility(View.VISIBLE);
            continuar.setVisibility(View.INVISIBLE);
        }
    }
}
