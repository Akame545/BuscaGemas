package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

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
        if(utilsFile(1)){
            createAlertDialog();
        }
    }
    private void createAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_load_game, null));
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, TableGame.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private Boolean utilsFile(int uf){
        File file = new File("/data/data/com.alexmartin.buscagemas/files/save.txt");
        switch (uf){
            case 0:
                if (file.delete()){
                    file.delete();
                    return true;
                }
                else return false;
            case 1:
                if(file.isFile())
                    return true;
                else return false;
        }
        return null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    public void openGame(View view) {
        Intent intent =new Intent(MainActivity.this, TableGame.class);
        // *****************************************************************************************************************************
        //                                              HAY QUE HACER UN SELECTOR DE DIFICULTAD
        // *****************************************************************************************************************************
        utilsFile(0);
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