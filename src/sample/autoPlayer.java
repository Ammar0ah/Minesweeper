package sample;

import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.Random;
import java.util.Scanner;

public class autoPlayer extends Player {

    public autoPlayer() {
        name ="Computer";
        this.score.setPlayerscore(0);
        isAuto=true;
    }

    public playerMove dumbMove(Grid grid) {
        double randomi = Math.random()*(grid.getHeight()-1);
        double randomj = Math.random()*(grid.getWidth()-1);
        int i = (int) Math.round(randomi);
        int j = (int) Math.round(randomj);
        playerMove p = new playerMove(this);
        p.setPlayer(this);
        p.setMoveType(moveType.Reveal);
        p.setSquare(grid.getIndex(i,j));
        if(square.getState() == squareState.STATE_OPENED){
           return dumbMove(grid);
        }
        return p;
    }

    @Override
    public playerMove getplayermove(Grid grid) {
        return null;
    }

}
