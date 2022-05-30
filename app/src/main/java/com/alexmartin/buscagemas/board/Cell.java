package com.alexmartin.buscagemas.board;

public class Cell {
    public static final int BOMB = -1;
    public static final int BLANK = 0;

    private int value;
    private boolean isRevealed;
    private boolean isFlagged;
    private boolean hasGem;
    private int x;
    private int y;
    public Cell(int x,int y) {
        this.value = 0;
        this.isRevealed = false;
        this.isFlagged = false;
    }
    //getters y setters para cada uno de los valores
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isHasGem() {
        return hasGem;
    }

    public void setHasGem(boolean hasGem) {
        this.hasGem = hasGem;
    }
}
