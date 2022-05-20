package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alexmartin.buscagemas.board.Board;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button pickaxe;
    Boolean activePickaxe = false;
    Button explosive;
    Boolean activeExplosive = true;

    Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Instanciacion y eventos onClick de Herramientas
        pickaxe = findViewById(R.id.button);
        explosive = findViewById(R.id.button);
        pickaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeExplosive = false;
                activePickaxe = true;
            }
        });
        explosive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeExplosive = true;
                activePickaxe = false;
            }
        });

        btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board b = new Board(8,8,30);
                b.printMap();
                System.out.println("-------------------------------");
                b.printHint();
            }
        });
    }
    //Método para comprobar que herramienta se esta usando
    private void checkTool(){
        if(activeExplosive){

        } else {

        }
    }
    //Usar evento onTouch para

    public void openGame(View view) {

        //VERSION ANTERIOR
        /*Intent intent = new Intent(MainActivity.this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/

        Intent intent =new Intent(MainActivity.this, JuegoActivity.class);
        startActivity(intent);
    }
}