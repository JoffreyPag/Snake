package com.example.joffr.snake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String SALVASTATUS = "ULTIMOSAVE";
    private static final String PONTUACAO = "PONTMAX";
    private static final String DIFICULDADE = "DIF";
    private static final int CONFS = 11;

    int pontuacao;
    int dif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences save = getSharedPreferences(SALVASTATUS, MODE_PRIVATE);

        pontuacao = save.getInt(PONTUACAO, 0);
        dif = save.getInt(DIFICULDADE, 2);
    }

    public void NovoJogo(View v){
        Intent i = new Intent(this, Configuracoes.class);
        Bundle b = new Bundle();
        b.putInt("pontMax", pontuacao);
        b.putInt("dificuldade", dif);
        i.putExtras(b);
        startActivity(i);
    }

    public void Continuar(View v){
        Intent i = new Intent(this, Configuracoes.class);
        startActivity(i);
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();

    }
    public void Config(View v){
        Intent i = new Intent(this, Configuracoes.class);
        startActivityForResult(i, CONFS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==  CONFS){
            if(resultCode == RESULT_OK){
                dif = data.getIntExtra("dificuldade");
            }
        }
    }
}
