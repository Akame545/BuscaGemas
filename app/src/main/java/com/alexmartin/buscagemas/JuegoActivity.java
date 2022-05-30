package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.recyclerview.MineGridRecyclerAdapter;
import com.alexmartin.buscagemas.recyclerview.onCellClickListener;

public class JuegoActivity extends AppCompatActivity implements onCellClickListener {

    /*//prueba de zoom
    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR=1.0f;*/


    /*    Configuramos el adaptador y el RecyclerView en el método onCreate*/
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    BuscaGemasJuego juego;
    ImageButton smiley;
    ImageButton pico;
    AnimationDrawable anim;
    ImageButton dinamita;
    Boolean activeExplosive = true;
    Boolean activePickaxe = false;
    ProgressBar progressBar;
    Handler handler = new Handler();
    ImageView imageVidas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        progressBar = findViewById(R.id.progressBar);

        imageVidas = findViewById(R.id.imagevidas);

        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.juego);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(7000);
        anim.setExitFadeDuration(2000);

        pico = findViewById(R.id.pico);
        dinamita = findViewById(R.id.dinamita);




        smiley=findViewById(R.id.restart);

        smiley.setOnClickListener(new View.OnClickListener() {
            //crearemos un nuevo juego
            @Override
            public void onClick(View view) {
                juego = new BuscaGemasJuego(20, 10);
                mineGridRecyclerAdapter.setCells(juego.getMineGrid().getCells());
            }
        });

        //INICIAR RECYCLERVIEW EN LA ACTIVIDAD
        gridRecyclerView = findViewById(R.id.activity_main_grid);
//        10=Tamaño del grid (10x10)
        GridLayoutManager mLayout=new GridLayoutManager(this, 10);
        gridRecyclerView.setLayoutManager(mLayout);
        juego=new BuscaGemasJuego(10, 40);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(juego.getMineGrid().getCells(), this);
        //set the recycler adapter on the RecyclerView
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);


        /*//ZOOM
        scaleGestureDetector=new ScaleGestureDetector(this,new ScaleListener());*/

//         Instanciacion y eventos onClick de Herramientas
        pico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activeExplosive = false;
                activePickaxe = true;
            }
        });
        dinamita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeExplosive = true;
                activePickaxe = false;
            }
        });
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

    // a este metodo le he añadido un parametro (position)
    @Override
    public void cellClick(Cell cell, int position){
        //Toast.makeText(getApplicationContext(), "Cell clicked & "+position, Toast.LENGTH_LONG).show();
        //nos permite cambiar el estado de una celda a "isRevelated"
        if(activeExplosive){
            juego.handleCellClick(cell,true);
        } else juego.handleCellClick(cell,false);

        switch (juego.getLifes()){
            case 1:
                imageVidas.setImageDrawable(getDrawable(R.drawable.ic_vidas2));
                break;
            case 0:
                imageVidas.setImageDrawable(getDrawable(R.drawable.ic_vidas1));
                break;
            default:
                imageVidas.setImageDrawable(getDrawable(R.drawable.ic_vidas3));
        }

        if (juego.isGameOver()){
            imageVidas.setImageDrawable(getDrawable(R.drawable.ic_vidas0));
            Toast.makeText(getApplicationContext(), "Has Perdido",Toast.LENGTH_SHORT).show();
            juego.getMineGrid().revealAllBombs();
        }
        if (juego.isGameWon()){
            Toast.makeText(getApplicationContext(), "Has Ganado!!!!",Toast.LENGTH_SHORT).show();
            juego.getMineGrid().revealAllBombs();
        }

        mineGridRecyclerAdapter.setCells(juego.getMineGrid().getCells());
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

}