package com.alexmartin.buscagemas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import com.alexmartin.buscagemas.tutorial.TutorialActivity;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment1;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button pickaxe;


    Button explosive;
//    Boolean activeExplosive = true;
//    Boolean activePickaxe = false;

    Button restart;

    AnimationDrawable anim;
    ActivityResultLauncher<Intent> my_ActivityResultLauncher;
    int mode=0;
    int cuantityGems=0;
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
        my_ActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent my_intent_vuelta = result.getData();
                            Bundle bundleVuelta = my_intent_vuelta.getBundleExtra("Bundle");
                            mode = (int) bundleVuelta.get("mode");
                            cuantityGems = (int) bundleVuelta.get("cuantityGems");
                        }
                        else if(result.getResultCode() == Activity.RESULT_CANCELED){
                            Context context = getApplicationContext();
                            Toast.makeText(MainActivity.this, "Se ha establecido la dificultad por defecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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
        builder.setNeutralButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                utilsFile(0);
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
        utilsFile(0);
        Bundle bundle = new Bundle();
        bundle.putInt("mode",mode);
        bundle.putInt("cuantityGems", cuantityGems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openDificultad(View view) {
        Intent intent =new Intent(MainActivity.this, Dificultad.class);
        my_ActivityResultLauncher.launch(intent);
    }

    public void openResultados(View view) {
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
    }
    public void openTutorial(View view) {
        Intent intent=new Intent(MainActivity.this, TutorialActivity.class);
        startActivity(intent);
    }
}