package com.alexmartin.buscagemas;

import java.util.ArrayList;
import java.util.List;

public class BuscaGemasJuego {
    private MineGrid mineGrid;
    private boolean clearMode;


    public BuscaGemasJuego(int size, int numberOfBombs, int spanCount){
        this.clearMode=true;
        mineGrid=new MineGrid(size, spanCount);
        mineGrid.generateGrid(numberOfBombs);
    }

    public void handleCellClick(Cell cell) {
        if (clearMode) {
            clear(cell);
        }
    }

    /*METODO PARA LIMPIAR LOS BLOQUES DE CELDAS SIN MINAS */
    public void clear(Cell cell) {
        //obtener el indice de la celda a traves de la lista de celdas
        int index = getMineGrid().getCells().indexOf(cell);
        //a esta celda se le actualiza la variable isRevelated a true
        getMineGrid().getCells().get(index).setRevealed(true);

        /* comporbamos si la celda tiene una bomba o no y en caso de que no las meteremos en un
        arrayList */
        /*if (cell.getValue() == Cell.BOMB) {
            gameOver = true;
        } else */if (cell.getValue() == Cell.BLANK) {
            List<Cell> toClear = new ArrayList<>();
            List<Cell> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(cell);

            while (toCheckAdjacents.size() > 0) {
                Cell c = toCheckAdjacents.get(0);
                int cellIndex = getMineGrid().getCells().indexOf(c);
                int[] cellPos = getMineGrid().toXY(cellIndex);
                for (Cell adjacent: getMineGrid().adjacentCells(cellPos[0], cellPos[1])) {
                    //para las celdas adyacentes comprobamos si tienen el valor "blank"
                    if (adjacent.getValue() == Cell.BLANK) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent);
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent);
                        }
                    }
                }
                toCheckAdjacents.remove(c);
                toClear.add(c);
            }

            for (Cell c: toClear) {
                c.setRevealed(true);
            }
        }
    }

    public MineGrid getMineGrid(){
        return mineGrid;
    }
}
