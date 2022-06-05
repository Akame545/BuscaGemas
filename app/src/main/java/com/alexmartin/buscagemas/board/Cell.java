package com.alexmartin.buscagemas.board;

import java.io.Serializable;

public class Cell implements Serializable {
    private int value;
    private boolean isRevealed;
    private boolean isMined;
    private boolean hasGem;
    private boolean isDestroyed;
    private int x;
    private int y;
    public Cell(int x,int y) {
        this.value = 0;
        this.isRevealed = false;
        this.isMined = false;
        this.x = x;
        this.y = y;
    }
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

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public boolean isHasGem() {
        return hasGem;
    }

    public void setHasGem(boolean hasGem) {
        this.hasGem = hasGem;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
