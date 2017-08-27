package com.example.joffr.snake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String SALVASTATUS = "ULTIMOSAVE";
    private static final String PONTUACAO = "PONTMAX";
    private static final String DIFICULDADE = "DIF";
    private static final String TAMGRANDE = "TAMANHODAGRADE";
    private static final String PONTUSAVE = "SAVEPONTU";
    private static final String CONTINUE = "CONTINUE";
    private static final String ORIENTACAOSALVA = "SENTIDO";
    private static final String VETORPOSICOES = "VETORCOBRA";
    private static final int CONFS = 11;
    private static final int ENDGAME = 22;

    String posicoes="", sentido="";
    int pontuacao,pontumin, dif, tamgrade;
    int var[];

    boolean continuar;

    Button conti;

    TextView record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conti = (Button)findViewById(R.id.button2);
        record = (TextView)findViewById(R.id.reco);

        SharedPreferences save = getSharedPreferences(SALVASTATUS, MODE_PRIVATE);

        pontuacao = save.getInt(PONTUACAO, 0);
        dif = save.getInt(DIFICULDADE, 500);
        tamgrade = save.getInt(TAMGRANDE, 15);
        continuar = save.getBoolean(CONTINUE, false);
        pontumin = save.getInt(PONTUSAVE, 0);
        posicoes = save.getString(VETORPOSICOES, "");
        sentido = save.getString(ORIENTACAOSALVA, "");

        record.setText(""+pontuacao);
        if(continuar){
            conti.setVisibility(View.VISIBLE);
        }else{
            conti.setVisibility(View.INVISIBLE);
        }

    }

    public void NovoJogo(View v) {
        Intent i = new Intent(this, Agrade.class);
        Bundle b = new Bundle();
        b.putInt("pontMax", pontuacao);
        b.putInt("dificuldade", dif);
        b.putInt("tamanhograde", tamgrade);
        i.putExtras(b);
        startActivityForResult(i, ENDGAME);
    }

    public void Continuar(View v) {
        Intent i = new Intent(this, Agrade.class);
        Bundle b = new Bundle();
        b.putInt("pontumin", pontumin);
        b.putInt("pontMax", pontuacao);
        b.putInt("dificuldade", dif);
        b.putInt("tamanhograde", tamgrade);
        b.putString("posicoes", posicoes);
        b.putString("sentido", sentido);
        i.putExtras(b);
        startActivityForResult(i, ENDGAME);

    }

    public void Config(View v) {
        Intent i = new Intent(this, Configuracoes.class);
        Bundle b = new Bundle();
        b.putInt("dificuldade", dif);
        b.putInt("tamanhograde", tamgrade);
        i.putExtras(b);
        startActivityForResult(i, CONFS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONFS) {
            if (resultCode == RESULT_OK) {
                int novadif = data.getIntExtra("dificuldade", 500);
                int novotam = data.getIntExtra("tamanhograde", 15);
                if(novadif != dif || novotam != tamgrade){
                    continuar = false;
                    conti.setVisibility(View.INVISIBLE);
                }
                dif = novadif;
                tamgrade = novotam;

            }
        }else if(requestCode == ENDGAME){
            if (resultCode == RESULT_OK){
                pontumin = data.getIntExtra("pontumin", 0); //PONTUACAO CONQUISTADA ATE O MOMENTO
                 //posicoes salvas
                posicoes = data.getStringExtra("posicoes");
                sentido = data.getStringExtra("sentido");
                continuar = true;
                conti.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Jogo salvo", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                pontuacao = data.getIntExtra("pontMax",0);
                record.setText(""+pontuacao);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sp = getSharedPreferences(SALVASTATUS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PONTUACAO, pontuacao);
        editor.putInt(DIFICULDADE, dif);
        editor.putInt(TAMGRANDE, tamgrade);
        editor.putInt(PONTUSAVE, pontumin);
        editor.putBoolean(CONTINUE, continuar);
        editor.putString(VETORPOSICOES, posicoes);
        editor.putString(ORIENTACAOSALVA, sentido);

        editor.commit();
    }
}
