package sample;

import javafx.scene.control.Button;

import java.io.Serializable;

enum Result {playing, winner, loser,shielded}

public abstract class Player implements Serializable {

    protected Score score = new Score();
    protected String name;
    protected Square square = new Square();
    protected Result result = Result.playing;
    public int numberOfFlags = 0;
    protected boolean isAuto = false;
    protected Shield shield=new Shield();

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }


    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }



    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score.setPlayerscore(score);
    }

    public abstract playerMove getplayermove(Grid grid);


}
