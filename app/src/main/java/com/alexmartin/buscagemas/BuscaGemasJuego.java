package com.alexmartin.buscagemas;

public class BuscaGemasJuego {
    private MineGrid mineGrid;

    public BuscaGemasJuego(int size, int numberOfBombs){
        mineGrid=new MineGrid(size);
        mineGrid.generateGrid(numberOfBombs);
    }

    public MineGrid getMineGrid(){
        return mineGrid;
    }
}
