package com.alexmartin.buscagemas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Dificultad extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    Button normal, dificil, muyDificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

//        normal = findViewById(R.id.normal);
//        registerForContextMenu(normal);

    }

    public void desplegableNormal(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.opciones_normal);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.veinte:
                Toast.makeText(this, "20 minas seleccionada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.treintaCinco:
                Toast.makeText(this, "35 minas seleccionada", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "50 minas seleccionada", Toast.LENGTH_SHORT).show();
                return false;
        }
    }
    //    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Elige cuantas minas quieres que haya:");
//        getMenuInflater().inflate(R.menu.opciones_normal, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.veinte:
//                Toast.makeText(this, "20 minas seleccionada", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.treintaCinco:
//                Toast.makeText(this, "35 minas seleccionada", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(this, "50 minas seleccionada", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onContextItemSelected(item);
//    }
}