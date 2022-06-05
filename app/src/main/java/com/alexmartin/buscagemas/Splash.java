package com.alexmartin.buscagemas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView icono;
    private MediaPlayer au_intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        au_intro = MediaPlayer.create(this,R.raw.a_intro);
        au_intro.start();
        openMain(true);
        icono = (ImageView) findViewById(R.id.picaxe);
        Animation roatation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        icono.startAnimation(roatation);
    }

    public void openMain(boolean locationPermission){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                //SIRVE PARA CUANDO SE LE DE AL BOTON DE ATRAS SE SALGA DE LA APLICACION EN VEZ DE IR A LA PANTALLA ANTERIOR
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 2700);
    }
}