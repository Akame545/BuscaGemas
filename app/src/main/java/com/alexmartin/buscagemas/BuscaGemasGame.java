package com.alexmartin.buscagemas;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.board.GemsGrid;

import java.util.ArrayList;
import java.util.List;

public class BuscaGemasGame {
    private GemsGrid gemsGrid;
    private boolean isGameOver;
    private int lifes=2;
    public BuscaGemasGame(int mode, int cuantityGems){
        this.isGameOver=false;
        gemsGrid = modeConfiguration(mode);
        gemsGrid.placeGems(gemsAccordingToDifficulty(cuantityGems));
        gemsGrid.asingValues();
    }
    //public void generate
    public void handleCellClick(Cell cell, Boolean tool) {
        if (!isGameOver){
            if(tool) {
                if (cell.isHasGem()){
                    if(lifes == 0) {
                        isGameOver = true;
                    } else lifes--;
                } else clear(cell);
            } else cell.setFlagged(true);
        }
    }
    private GemsGrid modeConfiguration(int mode){
        switch(mode){
            case 0:
                return new GemsGrid(10,10);
            case 1:
                return new GemsGrid(30,10);
            case 2:
                return new GemsGrid(60,10);
        }
        return new GemsGrid(10,10);
    }

    /*METODO PARA LIMPIAR LOS BLOQUES DE CELDAS SIN MINAS */
    public void clear(Cell cell) {
        //obtener el indice de la celda a traves de la lista de celdas
        int index = getGemsGrid().getCellsList().indexOf(cell);
        //a esta celda se le actualiza la variable isRevelated a true
        getGemsGrid().getCellsList().get(index).setRevealed(true);

        // comporbamos si la celda tiene una bomba o no y en caso de que no las meteremos en un arrayList
        if (cell.getValue() == 0 && !cell.isHasGem()) {
            List<Cell> toClear = new ArrayList<>();
            List<Cell> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(cell);

            while (toCheckAdjacents.size() > 0) {
                Cell c = toCheckAdjacents.get(0);
                int cIndex = getGemsGrid().getCellsList().indexOf(c);
                int[] cellPos = getGemsGrid().toXY(cIndex);
                for (Cell adjacentTemp : getGemsGrid().adjacentCells(c.getX(), c.getY())) {
                    //para las celdas adyacentes comprobamos si tienen el valor "blank"
                    if (adjacentTemp.getValue() == 0) {
                        if (!toClear.contains(adjacentTemp)) {
                            if (!toCheckAdjacents.contains(adjacentTemp)) {
                                toCheckAdjacents.add(adjacentTemp);
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacentTemp)) {
                            toClear.add(adjacentTemp);
                        }
                    }
                }
                toCheckAdjacents.remove(c);
                toClear.add(c);
            }

            for (Cell c : toClear) {
                c.setRevealed(true);
            }
        }
    }
    public boolean isGameWon(){
        int cellsUnrevealed=0;
        for (Cell c: getGemsGrid().getCellsList()){
            if(!c.isHasGem() && c.getValue() == 0 && !c.isRevealed()){
                cellsUnrevealed++;
            }
        }
        if (cellsUnrevealed == 0){
            return true;
        } else return false;
    }
    private int gemsAccordingToDifficulty(int cuantityGems){
        switch (cuantityGems){
            case 0:
                return 20;
            case 1:
                return 30;
            case 2:
                return 50;
            case 3:
                return 100;
            case 4:
                return 150;
            case 5:
                return 200;
            case 6:
                return 250;
        }
        return 35;
    }
    public GemsGrid getGemsGrid(){
        return gemsGrid;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getLifes() {
        return lifes;
    }
}
