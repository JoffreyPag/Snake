package com.example.joffr.snake;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Agrade extends AppCompatActivity {

    private Boolean running = true;
    TextView pontu;
    GridLayout gl;
    ImageButton pausa, continuar;
    ImageView[][] tabuleiro;
    int[] corpo = new int[2];
    int[] fruta = new int[2];
    int tamgrid, dificuldade, count = 0;
    ArrayList<int[]> cobra = new ArrayList<int[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrade);

        pontu = (TextView) findViewById((R.id.score));
        gl = (GridLayout) findViewById(R.id.gridLayout);
        //pausa = (ImageButton)findViewById(R.id.botPausa);
        //continuar = (ImageButton)findViewById(R.id.botaoContinua);


        //pega valores do bundle, o tamanho que sera a grade e a dificuldade
        Bundle b = getIntent().getExtras();
        tamgrid = b.getInt("tamanhograde");
        dificuldade = b.getInt("dificuldade");

        //tamanho da grade XY baseado no bundle recebido
        tabuleiro = new ImageView[tamgrid][tamgrid];
        gl.setColumnCount(tamgrid);
        gl.setRowCount(tamgrid);

        //inflando o grid layout com image viewers
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < gl.getRowCount(); i++) {
            for (int j = 0; j < gl.getColumnCount(); j++) {
                tabuleiro[i][j] = (ImageView) inflater.inflate(R.layout.inflate_imageview, gl, false);
                gl.addView(tabuleiro[i][j]);
            }
        }

        //pinta um ponto qualquer
        //tabuleiro[20][20].setImageResource(R.color.Black);

        //criando a cabeça e add na posição 0;
        corpo[0] = 0;
        corpo[1] = 0;
        cobra.add(0, corpo);

        //posição inicial da fruta
        fruta[0] = 0;
        fruta[1] = 5;

        tabuleiro[fruta[0]][fruta[1]].setImageResource(R.color.Red);

        //System.out.println(cobra.size());
        startTimerThread();

    }

    private void startTimerThread() {

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {

                            //a cobra anda aqui
                            //se a cabeça nao chegou no limite da grade
                            if (cobra.get(0)[1] < tamgrid - 1) {

                                tabuleiro[cobra.get(0)[0]][cobra.get(0)[1]].setImageResource(R.color.White);
                                cobra.get(0)[1] += 1;
                                tabuleiro[cobra.get(0)[0]][cobra.get(0)[1]].setImageResource(R.color.Black);

                                //fazer o resto d corpo seguir seu superior
                                for (int i = 1; i < cobra.size(); i++) {
                                    tabuleiro[cobra.get(i)[0]][cobra.get(i)[1]].setImageResource(R.color.White);
                                    //se seu superior tiver na posição 0, sua posição tem que ser no outro lado do mapa
                                    //isso so ocorre se a cobra estiver muito grande...
                                    if (cobra.get(i - 1)[1] - 1 < 0) {
                                        cobra.get(i)[1] = tamgrid - 1;
                                    } else {
                                        //se sey superior nao tiver na pos 0 ele realoca pra uma posição atras
                                        cobra.get(i)[1] = cobra.get(i - 1)[1] - 1;
                                    }
                                    tabuleiro[cobra.get(i)[0]][cobra.get(i)[1]].setImageResource(R.color.Black);

                                }
                            }
                            //se a cobra chegou no limite da grade
                            else {
                                tabuleiro[cobra.get(0)[0]][cobra.get(0)[1]].setImageResource(R.color.White);
                                cobra.get(0)[1] = 0;
                                tabuleiro[cobra.get(0)[0]][cobra.get(0)[1]].setImageResource(R.color.Black);
                                //fazer o resto d corpo seguir seu superior
                                for (int i = 1; i < cobra.size(); i++) {
                                    tabuleiro[cobra.get(i)[0]][cobra.get(i)[1]].setImageResource(R.color.White);
                                    //se seu superior tiver na posição 0, sua posição tem que ser no outro lado do mapa
                                    if (cobra.get(i - 1)[1] - 1 < 0) {
                                        cobra.get(i)[1] = tamgrid - 1;
                                    } else {
                                        //se sey superior nao tiver na pos 0 ele realoca pra uma posição atras
                                        cobra.get(i)[1] = cobra.get(i - 1)[1] - 1;
                                    }
                                    tabuleiro[cobra.get(i)[0]][cobra.get(i)[1]].setImageResource(R.color.Black);
                                }

                            }
                            //se a cabela estiver no mesmo lugar que a fruta
                            if (cobra.get(0)[0] == fruta[0] && cobra.get(0)[1] == fruta[1]) {
                                int ultimapos = cobra.size();
                                //o novo corpo recebe as mesmas coordenadas da ultima parte do corpo
                                corpo[0] = cobra.get(ultimapos - 1)[0];
                                corpo[1] = cobra.get(ultimapos - 1)[1];
                                cobra.add(ultimapos, corpo);
                                count += 50;
                                pontu.setText("Potuação: " + count);
                                //reposicionei a fruta pra tirar um buguinho
                                fruta[0] = 5;
                                fruta[1] = 5;
                                tabuleiro[fruta[0]][fruta[1]].setImageResource(R.color.Red);
                            }

                        }
                    });
                }
            }
        }).start();
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
