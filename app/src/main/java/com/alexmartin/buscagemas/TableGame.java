package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.recyclerview.GemsGridRecyclerAdapter;
import com.alexmartin.buscagemas.recyclerview.onCellClickListener;

public class TableGame extends AppCompatActivity implements onCellClickListener {

    BuscaGemasGame game;

    ImageView imageLifes;
    //aqui va el timer
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

    //Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_game);

        imageLifes = findViewById(R.id.imageLifes);
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

                game = new BuscaGemasGame(mode, cuantityGems);
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

        // *****************************************************************************************************************************
        //                                              AQUI VA PROGRESSBARR
        // *****************************************************************************************************************************

        picaxe.setOnClickListener(v -> tool=false);
        dynamite.setOnClickListener(v -> tool=true);
    }

    @Override
    public void cellClick(Cell cell){

        if(tool){
            game.handleCellClick(cell,true);
        } else game.handleCellClick(cell,false);

        compLifes();

        if (game.isGameOver()){
            imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas0));
            // *****************************************************************************************************************************
            //                                            AQUI VA EL ALERTDIALOG DE PERDER LA PARTIDA
            // *****************************************************************************************************************************


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_lost, null));
            builder.setPositiveButton("Aceptar", null);
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

            //Toast.makeText(getApplicationContext(), "Has Perdido",Toast.LENGTH_SHORT).show();
            game.getGemsGrid().revealAllBombs();
        }
        if (game.isGameWon()){
            // *****************************************************************************************************************************
            //                                            AQUI VA EL ALERTDIALOG DE GANAR LA PARTIDA
            // *****************************************************************************************************************************

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_win, null));

            builder.setPositiveButton("Si", null);

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
            //Toast.makeText(getApplicationContext(), "Has Ganado!!!!",Toast.LENGTH_SHORT).show();
            game.getGemsGrid().revealAllBombs();
        }

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
    /*ZOOM  *//*
    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            FACTOR*=detector.getScaleFactor();
            FACTOR=Math.max(0.1f, Math.min(FACTOR, 10.f));
            gridRecyclerView.setScaleX(FACTOR);
            gridRecyclerView.setScaleY(FACTOR);
            return true;
        }
    }
*/
// Starting animation:- start the animation on onResume.
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
}