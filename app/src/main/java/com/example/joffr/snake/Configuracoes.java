package com.example.joffr.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Configuracoes extends AppCompatActivity {
    int dif;
    int tamgrid;
    RadioButton rb1, rb2, rb3, rbp, rbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        Bundle b = getIntent().getExtras();

        rb1 = (RadioButton)findViewById(R.id.fac);
        rb2 = (RadioButton)findViewById(R.id.medio);
        rb3 = (RadioButton)findViewById(R.id.dificil);
        rbp = (RadioButton)findViewById(R.id.peq);
        rbg = (RadioButton)findViewById(R.id.gran);

        if(b.getInt("dificuldade") == 1){
            dif = 1;
            rb1.setChecked(true);
        } else if(b.getInt("dificuldade") == 2){
            dif= 2;
            rb2.setChecked(true);
        }else if(b.getInt("dificuldade") == 3){
            dif = 3;
            rb3.setChecked(true);
        }
        if(b.getInt("tamanhograde") == 1){
            tamgrid = 1;
            rbp.setChecked(true);
        }else if(b.getInt("tamanhograde") == 2){
            tamgrid = 2;
            rbg.setChecked(true);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.fac:
                if (checked) {
                    dif = 1;
                    rb2.setChecked(false);
                    rb3.setChecked(false);
                }
                    break;
            case R.id.medio:
                if (checked) {
                    dif = 2;
                    rb1.setChecked(false);
                    rb3.setChecked(false);
                }
                break;
            case R.id.dificil:
                if(checked) {
                    dif = 3;
                    rb1.setChecked(false);
                    rb2.setChecked(false);
                }
                break;
            case R.id.peq:
                if(checked){
                    tamgrid = 1;
                    rbg.setChecked(false);
                }
                break;
            case R.id.gran:
                if(checked){
                    tamgrid = 2;
                    rbp.setChecked(false);
                }
            break;
        }
    }

    public void Alterar(View v){
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putInt("dificuldade", dif);
        b.putInt("tamanhograde", tamgrid);
        i.putExtras(b);
        setResult(RESULT_OK, i);
        finish();
    }

    public void voltar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}
