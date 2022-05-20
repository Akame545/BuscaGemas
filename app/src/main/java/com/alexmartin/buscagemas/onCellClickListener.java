package com.alexmartin.buscagemas;
//QUEREMOS QUE SE PUEDA HACER CLICK EN LAS CELDAS DE LA CUADRÍCULA
//implementamos esta interfaz en JuegoActivity
public interface onCellClickListener {
    void cellClick(Cell cell, int position);
}