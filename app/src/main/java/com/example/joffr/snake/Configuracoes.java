package com.example.joffr.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Configuracoes extends AppCompatActivity {
    int dif;
    int tamgrid;
    RadioButton rb1, rb2, rb3, rbp, rbg;
    RadioGroup rg1, rg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        Bundle b = getIntent().getExtras();

        rb1 = (RadioButton) findViewById(R.id.fac);
        rb2 = (RadioButton) findViewById(R.id.medio);
        rb3 = (RadioButton) findViewById(R.id.dificil);
        rbp = (RadioButton) findViewById(R.id.peq);
        rbg = (RadioButton) findViewById(R.id.gran);
        rg1 = (RadioGroup) findViewById(R.id.radioGroup);
        rg2 = (RadioGroup) findViewById(R.id.radioGroup2);

        dif = b.getInt("dificuldade");
        tamgrid = b.getInt("tamanhograde");

        if (dif == 750) {
            rb1.setChecked(true);
        } else if (dif == 500) {
            rb2.setChecked(true);
        } else if (dif == 250) {
            rb3.setChecked(true);
        }
        if (tamgrid == 15) {
            rbp.setChecked(true);
        } else if (tamgrid == 35) {
            rbg.setChecked(true);
        }
    }

    public void onRadioButton1Clicked(View view) {
        if (R.id.fac == rg1.getCheckedRadioButtonId()) {
            dif = 750;
        } else if (R.id.medio == rg1.getCheckedRadioButtonId()) {
            dif = 500;
        } else if (R.id.dificil == rg1.getCheckedRadioButtonId()) {
            dif = 250;
        }
    }

    public void onRadioButton2Clicked(View v) {
        if (R.id.peq == rg2.getCheckedRadioButtonId()) {
            tamgrid = 15;
        } else if (R.id.gran == rg2.getCheckedRadioButtonId()) {
            tamgrid = 35;
        }
    }

    public void Alterar(View v) {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putInt("dificuldade", dif);
        b.putInt("tamanhograde", tamgrid);
        i.putExtras(b);
        setResult(RESULT_OK, i);
        finish();
    }

}
