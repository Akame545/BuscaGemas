package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "50 minas seleccionada", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNeutralButton("20 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "20 minas seleccionada", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("35 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "35 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

            }
        });

        builder.setNegativeButton("100 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "100 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

            }
        });

        builder.setNegativeButton("200 Minas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Dificultad.this, "200 minas seleccionada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}