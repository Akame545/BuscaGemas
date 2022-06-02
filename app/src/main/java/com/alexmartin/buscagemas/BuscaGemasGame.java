package com.alexmartin.buscagemas;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.board.GemsGrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuscaGemasGame implements Serializable {
    private GemsGrid gemsGrid;
    private boolean isGameOver;
    private int lifes=2;
    private int picaxeDurability;
    private int seconds;
    private int mode;
    private int cuantityGems;
    public BuscaGemasGame(int mode, int cuantityGems){
        this.isGameOver=false;
        this.mode = mode;
        this.cuantityGems = cuantityGems;
        gemsGrid = modeConfiguration(mode);
        gemsGrid.placeGems(gemsAccordingToDifficulty(cuantityGems));
        gemsGrid.asingValues();
    }
    //public void generate
    public void handleCellClick(Cell cell, Boolean tool) {
        if (!isGameOver){
            if(tool) {
                if (cell.isHasGem()){
                    cell.setRevealed(true);
                    if(lifes == 0) {
                        isGameOver = true;

                    } else lifes--;
                } else clear(cell);
            } else {
                cell.setFlagged(true);
                picaxeDurability--;
                if(picaxeDurability==0){
                    isGameOver=true;
                }
            }
        }
    }
    private GemsGrid modeConfiguration(int mode){
        switch(mode){
            case 0:
                this.seconds = 100;
                return new GemsGrid(10,10);
            case 1:
                this.seconds = 150;
                return new GemsGrid(30,10);
            case 2:
                this.seconds = 200;
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
        this.picaxeDurability=20;
        switch (cuantityGems){
            case 0:
                return 15;
            case 1:
                return 25;
            case 2:
                return 50;
            case 3:
                return 70;
            case 4:
                return 100;
            case 5:
                return 100;
            case 6:
                return 200;
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

    public int getPicaxeDurability() {
        return picaxeDurability;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getMode() {
        return mode;
    }

    public int getCuantityGems() {
        return cuantityGems;
    }
}
