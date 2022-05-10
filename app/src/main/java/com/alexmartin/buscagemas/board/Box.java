package com.alexmartin.buscagemas.board;

public class Box {
    private int posRow;
    private int posColumn;
    private Boolean hasGem = false;
    //Número que tiene las casillas sin gema
    private int numHint;
    public Box(int posRow, int posColumn) {
        this.posRow = posRow;
        this.posColumn = posColumn;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getPosColumn() {
        return posColumn;
    }

    public void setPosColumn(int posColumn) {
        this.posColumn = posColumn;
    }

    public Boolean getHasGem() {
        return hasGem;
    }

    public void setHasGem(Boolean hasGem) {
        this.hasGem = hasGem;
    }

    public int getNumHint() {
        return numHint;
    }

    public void setNumHint(int numHint) {
        this.numHint = numHint;
    }
    public void incrementHintGemsArount(){
        this.numHint++;
    }
}
