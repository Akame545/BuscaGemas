package com.alexmartin.buscagemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//CLASE QUE REPRESENTA LA CUADRICULA DE MINAS
public class MineGrid {
    //en este arrayList se meteran cada una de las celdas del grid
    private ArrayList<Cell> cells;
    //el tamaño es el número de cuadrados en las filas y columnas
    private int size;
    //public int celdasTotales;
    public int filas;
    public int columnas;

    public MineGrid(int size, int numColumnas){
        this.size=size;
        //this.celdasTotales= (int) (size*size);
        this.cells=new ArrayList<>();
        this.columnas=numColumnas;
        this.filas= (int) (size/columnas);
        //inicialmente cada celda tendrá valores blancos

        /*  ATENCIÓN: En un momento me salian solo diez celdas (una fila) en la cuadricula ya que habia
        codificado el siguiente for:

        for (int i=0; i<size; i++){
            //si tenemos un grid de 10x10, se añadiran al ArrayList 100 nuevas celdas vacias
            cells.add(new Cell(Cell.BLANK));
        }
         */
        for (int i=0; i<size; i++){
            //si tenemos un grid de 10x10, se añadiran al ArrayList 100 nuevas celdas vacias
            cells.add(new Cell(Cell.BLANK));
        }
    }

    /* comprueba si el nº de bombas metidas es menor que el numero total de bombas que necesitamos meter.
        En caso de ser afirmativo se meten las necesarias   */
    public void generateGrid(int totalBombs) {
        int bombsPlaced = 0;
        while (bombsPlaced < totalBombs) {
            int x = new Random().nextInt(filas);
            int y = new Random().nextInt(columnas);

            int index = toIndex(x, y);
            System.out.println(index);

            if (cells.get(index).getValue() == Cell.BLANK) {
                cells.set(index, new Cell(Cell.BOMB));
                bombsPlaced++;
            }
        }
        //calculamos los numeros  que se dispondran en el grid
        for (int x = 0; x < filas; x++) {
            for (int y = 0; y < columnas; y++) {
                /*compprueba si la celda esta vacia. Si es asi por cada celda adyacente con bomba se
                suma un contador (será el numero). Actualizamos la celda con un nuevo valor de "value"*/
                if (cellAt(x, y).getValue() != Cell.BOMB) {
                    List<Cell> adjacentCells = adjacentCells(x, y);
                    int countBombs = 0;
                    for (Cell cell: adjacentCells) {
                        if (cell.getValue() == Cell.BOMB) {
                            countBombs++;
                        }
                    }
                    if (countBombs > 0) {
                        cells.set(toIndex(x, y), new Cell(countBombs));
                    }
                }
            }
        }

    }
    //construye un ArrayList con todas las celdas que son adyacentes a las del parametro
    public List<Cell> adjacentCells(int x, int y) {
        List<Cell> adjacentCells = new ArrayList<>();

        List<Cell> cellsList = new ArrayList<>();
        cellsList.add(cellAt(x-1, y));
        cellsList.add(cellAt(x+1, y));
        cellsList.add(cellAt(x-1, y-1));
        cellsList.add(cellAt(x, y-1));
        cellsList.add(cellAt(x+1, y-1));
        cellsList.add(cellAt(x-1, y+1));
        cellsList.add(cellAt(x, y+1));
        cellsList.add(cellAt(x+1, y+1));

        for (Cell cell: cellsList) {
            if (cell != null) {
                adjacentCells.add(cell);
            }
        }

        return adjacentCells;
    }
    /*  Estos dos metodos nos simplifican el proceso de crear el grid de minas y numeros */
    //convierte las cordinadas x e y en un "indice" en nuestra lista
    public int toIndex(int x, int y){
        //return x + (y*size);
        return ((x*columnas) - (columnas-y)-1);
    }

    public int[] toXY(int index){
        /*int y=index /size;
        int x= index - (y*size);*/
        //LAS FILAS Y COLUMNAS EMPIEZAN A CONTAR DESDE 0
        int x, y;
        x=index/columnas;
        y=index%columnas;
        /*int auxX=index/columnas;
        int auxY=index%columnas;
        if (index%columnas!=0){
            x=auxX+1;
            y=auxY;
        }else{
            x=auxX;
            y=auxY+columnas;
        }*/
        return new int[]{x,y};
    }
    /*comprueba que la coordenada pasada por parametro se encuentra dentro del trablero y devuelve
    el objeto celda (de la clase Cell) de esa coordenada.*/
    public Cell cellAt(int x, int y) {
        if (x < 0 || x >= filas || y < 0 || y >= columnas) {
            return null;
        }
        return cells.get(toIndex(x, y));
    }

    public ArrayList<Cell> getCells(){
        return cells;
    }
}
