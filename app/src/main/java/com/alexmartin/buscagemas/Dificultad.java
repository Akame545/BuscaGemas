package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dificultad extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

    }

    public void desplegableNormal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_dificultad, null));
        builder.setPositiveButton("50 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(0,2);
            }
        });
        builder.setNeutralButton("20 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "20 minas seleccionada", Toast.LENGTH_SHORT).show();
                salir(0,0);
            }
        });
        builder.setNegativeButton("35 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "35 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                salir(0,1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void desplegableDificil(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_dificultad, null));
        builder.setPositiveButton("130 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "130 minas seleccionada", Toast.LENGTH_SHORT).show();
                salir(1,4);
            }
        });

        builder.setNegativeButton("100 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "100 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                salir(1,3);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void desplegableMuyDificil(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_dificultad, null));
        builder.setPositiveButton("280 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "280 minas seleccionada", Toast.LENGTH_SHORT).show();
                salir(2,6);
            }
        });

        builder.setNegativeButton("200 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "200 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                salir(2,5);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void salir(int mode, int cuantityGems){
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putInt("cuantityGems", cuantityGems);
        Intent my_intent2 = new Intent();
        my_intent2.putExtra("Bundle", bundle);

        setResult(Activity.RESULT_OK, my_intent2);
        this.finish();
    }
}