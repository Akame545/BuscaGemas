package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button pickaxe;

    Button explosive;
//    Boolean activeExplosive = true;
//    Boolean activePickaxe = false;

    Button restart;

    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.main);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);

        // Instanciacion y eventos onClick de Herramientas
//        pickaxe = findViewById(R.id.button);
//        explosive = findViewById(R.id.button);
//        pickaxe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activeExplosive = false;
//                activePickaxe = true;
//            }
//        });
//        explosive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activeExplosive = true;
//                activePickaxe = false;
//            }
//        });

//        btn1 = findViewById(R.id.button);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Board b = new Board(8,8,30);
//                b.printMap();
//                System.out.println("-------------------------------");
//                b.printHint();
//            }
//        });
    }

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


    //Método para comprobar que herramienta se esta usando
//    private void checkTool(){
//        if(activeExplosive){
//
//        } else {
//
//        }
//    }
    //Usar evento onTouch para

    public void openGame(View view) {

        //VERSION ANTERIOR
        /*Intent intent = new Intent(MainActivity.this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/

        Intent intent =new Intent(MainActivity.this, TableGame.class);
        // *****************************************************************************************************************************
        //                                              HAY QUE HACER UN SELECTOR DE DIFICULTAD
        // *****************************************************************************************************************************
        Bundle bundle = new Bundle();
        bundle.putInt("mode",0);
        bundle.putInt("cuantityGems", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openDificultad(View view) {
        Intent intent =new Intent(MainActivity.this, Dificultad.class);
        startActivity(intent);
    }

    public void openResultados(View view) {
        Intent intent =new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
    }
}