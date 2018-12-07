package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Game {
    protected Player player;
    protected Grid grid;
    protected static int numOfentries = 0;

    public GameRules gameRules;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }




    public Game() {
        gameRules = new GameRules();
    }

    public static boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    //check the cell status and change the score according to it
    public abstract boolean acceptMove(playerMove playerMove,ArrayList<Player> players);

    // open all the cells in the game when player loses
    protected abstract void checkBombCells();



    public  class GameRules {
        public void getScoreChanges(playerMove playerMove) {
            Player player = playerMove.getPlayer();
            Square square = playerMove.getSquare();
            Score score = player.getScore();
            if (player.getResult() == Result.loser)
                score.setPlayerscore(0);
            else if (isNumeric(square.getMark()))
                score.updateScore(Integer.parseInt(square.getMark()));

            else if(square.getState() == squareState.STATE_FLAG && square.isHasBomb()) {
                score.updateFlagsScore(5);
                grid.numberofFlags++;
                if(player.numberOfFlags == grid.getNumberofMines() || (calculateBlankCells() - grid.getNumberofMines() == grid.getWidth()*grid.getHeight())) {
                    player.setResult(Result.winner);
                    System.out.println(player.getName()+" is the WINNER!!! :D");
                    score.updateScore(grid.getNumberofMines() * 100);
                }
            }
            else if(square.getState() == squareState.STATE_FLAG && !square.isHasBomb())
                score.updateScore(-1);

            else score.updateScore((10 + numOfentries - 1));


        }

        public Player decideNextPlayer(ArrayList<Player>players ,int i) {

            return players.get(i);

        }
    }

    protected   int calculateBlankCells(){
        int calc =0 ;
        for (int i =0 ; i<grid.getHeight() ; i++)
        {
            for (int j=0 ; j < grid.getWidth() ; j++){
                if(!grid.getGameGround()[i][j].isHasBomb() && grid.getGameGround()[i][j].getState()== squareState.STATE_OPENED)
                    calc++;
            }
        }
        return calc;
    }

    public boolean isSafe(int i, int j) {
        return (i >= 0 && i <grid.getHeight()) && (j >= 0 && j < grid.getWidth());
    }

    //searches for the mines
    protected int findMines(Square cell) {

        int sumofMines = 0;
        cell.setState(squareState.STATE_OPENED);

        for (int i = cell.getI() - 1; i <= cell.getI() + 1; i++) {
            for (int j = cell.getJ() - 1; j <= cell.getJ() + 1; j++) {

                if (isSafe(i, j))
                    if (grid.getGameGround()[i][j].isHasBomb()) {
                        sumofMines++;
                        grid.getGameGround()[i][j].setClicked(true);
                    }
            }
        }


        cell.setMark(String.valueOf(sumofMines));
        return sumofMines;
    }

    // opens the blank cells and puts numbers on the cells if it's possible
    protected abstract int openBlankCells(int i , int j,playerMove playerMove);
///// check if anybody won

}