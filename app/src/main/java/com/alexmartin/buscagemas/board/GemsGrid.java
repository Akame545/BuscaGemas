package com.alexmartin.buscagemas.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//CLASE QUE REPRESENTA LA CUADRICULA DE MINAS
public class GemsGrid {
    //en este arrayList se meteran cada una de las celdas del grid
    private ArrayList<Cell> cellsList;
    //el tamaño es el número de cuadrados en las filas y columnas
    private int size;
    //public int celdasTotales;
    public int filas;
    public int columnas;

    public GemsGrid(int filas, int columnas){
        this.columnas=columnas;
        this.filas=filas;
        this.cellsList=new ArrayList<>();

        for (int x=0; x<filas;x++){
            //si tenemos un grid de 10x10, se añadiran al ArrayList 100 nuevas celdas vacias
            for(int y=0; y<columnas;y++) {
                cellsList.add(new Cell(x, y));
            }
        }
    }

    /* comprueba si el nº de bombas metidas es menor que el numero total de bombas que necesitamos meter.
        En caso de ser afirmativo se meten las necesarias   */
    public void placeGems(int totalGems) {
        System.out.println("Entra en placeGems");
        int gemsPlaced = 0;
        while (gemsPlaced < totalGems) {
            int x = new Random().nextInt(filas);
            int y = new Random().nextInt(columnas);

            int index = toIndex(x, y);

            //System.out.println("x:"+x+",y:"+y+",index:"+index);

            if (!cellsList.get(index).isHasGem()) {
                cellsList.get(index).setHasGem(true);
                gemsPlaced++;
            }
        }
    }
    public void asingValues(){
        //calculamos los numeros  que se dispondran en el grid
        for (int x = 0; x < filas; x++) {
            for (int y = 0; y < columnas; y++) {
                /*compprueba si la celda esta vacia. Si es asi por cada celda adyacente con bomba se
                suma un contador (será el numero). Actualizamos la celda con un nuevo valor de "value"*/
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
    //construye un ArrayList con todas las celdas que son adyacentes a las del parametro
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
            //Comprobar si esta dentro del mapa generado
            if (tempX >= 0 && tempX < filas && tempY >= 0 && tempY < columnas){
                tempListCells.add(cellAt(tempX, tempY));
            }
        }
        return tempListCells;
    }

    /*  Estos dos metodos nos simplifican el proceso de crear el grid de minas y numeros */
    //convierte las cordinadas x e y en un "indice" en nuestra lista
    public int toIndex(int x, int y){
        return (this.columnas*x+y);
    }

    public int[] toXY(int index){
        int x, y;
        x = index / this.columnas;
        y = index % this.columnas;
        return new int[]{x,y};
    }
    /*comprueba que la coordenada pasada por parametro se encuentra dentro del trablero y devuelve
    el objeto celda (de la clase Cell) de esa coordenada.*/
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
