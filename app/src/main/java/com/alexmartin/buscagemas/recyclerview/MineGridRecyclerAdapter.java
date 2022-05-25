package com.alexmartin.buscagemas.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexmartin.buscagemas.R;
import com.alexmartin.buscagemas.board.Cell;

import java.util.List;

//Clase Adaptador para el MineGrid
public class MineGridRecyclerAdapter extends RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder> {
    private List<Cell> cells;
    private onCellClickListener listener;

    public MineGridRecyclerAdapter(List<Cell> cells, onCellClickListener listener) {
        this.cells = cells;
        this.listener = listener;
    }

    //  crea los nuevos objetos ViewHolder necesarios para los elementos de la colección.
    @NonNull
    @Override
    public MineTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new MineTileViewHolder(itemView);
    }

    //Es el encargado de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(@NonNull MineTileViewHolder holder, int position) {
        holder.bind(cells.get(position), position);

        holder.setIsRecyclable(false);
    }

    //Nos indica el numero de elementos de la coleccion de datos.
    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }

    class MineTileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell, int position) {
            itemView.setBackgroundColor(Color.GRAY);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cellClick(cell,position);
                }
            });

//            Toast.makeText(getActivity().getApplicationContext(), "position:"+position, Toast.LENGTH_SHORT).show();

            /*nos permite mostrar el valor dentro de cada celda (textView). Si comentamos el primer if y su
            else podremos ver el valor de cada celda*/
            if (cell.isRevealed()) {
                if (cell.getValue() == Cell.BOMB) {
                    valueTextView.setText(R.string.bomb);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    /* si la celda es un nº, este sera de un color u otro dependiendo de cual sea */
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    if (cell.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                    } else if (cell.getValue() == 2) {
                        valueTextView.setTextColor(Color.GREEN);
                    } else if (cell.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED);
                    }
                }
            } /*else if (cell.isFlagged()) {
                valueTextView.setText(R.string.flag);
            }*/
        }
    }
}
