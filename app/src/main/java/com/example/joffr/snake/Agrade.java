package com.example.joffr.snake;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Agrade extends AppCompatActivity {

    private Boolean running = true;
    TextView pontu;
    GridLayout gl;
    Button cima, baixo, esq, dir;
    ImageButton pausa, continuar;
    ImageView[][] tabuleiro;
    int[] corpo;
    int[] fruta = new int[2];
    int[] sentido = new int[2];
    int tamgrid, dificuldade, count = 0, x, y, pontmax;
    Random gerador;
    ArrayList<int[]> cobra = new ArrayList<int[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrade);

        pontu = (TextView) findViewById((R.id.score));
        gl = (GridLayout) findViewById(R.id.gridLayout);
        cima = (Button) findViewById(R.id.cima);
        baixo = (Button) findViewById(R.id.baixo);
        esq = (Button) findViewById(R.id.esquerda);
        dir = (Button) findViewById(R.id.direita);
        pausa = (ImageButton) findViewById(R.id.botPausa);
        continuar = (ImageButton) findViewById(R.id.botaoContinua);


        //pega valores do bundle, o tamanho que sera a grade e a dificuldade
        Bundle b = getIntent().getExtras();
        tamgrid = b.getInt("tamanhograde");
        dificuldade = b.getInt("dificuldade");
        count = b.getInt("pontumin", 0);
        pontmax = b.getInt("pontMax", 0);
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
        corpo = new int[2];
        corpo[0] = 0;
        corpo[1] = 0;
        cobra.add(0, corpo);

        //posição inicial da fruta
        gerador = new Random();
        NovaFruta();

        //sentido incial (0,1) pra direita
        sentido[0] = 0;
        sentido[1] = 1;


        pontu.setText("Pontuaçãp: " + count);
        startTimerThread();

    }

    private void startTimerThread() {

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(dificuldade);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {

                            //a cobra anda aqui

                            //teste de fruta
                            if (cobra.get(0)[0] == fruta[0] && cobra.get(0)[1] == fruta[1]) {
                                count += 1;
                                pontu.setText("Pontuação: " + count);
                                int ultimapos = cobra.size(); //pega a ultima posicao do array cobra
                                x = cobra.get(ultimapos - 1)[0];
                                y = cobra.get(ultimapos - 1)[1];
                                corpo = new int[2];
                                corpo[0] = x;//pega os parametros de um ponto qualquer
                                corpo[1] = y;
                                cobra.add(ultimapos, corpo); //cria um novo tile da cobra
                                //reposiciona a fruta
                                NovaFruta();

                            }

                            //limpa a tela
                            for (int[] posCobra : cobra) {
                                tabuleiro[posCobra[0]][posCobra[1]].setImageResource(R.color.White);
                            }

                            //muda a posição da cobra OBS: AINDA NAO PINTA NA TELA
                            for (int i = cobra.size() - 1; i >= 0; i--) {
                                //se nao for a cabeça
                                Log.i("Movimento", "i = " + i);
                                if (i != 0) {
                                    //Log.i("Move Tile", "Tile: " + i + " pos antiga: " + cobra.get(i)[1]);
                                    cobra.get(i)[0] = cobra.get(i - 1)[0];
                                    cobra.get(i)[1] = cobra.get(i - 1)[1];
                                    //Log.i("Move Tile", "Tile: " + i + " pos nova: " + cobra.get(i)[1]);
                                } else {
                                    //Log.i("Move cabeça", "Tile: " + i + " pos antiga: " + cobra.get(i)[1]);
                                    cobra.get(i)[0] += sentido[0];
                                    cobra.get(i)[1] += sentido[1];
                                    //Log.i("Move cabeça", "Tile: " + i + " pos nova: " + cobra.get(i)[1]);
                                }
                            }

                            //teste de borda
                            //tratamento de borda
                            //se a cabeça estiver no limite do fundo, redireciona para o extremo do topo
                            if (cobra.get(0)[0] >= tamgrid && cobra.get(0)[1] < tamgrid) {
                                cobra.get(0)[0] = 0;
                            }
                            //se a cabeça estiver no limite da direita, redireciona para o extremo esquerdo
                            else if (cobra.get(0)[0] < tamgrid && cobra.get(0)[1] >= tamgrid) {
                                cobra.get(0)[1] = 0;
                            }
                            //se os dois estiverem nos limites, zera tuto
                            //esse caso nunca acontece, mas vamos deixar aqui por descarne de consciencia
                            else if (cobra.get(0)[0] >= tamgrid && cobra.get(0)[1] >= tamgrid) {
                                cobra.get(0)[0] = 0;
                                cobra.get(0)[1] = 0;
                            }
                            if (cobra.get(0)[0] == -1) {
                                cobra.get(0)[0] = tamgrid - 1;
                            } else if (cobra.get(0)[1] == -1) {
                                cobra.get(0)[1] = tamgrid - 1;
                            }
                            //se nao atender nenhum dos casos, a cobra anda normal

                            //imprime na tela
                            for (int[] snake : cobra) {
                                tabuleiro[snake[0]][snake[1]].setImageResource(R.color.Black);
                            }
                            //Testa de a cobra se tocou
                            for (int i = 1; i <= cobra.size() - 1; i++) {
                                if (cobra.get(0)[0] == cobra.get(i)[0] && cobra.get(0)[1] == cobra.get(i)[1]) {
                                    //pontu.setText("VOCÊ MORREU!");
                                    running = false;
                                    Intent ni = new Intent(Agrade.this, Resultado.class);
                                    ni.putExtra("pontuacao", "" + count);
                                    Intent retorno = new Intent();
                                    if (pontmax <= count) {
                                        retorno = new Intent();
                                        retorno.putExtra("pontMax", count);
                                    } else {
                                        retorno.putExtra("pontMax", pontmax);
                                    }
                                    setResult(RESULT_CANCELED, retorno);
                                    startActivity(ni);
                                    finish();
                                }
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
        if (running)
            running = false;
        else {
            running = true;
            startTimerThread();
        }
    }

    public void Direcao(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.cima:
                sentido[0] = -1;
                sentido[1] = 0;
                baixo.setClickable(false);
                esq.setClickable(true);
                dir.setClickable(true);
                break;
            case R.id.baixo:
                sentido[0] = 1;
                sentido[1] = 0;
                cima.setClickable(false);
                esq.setClickable(true);
                dir.setClickable(true);
                break;
            case R.id.esquerda:
                sentido[0] = 0;
                sentido[1] = 1;
                dir.setClickable(false);
                cima.setClickable(true);
                baixo.setClickable(true);
                break;
            case R.id.direita:
                sentido[0] = 0;
                sentido[1] = -1;
                esq.setClickable(false);
                baixo.setClickable(true);
                cima.setClickable(true);
                break;
        }
    }

    public void Salvar(View v) {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putInt("pontumin", count);
        i.putExtras(b);
        setResult(RESULT_OK, i);
        finish();
    }

    public void NovaFruta() {
        //atribui nova posição
        fruta[0] = gerador.nextInt(tamgrid);
        fruta[1] = gerador.nextInt(tamgrid);
        //ver se esta em cima da cobra
        for (int i = 0; i < cobra.size(); i++) {
            if (fruta[0] == cobra.get(i)[0] && fruta[1] == cobra.get(i)[1]) {
                //se estiver chama a função novameno e interrompe o for
                NovaFruta();
                break;
            }
        }
        tabuleiro[fruta[0]][fruta[1]].setImageResource(R.color.Red);
    }
}
