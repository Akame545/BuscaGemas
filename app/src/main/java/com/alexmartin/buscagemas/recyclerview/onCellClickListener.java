package com.alexmartin.buscagemas.recyclerview;

import com.alexmartin.buscagemas.board.Cell;

//QUEREMOS QUE SE PUEDA HACER CLICK EN LAS CELDAS DE LA CUADRÍCULA
//implementamos esta interfaz en JuegoActivity
public interface onCellClickListener {
    void cellClick(Cell cell);
}