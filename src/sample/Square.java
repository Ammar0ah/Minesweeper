package sample;


import java.io.Serializable;

enum squareState {STATE_FLAG, STATE_CLOSED, STATE_OPENED}

public class Square implements Serializable {
    private squareState state;

    private String mark;
    private boolean clicked = false;
    private boolean hasBomb = false;
    private boolean hasShield = false;
    private int i;
    private int j;

    public boolean isHasShield() {
        return hasShield;
    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }


    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }


    public void setJ(int j) {
        this.j = j;
    }

    public Square(squareState state) {
        this.state = state;
    }

    public squareState getState() {
        return state;
    }

    public void setState(squareState state) {
        this.state = state;
    }

    public Square() {
        clicked = false;
        mark = "#";
        state = squareState.STATE_CLOSED;

    }




    public int getJ() {
        return j;
    }




    public Square getIndex(int i, int j) {
        return null;
    }


}


