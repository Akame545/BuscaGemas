package com.alexmartin.buscagemas;

import com.alexmartin.buscagemas.board.Cell;
import com.alexmartin.buscagemas.board.GemsGrid;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuscaGemasGame implements Serializable {
    private GemsGrid gemsGrid;
    private boolean isGameOver;
    private int lifes=2;
    private int picaxeDurability;
    private int seconds;
    private int mode;
    private int quantityGems;
    public BuscaGemasGame(int mode, int quantityGems){
        this.isGameOver=false;
        this.mode = mode;
        this.quantityGems = quantityGems;
        gemsGrid = modeConfiguration(mode);
        gemsGrid.placeGems(gemsAccordingToDifficulty(quantityGems));
        gemsGrid.asingValues();
    }

    public void handleCellClick(Cell cell, Boolean tool) {
        if (!isGameOver){
            if(tool) {
                if (cell.isHasGem()){
                    cell.setRevealed(true);
                    cell.setDestroyed(true);
                    if(lifes == 0) {
                        isGameOver = true;
                    } else lifes--;
                } else clear(cell);
            } else {
                cell.setRevealed(true);
                cell.setMined(true);
                if(!cell.isHasGem()){
                    cell.setRevealed(true);
                }
                picaxeDurability--;
                if(picaxeDurability==0){
                    isGameOver=true;
                }
            }
        }
    }
    public void clear(Cell cell) {
        int index = getGemsGrid().getCellsList().indexOf(cell);
        getGemsGrid().getCellsList().get(index).setRevealed(true);
        if (cell.getValue() == 0 && !cell.isHasGem()) {
            List<Cell> toClear = new ArrayList<>();
            List<Cell> toCheckAdjacents = new ArrayList<>();
            toCheckAdjacents.add(cell);
            while (toCheckAdjacents.size() > 0) {
                Cell c = toCheckAdjacents.get(0);
                for (Cell adjacentTemp : getGemsGrid().adjacentCells(c.getX(), c.getY())) {
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
            if(!c.isHasGem() && !c.isRevealed()){
                cellsUnrevealed++;
            }
        }
        if (cellsUnrevealed == 0){
            return true;
        } else return false;
    }
    public int remainingGems(){
        int remainingGems=0;
        for (Cell c: getGemsGrid().getCellsList()){
            if(c.isHasGem() && c.isRevealed()){
                remainingGems++;
            }
        }
        return remainingGems;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public int calculateScore(){
        int score=0;
        for (Cell c: getGemsGrid().getCellsList()){
            if(c.isRevealed() && !isGameOver()){
                score++;
            }
            if(c.isHasGem() && c.isMined()){
                switch (c.getValue()) {
                    case 0: case 1:
                        score+=2;
                        break;
                    case 2: case 3:
                        score+=5;
                        break;
                    default:
                        score+=10;
                }
            }
            if(c.isDestroyed())
                score -=10;
        }
        if(!isGameOver) {
            score += picaxeDurability * 3;
        }
        return score;
    }
    private GemsGrid modeConfiguration(int mode){
        switch(mode){
            case 0:
                this.seconds = 100;
                return new GemsGrid(10,10);
            case 1:
                this.seconds = 200;
                return new GemsGrid(30,10);
            case 2:
                this.seconds = 350;
                return new GemsGrid(50,10);
        }
        return new GemsGrid(10,10);
    }
    public int gemsAccordingToDifficulty(int cuantityGems){

        switch (cuantityGems){
            case 0:
                this.picaxeDurability=12;
                return 10;
            case 1:
                this.picaxeDurability=18;
                return 15;
            case 2:
                this.picaxeDurability=23;
                return 20;
            case 3:
                this.picaxeDurability=54;
                return 50;
            case 4:
                this.picaxeDurability=64;
                return 60;
            case 5:
                this.picaxeDurability=86;
                return 80;
            case 6:
                this.picaxeDurability=96;
                return 90;
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

    public int getQuantityGems() {
        return quantityGems;
    }
}
