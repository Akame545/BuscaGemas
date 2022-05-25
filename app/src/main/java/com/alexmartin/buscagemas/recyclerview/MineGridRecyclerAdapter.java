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
import java.util.concurrent.BlockingQueue;

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
            valueTextView.setBackgroundResource(R.drawable.roca);
            //itemView.setBackgroundResource(R.drawable.roca);

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
                    valueTextView.setBackgroundResource(R.drawable.joya_rosa);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    /* si la celda es un nº, este sera de un color u otro dependiendo de cual sea */
                    //valueTextView.setText(String.valueOf(cell.getValue()));
                    switch (cell.getValue()){
                        case 1: valueTextView.setBackgroundResource(R.drawable.bloque1);
                            break;
                        case 2: valueTextView.setBackgroundResource(R.drawable.bloque2);
                            break;
                        case 3: valueTextView.setBackgroundResource(R.drawable.bloque3);
                            break;
                        case 4: valueTextView.setBackgroundResource(R.drawable.bloque4);
                            break;
                        case 5: valueTextView.setBackgroundResource(R.drawable.bloque5);
                            break;
                        case 6: valueTextView.setBackgroundResource(R.drawable.bloque6);
                            break;
                        case 7: valueTextView.setBackgroundResource(R.drawable.bloque7);
                            break;
                        default: valueTextView.setBackgroundResource(R.drawable.bloque8);
                    }

                }
            } else if (cell.isFlagged()) {
                valueTextView.setBackgroundResource(R.drawable.joya_azul);

            }
        }
    }
}
