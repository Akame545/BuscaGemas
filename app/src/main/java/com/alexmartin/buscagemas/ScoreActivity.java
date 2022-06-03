package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.alexmartin.buscagemas.adapter.ScoreAdapter;
import com.alexmartin.buscagemas.entidades.Score;
import com.alexmartin.buscagemas.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    ArrayList<Score> listScore;
    RecyclerView recyclerScore;
    ConexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        conn = new ConexionSQLiteHelper(this,"bd_score",null,1);
        listScore = new ArrayList<>();

        recyclerScore = findViewById(R.id.recyclerId);
        recyclerScore.setLayoutManager(new LinearLayoutManager(this));

        consultarListaScore();
        Collections.reverse(listScore);
        ScoreAdapter adapter = new ScoreAdapter(listScore);
        recyclerScore.setAdapter(adapter);
    }

    private void consultarListaScore() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Score score = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_SCORE,null);

        while (cursor.moveToNext()){
            score = new Score();
            score.setGanar(cursor.getInt(0));
            score.setTiempo(cursor.getInt(1));
            score.setModo(cursor.getInt(2));
            score.setCantidad_gemas(cursor.getInt(3));
            score.setVidas_restantes(cursor.getInt(4));
            score.setGemas_restantes(cursor.getInt(5));
            score.setFecha(cursor.getString(6));
            score.setScore(cursor.getInt(7));

            listScore.add(score);
        }
    }
}