package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Dificultad extends AppCompatActivity{
    Button btn_normal;
    Button btn_hard;
    Button btn_veryHard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_dificulty);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#E3AF50"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        buttonInicializer();

    }

    private void buttonInicializer() {
        btn_normal = findViewById(R.id.btn_normal);
        btn_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalDropdown(v);
            }
        });
        btn_hard = findViewById(R.id.btn_hard);
        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardDropdown(v);
            }
        });
        btn_veryHard = findViewById(R.id.btn_veryHard);
        btn_veryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veryHardDropdown(v);
            }
        });
    }

    public void normalDropdown(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_difficulty, null));
        builder.setPositiveButton("20 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(0,2);
            }
        });
        builder.setNeutralButton("10 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(0,0);
            }
        });
        builder.setNegativeButton("15 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(0,1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void hardDropdown(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_difficulty, null));
        builder.setPositiveButton("60 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(1,4);
            }
        });

        builder.setNeutralButton("50 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(1,3);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void veryHardDropdown(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.alert_dialog_difficulty, null));
        builder.setPositiveButton("90 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salir(2,6);
            }
        });

        builder.setNeutralButton("80 Gemas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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