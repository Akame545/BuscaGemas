package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.board.GemsGrid;
import com.alexmartin.buscagemas.recyclerview.GemsGridRecyclerAdapter;
import com.alexmartin.buscagemas.recyclerview.onCellClickListener;
import com.alexmartin.buscagemas.utilidades.Utilidades;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TableGame extends AppCompatActivity implements onCellClickListener {
    Integer ganar;
    Integer remainingGems;
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
    int score;

    private MediaPlayer au_picaxe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_game);

        au_picaxe = MediaPlayer.create(this,R.raw.a_pickaxe);

        imageLifes = findViewById(R.id.imageLifes);
        timer = findViewById(R.id.timer);
        restart =findViewById(R.id.restart);

        gridRecyclerView = findViewById(R.id.activity_main_grid);

        progressBar = findViewById(R.id.progressBar);

        dynamite = findViewById(R.id.dynamite);
        picaxe = findViewById(R.id.picaxe);

//**************************************************
//        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.tableGame);
//        anim = (AnimationDrawable) container.getBackground();
//        anim.setEnterFadeDuration(7000);
//        anim.setExitFadeDuration(2000);
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
                compLifes();
                gemsGridRecyclerAdapter.setCells(game.getGemsGrid().getCellsList());

            }
        });

        //INICIAR RECYCLERVIEW EN LA ACTIVIDAD
        if(getIntent().getExtras()!=null) {
            mode = (int) getIntent().getExtras().get("mode");
            cuantityGems = (int) getIntent().getExtras().get("cuantityGems");
        }

        GridLayoutManager mLayout = new GridLayoutManager(this, 10);
        gridRecyclerView.setLayoutManager(mLayout);

        if(utilsFile(1)){
            loadFile();
            utilsFile(0);
        } else {

            game = new BuscaGemasGame(mode, cuantityGems);
            seconds = game.getSeconds();
            progressBar.setMax(game.getPicaxeDurability());
            progressBar.setProgress(game.getPicaxeDurability());
        }
        if(game == null) {
            game = new BuscaGemasGame(mode, cuantityGems);
            Toast.makeText(this,"No se ha podido cargar la partida empezada",Toast.LENGTH_SHORT).show();
        }
        mode = game.getMode();
        cuantityGems = game.getCuantityGems();
        compLifes();
        gemsGridRecyclerAdapter = new GemsGridRecyclerAdapter(game.getGemsGrid().getCellsList(), this);
        gridRecyclerView.setAdapter(gemsGridRecyclerAdapter);


        timer.setText(String.valueOf(seconds));
        startTimer(seconds);






        picaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool=false;
                picaxe.setImageDrawable(getDrawable(R.drawable.ic_picofondo));
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
        } else {
            au_picaxe.start();
            game.handleCellClick(cell,false);
        }

        compLifes();

        compGameOver();
        if (game.isGameWon()){
            ganar = 1;
            compScore();
            registrarScore();

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
            cdt.cancel();
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
    private void compGameOver(){
        if (game.isGameOver()){
            imageLifes.setImageDrawable(getDrawable(R.drawable.ic_vidas0));
            ganar = 0;
            remainingGems = game.remainingGems();
            compScore();
            score-=100;
            registrarScore();
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
                    Intent intent = new Intent(TableGame.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            game.getGemsGrid().revealAllBombs();

        }
    }
    private void compScore(){
        score=0;
        score = game.calculateScore();
        switch (game.getLifes()){
            case 1:
                score += 200;
                break;
            case 0:
                score += 100;
                break;
            default:
                score += 300;
        }
        score += Integer.valueOf(timer.getText().toString());
    }
    private void startTimer(int startSeconds){
        cdt = new CountDownTimer(startSeconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                game.setGameOver(true);
                compGameOver();
            }
        }.start();

    }
    //*************************************************************************************
    private void saveFile(){
        FileOutputStream fileOutputStream = null;
        HashMap<String, Object> data = new HashMap<>();
//        for (int i=0; i<game.getGemsGrid().getCellsList().size(); i++){
//            data.put(String.valueOf(i),game.getGemsGrid().getCellsList().get(i));
//        }

        data.put("game", game);
        data.put("progressBar", progressBar.getProgress());
        data.put("seconds", seconds);
        try {
            fileOutputStream = openFileOutput("save.txt", MODE_PRIVATE);
            ObjectOutputStream pipe = new ObjectOutputStream(fileOutputStream);
            pipe.writeObject(data);
            pipe.close();
        } catch (Exception e){
            Toast.makeText(this,"No se ha podido guardar el fichero",Toast.LENGTH_SHORT).show();
        } finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadFile(){
        FileInputStream fileInputStream = null;
        HashMap<String, Object> data = new HashMap<>();
        try{
            fileInputStream = openFileInput("save.txt");
            ObjectInputStream pipe = new ObjectInputStream(fileInputStream);

            data = (HashMap<String, Object>) pipe.readObject();
            game = (BuscaGemasGame) data.get("game");
            progressBar.setMax(game.getPicaxeDurability());
            progressBar.setProgress((int) (data.get("progressBar")));
            seconds = (int) data.get("seconds");
            pipe.close();
        } catch (Exception e){
            Toast.makeText(this,"No se ha podido cargar el fichero",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try{
                    fileInputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
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

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
        saveFile();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveFile();
        cdt.cancel();
    }
    private void registrarScore(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_score",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_GANAR,ganar);
        values.put(Utilidades.CAMPO_TIEMPO,timer.getText().toString());
        values.put(Utilidades.CAMPO_MODO,mode);
        values.put(Utilidades.CAMPO_CANTIDAD_GEMAS,game.gemsAccordingToDifficulty(cuantityGems));
        values.put(Utilidades.CAMPO_VIDAS_RESTANTES,game.getLifes());
        values.put(Utilidades.CAMPO_GEMAS_RESTANTES,remainingGems);
        values.put(Utilidades.CAMPO_FECHA,game.getDate());
        values.put(Utilidades.CAMPO_SCORE,score);

        Long idResultante = db.insert(Utilidades.TABLA_SCORE,Utilidades.CAMPO_GANAR,values);
        Toast.makeText(this,"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();

        db.close();
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
    * */

}