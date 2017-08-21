package com.example.joffr.snake;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Agrade extends AppCompatActivity {

    GridLayout gl;
    ImageButton pausa, continuar;
    int tamgrid, dificuldade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrade);

        gl = (GridLayout) findViewById(R.id.gridLayout);
        //pausa = (ImageButton)findViewById(R.id.botPausa);
        //continuar = (ImageButton)findViewById(R.id.botaoContinua);

        Bundle b = getIntent().getExtras();
        tamgrid = b.getInt("tamanhograde");
        dificuldade = b.getInt("dificuldade");

        gl.setColumnCount(25 * tamgrid);
        gl.setRowCount(25 * tamgrid);


        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < gl.getRowCount(); i++) {
            for (int j = 0; j < gl.getColumnCount(); j++) {
                ImageView im = (ImageView) inflater.inflate(R.layout.inflate_imageview, gl, false);

                gl.addView(im);

            }
        }

    }

    public void ClickPause(View v) {
        if (pausa.getVisibility() == View.VISIBLE) {
            pausa.setVisibility(View.INVISIBLE);
            continuar.setVisibility(View.VISIBLE);
        } else {
            pausa.setVisibility(View.VISIBLE);
            continuar.setVisibility(View.INVISIBLE);
        }
    }
}
