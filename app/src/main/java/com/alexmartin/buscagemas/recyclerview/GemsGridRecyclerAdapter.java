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
public class GemsGridRecyclerAdapter extends RecyclerView.Adapter<GemsGridRecyclerAdapter.MineTileViewHolder> {
    private List<Cell> cells;
    private onCellClickListener listener;

    public GemsGridRecyclerAdapter(List<Cell> cells, onCellClickListener listener) {
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
            int color1 = valueTextView.getResources().getColor(R.color.beage);
            int color2 = valueTextView.getResources().getColor(R.color.azulado);
            itemView.setBackgroundResource(R.drawable.ic_roca);
            //valueTextView.setBackgroundResource(R.drawable.ic_roca);
            //itemView.setBackgroundResource(R.drawable.roca);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cellClick(cell);
                }
            });
            /*nos permite mostrar el valor dentro de cada celda (textView). Si comentamos el primer if y su
            else podremos ver el valor de cada celda*/
            if (cell.isRevealed()) {
                if (cell.isHasGem()) {
                    int joyas;

                    switch (cell.getValue()) {
                        case 0: case 1: case 2:
                            itemView.setBackgroundResource(R.drawable.ic_joyaazul);
                            break;
                        case 3: case 4:
                            itemView.setBackgroundResource(R.drawable.ic_joyaverde);
                            break;
                        default:
                            itemView.setBackgroundResource(R.drawable.ic_joyarosa);
                    }
                } else if (cell.getValue() == 0) {
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                    valueTextView.setTextColor(color1);
                }
            } else if (cell.isFlagged()) {
                itemView.setBackgroundResource(R.drawable.ic_joyaazulrota);
            }
        }
    }

}
