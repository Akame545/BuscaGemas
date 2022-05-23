package com.alexmartin.buscagemas;

public class BuscaGemasJuego {
    private MineGrid mineGrid;
    private boolean clearMode;

    public BuscaGemasJuego(int size, int numberOfBombs){
        this.clearMode=true;
        mineGrid=new MineGrid(size);
        mineGrid.generateGrid(numberOfBombs);
    }

    public void handleCellClick(Cell cell) {

            if (clearMode) {
                clear(cell);
            }

    }

    public void clear(Cell cell) {
        //obtener el indice de la celda a traves de la lista de celdas
        int index = getMineGrid().getCells().indexOf(cell);
        //a esta celda se le actualiza la variable isRevelated a true
        getMineGrid().getCells().get(index).setRevealed(true);
    }
    public MineGrid getMineGrid(){
        return mineGrid;
    }
}
