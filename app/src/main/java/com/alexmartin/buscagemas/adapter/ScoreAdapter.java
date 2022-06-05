package com.alexmartin.buscagemas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexmartin.buscagemas.R;
import com.alexmartin.buscagemas.entidades.Score;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{

    ArrayList<Score> listaScore;

    public ScoreAdapter(ArrayList<Score> listaScore) {
        this.listaScore = listaScore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_record,null,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.score.setText(listaScore.get(position).getScore().toString());
        holder.tiempo.setText(listaScore.get(position).getTiempo().toString()+" seg");
        switch( listaScore.get(position).getModo()){
            case 0:
                holder.modo.setText("Normal");
                break;
            case 1:
                holder.modo.setText("Dificil");
                break;
            case 2:
                holder.modo.setText("Muy dificil");
                break;
        }
        holder.gemas.setText(listaScore.get(position).getGemas_restantes().toString()+"/"+listaScore.get(position).getCantidad_gemas().toString());
        holder.fecha.setText(listaScore.get(position).getFecha());

    }

    @Override
    public int getItemCount() {
        return listaScore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView score, tiempo, modo, gemas, fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            score = itemView.findViewById(R.id.score);
            tiempo = itemView.findViewById(R.id.tiempo);
            modo = itemView.findViewById(R.id.modo);
            gemas = itemView.findViewById(R.id.gemas);
            fecha = itemView.findViewById(R.id.fecha);

        }
    }
}
