package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
    TextView smiley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        smiley=findViewById(R.id.restart);
        smiley.setOnClickListener(new View.OnClickListener() {
            //crearemos un nuevo juego
            @Override
            public void onClick(View view) {
                juego = new BuscaGemasJuego(10, 10);
                mineGridRecyclerAdapter.setCells(juego.getMineGrid().getCells());
            }
        });

        //INICIAR RECYCLERVIEW EN LA ACTIVIDAD
        gridRecyclerView = findViewById(R.id.activity_main_grid);
//        10=Tamaño del grid (10x10)
        GridLayoutManager mLayout=new GridLayoutManager(this, 10);
        gridRecyclerView.setLayoutManager(mLayout);
        juego=new BuscaGemasJuego(10, 10);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(juego.getMineGrid().getCells(), this);
        //set the recycler adapter on the RecyclerView
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);


        /*//ZOOM
        scaleGestureDetector=new ScaleGestureDetector(this,new ScaleListener());*/
    }

    // a este metodo le he añadido un parametro (position)
    @Override
    public void cellClick(Cell cell, int position){
        //Toast.makeText(getApplicationContext(), "Cell clicked & "+position, Toast.LENGTH_LONG).show();
        //nos permite cambiar el estado de una celda a "isRevelated"
        juego.handleCellClick(cell);


        if (juego.isGameOver()){
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