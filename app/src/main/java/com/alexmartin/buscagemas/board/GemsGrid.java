package com.alexmartin.buscagemas.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//CLASE QUE REPRESENTA LA CUADRICULA DE MINAS
public class GemsGrid implements Serializable {
    private ArrayList<Cell> cellsList;
    public int filas;
    public int columnas;
    public GemsGrid(int filas, int columnas){
        this.columnas=columnas;
        this.filas=filas;
        this.cellsList=new ArrayList<>();
        inicializeArray(filas, columnas);
    }

    private void inicializeArray(int filas, int columnas) {
        for (int x = 0; x< filas; x++){
            for(int y = 0; y< columnas; y++) {
                cellsList.add(new Cell(x, y));
            }
        }
    }
    public void placeGems(int totalGems) {
        int gemsPlaced = 0;
        while (gemsPlaced < totalGems) {
            int x = new Random().nextInt(filas);
            int y = new Random().nextInt(columnas);
            int index = toIndex(x, y);
            if (!cellsList.get(index).isHasGem()) {
                cellsList.get(index).setHasGem(true);
                gemsPlaced++;
            }
        }
    }
    public void asingValues(){
        for (int x = 0; x < filas; x++) {
            for (int y = 0; y < columnas; y++) {
                List<Cell> adjacentcellsList = adjacentCells(x, y);
                int countBombs = 0;
                for (Cell cell: adjacentcellsList) {
                    if (cell.isHasGem()) {
                        countBombs++;
                    }
                }
                if (countBombs > 0) {
                    int index = toIndex(x,y);
                    cellsList.get(index).setValue(countBombs);
                }
            }
        }
    }
    public List<Cell> adjacentCells(int x, int y){
        List<Cell> tempListCells = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int tempX = x;
            int tempY = y;
            switch (i){
                //Arriba
                case 0:
                    tempX--;
                    break;
                //Arriba Derecha
                case 1:
                    tempX--;
                    tempY++;
                    break;
                //Derecha
                case 2:
                    tempY++;
                    break;
                //Abajo Derecha
                case 3:
                    tempX++;
                    tempY++;
                    break;
                //Abajo
                case 4:
                    tempX++;
                    break;
                //Abajo Izquierda
                case 5:
                    tempX++;
                    tempY--;
                    break;
                //Izquierda
                case 6:
                    tempY--;
                    break;
                //Arriba Izquierda
                case 7:
                    tempX--;
                    tempY--;
                    break;
            }
            if (tempX >= 0 && tempX < filas && tempY >= 0 && tempY < columnas){
                tempListCells.add(cellAt(tempX, tempY));
            }
        }
        return tempListCells;
    }
    public int toIndex(int x, int y){
        return (this.columnas*x+y);
    }
    public int[] toXY(int index){
        int x, y;
        x = index / this.columnas;
        y = index % this.columnas;
        return new int[]{x,y};
    }
    public Cell cellAt(int x, int y) {
        if (x < 0 || x > filas || y < 0 || y > columnas) {
            return null;
        }
        return cellsList.get(toIndex(x, y));
    }
    public void revealAllBombs(){
        for (Cell cell: cellsList){
            if (cell.isHasGem()){
                cell.setRevealed(true);
            }
        }
    }
    public ArrayList<Cell> getCellsList(){
        return cellsList;
    }
}
