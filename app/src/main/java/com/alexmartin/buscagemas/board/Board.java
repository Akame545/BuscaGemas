package com.alexmartin.buscagemas.board;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final String TAG = "BOARD:  ";
    List<List<Box>> map = new ArrayList<>();
    int numRows;
    int numColums;
    int numGems;
    public Board(int numRows, int numColums,int numGems) {
        this.numRows = numRows;
        this.numColums = numColums;
        this.numGems = numGems;
        setUpMap();
    }

    public void setUpMap(){
        for (int i = 0; i < numRows; i++) {
            map.add(new ArrayList<Box>());
            for (int j = 0; j < numColums; j++) {
                map.get(i).add(new Box(i,j));
            }
        }
        gemGenerator();
    }
    private void gemGenerator(){
        int gemsCount=0;
        while(gemsCount!=numGems){
            int posTmpRow = (int) (Math.random()*(numRows));
            int posTmpColum = (int) (Math.random()*(numColums));
            if (!map.get(posTmpColum).get(posTmpRow).getHasGem()){
                map.get(posTmpColum).get(posTmpRow).setHasGem(true);
                gemsCount++;
            }
        }
        reloadGems();
    }
    //Cargar pistas alrededor de las minas
    private void reloadGems(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColums; j++) {
                if (map.get(i).get(j).getHasGem()){
                    List<Box> listBoxes = aroundBoxes(i,j);
                    for (Box tempBox : listBoxes) {
                        tempBox.incrementHintGemsArount();
                    }
                }
            }

        }
    }
    private List<Box> aroundBoxes(int row,int colum ){
        List<Box> tempListBoxes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int tempRow = row;
            int tempColum = colum;
            switch (i){
                //Arriba
                case 0:
                    tempRow--;
                    break;
                //Arriba Derecha
                case 1:
                    tempRow--;
                    tempColum++;
                    break;
                //Derecha
                case 2:
                    tempColum++;
                    break;
                //Abajo Derecha
                case 3:
                    tempRow++;
                    tempColum++;
                    break;
                //Abajo
                case 4:
                    tempRow++;
                    break;
                //Abajo Izquierda
                case 5:
                    tempRow++;
                    tempColum--;
                    break;
                //Izquierda
                case 6:
                    tempColum--;
                    break;
                //Arriba Izquierda
                case 7:
                    tempRow--;
                    tempColum--;
                    break;
            }
            //Comprobar si esta dentro del mapa generado
            if (tempRow >= 0 && tempRow<map.size() && tempColum>=0 && tempColum<map.get(0).size()){
                tempListBoxes.add(map.get(tempRow).get(tempColum));
            }
        }
        return tempListBoxes;
    }
    public void printMap(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColums; j++) {
                System.out.print(map.get(i).get(j).getHasGem()?"*":"0");
            }
            System.out.println("");
        }
    }
    public void printHint(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColums; j++) {
                System.out.print(map.get(i).get(j).getNumHint());
            }
            System.out.println("");
        }
    }
}
