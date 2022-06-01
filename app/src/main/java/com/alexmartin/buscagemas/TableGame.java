package com.alexmartin.buscagemas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.recyclerview.GemsGridRecyclerAdapter;
import com.alexmartin.buscagemas.recyclerview.onCellClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TableGame extends AppCompatActivity implements onCellClickListener {

    BuscaGemasGame game;

    ImageView imageLifes;
    TextView timer;
    ImageButton restart;

    RecyclerView gridRecyclerView;
    GemsGridRecyclerAdapter gemsGridRecyclerAdapter;

    ProgressBar progressBar;

    ImageButton dynamite;
    Boolean tool = true; //True = dinamita, False = pico
    ImageButton picaxe;

    AnimationDrawable anim;
    int mode;
    int cuantityGems;
    int seconds;
    boolean refresh=false;
    CountDownTimer cdt;
    //Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_game);

        imageLifes = findViewById(R.id.imageLifes);
        timer = findViewById(R.id.timer);
        restart =findViewById(R.id.restart);

        gridRecyclerView = findViewById(R.id.activity_main_grid);

        progressBar = findViewById(R.id.progressBar);

        dynamite = findViewById(R.id.dynamite);
        picaxe = findViewById(R.id.picaxe);

//**************************************************
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.tableGame);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(7000);
        anim.setExitFadeDuration(2000);
//****************************************************



        restart.setOnClickListener(new View.OnClickListener() {
            //crearemos un nuevo juego
            @Override
            public void onClick(View view) {
                progressBar.setProgress(progressBar.getMax());
                cdt.cancel();
                game = new BuscaGemasGame(mode, cuantityGems);
                seconds = game.getSeconds();
                timer.setText(String.valueOf(seconds));
                startTimer(seconds);
                gemsGridRecyclerAdapter.setCells(game.getGemsGrid().getCellsList());

            }
        });

        //INICIAR RECYCLERVIEW EN LA ACTIVIDAD
        mode = (int) getIntent().getExtras().get("mode");
        cuantityGems = (int) getIntent().getExtras().get("cuantityGems");

        GridLayoutManager mLayout = new GridLayoutManager(this, 10);
        gridRecyclerView.setLayoutManager(mLayout);
        game = new BuscaGemasGame(mode, cuantityGems);

        gemsGridRecyclerAdapter = new GemsGridRecyclerAdapter(game.getGemsGrid().getCellsList(), this);
        gridRecyclerView.setAdapter(gemsGridRecyclerAdapter);
        seconds = game.getSeconds();
        timer.setText(String.valueOf(seconds));
        startTimer(seconds);

        progressBar.setMax(game.getPicaxeDurability());
        progressBar.setProgress(game.getPicaxeDurability());


        picaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool=false;
               // picaxe.setImageDrawable(getDrawable(R.drawable.ic_picofondo));
                dynamite.setImageDrawable(getDrawable(R.drawable.ic_dinamita));
            }
        });
        dynamite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool=true;
                dynamite.setImageDrawable(getDrawable(R.drawable.ic_dinamitafondo));
                picaxe.setImageDrawable(getDrawable(R.drawable.ic_pico));
            }
        });
    }

    @Override
    public void cellClick(Cell cell){

        if(tool){
            game.handleCellClick(cell,true);
        } else game.handleCellClick(cell,false);

        compLifes();

        if (game.isGameOver()){
            imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas0));
            cdt.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_lost, null));
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart.callOnClick();
                }
            });
            builder.setNegativeButton("Volver al menu principal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something like...
                    Intent intent = new Intent(TableGame.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            game.getGemsGrid().revealAllBombs();
        }
        if (game.isGameWon()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_win, null));

            builder.setPositiveButton("Si", null);

            builder.setNegativeButton("Volver al menu principal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(TableGame.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            game.getGemsGrid().revealAllBombs();
        }
        progressBar.setProgress(game.getPicaxeDurability());
        gemsGridRecyclerAdapter.setCells(game.getGemsGrid().getCellsList());
    }
    private void compLifes(){
        switch (game.getLifes()){
            case 1:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas2));
                break;
            case 0:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas1));
                break;
            default:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas3));
        }
    }
    private void startTimer(int startSeconds){
        cdt = new CountDownTimer(startSeconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds--;
                timer.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                game.setGameOver(true);
            }
        }.start();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
    /*
    * persistencia en fichero para cargar partida no terminada
    *
    *           objeto game, tiempo, durabilidad
    *
    * persistencia en sqlite para mostrar anteriores resultados
    *
    *           tiempo, modo, cantidad gemas, vidas restantes, score
    *
    * tutorial
    *
    *       viewpager2, como en el anterior proyecto, con un numero de fragmentos deslizables de izquierda a derecha con dotsindicator
    *          cada fragmento se compone de una imagen
    *
    * cuando se reinicia la partida, se pongan todas las vidas
    * */

}