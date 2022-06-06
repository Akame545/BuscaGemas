package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
import com.alexmartin.buscagemas.utilidades.Utilidades;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TableGame extends AppCompatActivity implements onCellClickListener {
    Integer ganar = -1;
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
    int mode = 0;
    int quantityGems = 0;
    int seconds;
    CountDownTimer cdt;
    int score;
    private MediaPlayer au_picaxe;
    private MediaPlayer au_dynamite;
    private MediaPlayer au_lose;
    private MediaPlayer au_win;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_game);

        variableInicializer();


        //INICIAR RECYCLERVIEW EN LA ACTIVIDAD
        if(getIntent().getExtras()!=null) {
            mode = (int) getIntent().getExtras().get("mode");
            quantityGems = (int) getIntent().getExtras().get("cuantityGems");
        }

        GridLayoutManager mLayout = new GridLayoutManager(this, 10);
        gridRecyclerView.setLayoutManager(mLayout);

        if(utilsFile(1)){
            loadFile();
            utilsFile(0);
        } else {
            game = new BuscaGemasGame(mode, quantityGems);
            seconds = game.getSeconds();
            progressBar.setMax(game.getPicaxeDurability());
            progressBar.setProgress(game.getPicaxeDurability());
        }
        if(game == null) {
            game = new BuscaGemasGame(mode, quantityGems);
            Toast.makeText(this,"No se ha podido cargar la partida empezada",Toast.LENGTH_SHORT).show();
        }
        mode = game.getMode();
        quantityGems = game.getQuantityGems();
        compLifes();
        gemsGridRecyclerAdapter = new GemsGridRecyclerAdapter(game.getGemsGrid().getCellsList(), this);
        gridRecyclerView.setAdapter(gemsGridRecyclerAdapter);


        timer.setText(String.valueOf(seconds));
        startTimer(seconds);


        listeners();
    }

    private void listeners() {
        restart.setOnClickListener(view -> {
            progressBar.setProgress(progressBar.getMax());
            cdt.cancel();
            game = new BuscaGemasGame(mode, quantityGems);
            seconds = game.getSeconds();
            timer.setText(String.valueOf(seconds));
            startTimer(seconds);
            compLifes();
            gemsGridRecyclerAdapter.setCells(game.getGemsGrid().getCellsList());

        });
        picaxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool=false;
                picaxe.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.ic_picaxe_focused));
                dynamite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.ic_dynamite));
            }
        });
        dynamite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool=true;
                dynamite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.ic_dynamite_focused));
                picaxe.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.ic_picaxe));
            }
        });
    }

    private void variableInicializer() {
        au_picaxe = MediaPlayer.create(this,R.raw.a_pickaxe);
        au_dynamite = MediaPlayer.create(this,R.raw.a_dynamite);
        au_lose = MediaPlayer.create(this,R.raw.a_lose);
        au_win = MediaPlayer.create(this,R.raw.a_win);

        imageLifes = findViewById(R.id.imageLifes);
        timer = findViewById(R.id.timer);
        restart =findViewById(R.id.restart);

        gridRecyclerView = findViewById(R.id.activity_main_grid);

        progressBar = findViewById(R.id.progressBar);

        dynamite = findViewById(R.id.dynamite);
        picaxe = findViewById(R.id.picaxe);
    }

    @Override
    public void cellClick(Cell cell){

        if(tool){
            au_dynamite.start();
            game.handleCellClick(cell,true);
        } else {
            au_picaxe.start();
            game.handleCellClick(cell,false);
        }

        compLifes();

        compGameOver();
        compGameWon();
        progressBar.setProgress(game.getPicaxeDurability());
        gemsGridRecyclerAdapter.setCells(game.getGemsGrid().getCellsList());
    }
    private void compLifes(){
        switch (game.getLifes()){
            case 1:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_life2));
                break;
            case 0:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_life1));
                break;
            default:
                imageLifes.setImageDrawable(getDrawable(R.drawable.ic_life3));
        }
    }
    private void compGameWon() {
        if (game.isGameWon()){
            au_win.start();
            ganar = 1;
            compScore();
            score+=200;
            registrarScore();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_win, null));

            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart.callOnClick();
                }
            });
            builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
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
    }
    private void compGameOver(){
        if (game.isGameOver()){
            imageLifes.setImageDrawable(getDrawable(R.drawable.ic_life0));
            au_lose.start();
            ganar = 0;
            remainingGems = game.remainingGems();
            compScore();
            score-=200;
            registrarScore();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_lost, null));
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart.callOnClick();
                }
            });
            builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
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
        if(!game.isGameOver()) {
            score += Integer.valueOf(timer.getText().toString());
        }
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
    private void saveFile(){
        FileOutputStream fileOutputStream = null;
        HashMap<String, Object> data = new HashMap<>();
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
        if(ganar<0) {
            saveFile();
        }
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
        values.put(Utilidades.CAMPO_CANTIDAD_GEMAS,game.gemsAccordingToDifficulty(quantityGems));
        values.put(Utilidades.CAMPO_VIDAS_RESTANTES,game.getLifes());
        values.put(Utilidades.CAMPO_GEMAS_RESTANTES,remainingGems);
        values.put(Utilidades.CAMPO_FECHA,game.getDate());
        values.put(Utilidades.CAMPO_SCORE,score);
        db.insert(Utilidades.TABLA_SCORE,Utilidades.CAMPO_GANAR,values);
        db.close();
    }
}