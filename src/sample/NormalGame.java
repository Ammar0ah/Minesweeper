package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class NormalGame extends Game {
    @Override
    public boolean acceptMove(playerMove playerMove,ArrayList<Player> players) {

        try {
            Square cell = playerMove.getSquare();
            int i = cell.getI();
            int j = cell.getJ();
            if (cell.getState() == squareState.STATE_CLOSED && (playerMove.getMoveType() == moveType.Mark)) {

                cell.setMark("F");
                cell.setState(squareState.STATE_FLAG);
                return true;
            } else if (cell.isHasBomb()) {
                cell.setState(squareState.STATE_OPENED);
                if(players.size() == 1)
                    cell.setMark("$");
                return false;
            } else if (cell.getState() == squareState.STATE_CLOSED && (playerMove.getMoveType()== moveType.Reveal)) {
                openBlankCells(i, j,playerMove);
                return true;
            } else if (cell.getState() == squareState.STATE_FLAG && (playerMove.getMoveType() == moveType.Unmark)) {
                cell.setMark("#");
                cell.setState(squareState.STATE_CLOSED);

                return true;
            } else if (cell.getState() == squareState.STATE_OPENED || cell.isClicked()) {
                System.out.println("Already Opened Try another one!!!");
                playerMove p =playerMove.getPlayer().getplayermove(grid);
                return acceptMove(p,players);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            System.out.println("change the index: ");
            throw ex;
        }
        return false;
    }

    @Override
    protected void checkBombCells() {
        for (int i = 0; i < Grid.height; i++) {
            for (int j = 0; j < Grid.width; j++) {

                Square temp = grid.getGameGround()[i][j];
                if (temp.isHasBomb() && (temp.getState() == squareState.STATE_CLOSED)) {
                    temp.setMark("&");
                } else if ((temp.getState() == squareState.STATE_FLAG) && temp.isHasBomb()) {
                    temp.setMark("F");
                } else if (temp.isHasBomb()) {
                    temp.setMark("$");

                } else if (temp.getState() == squareState.STATE_FLAG && !temp.isHasBomb()) {
                    temp.setMark("F!");
                }

            }
        }

    }



    @Override
    protected int openBlankCells(int i, int j,playerMove playerMove) {

        if (!isSafe(i, j))
            return numOfentries;

        Square cell = grid.getGameGround()[i][j];

        if (cell.isClicked())
            return numOfentries;

        if (findMines(cell) == 0) {
            ++numOfentries;
            cell.setMark("-");
            cell.setState(squareState.STATE_OPENED);
            cell.setClicked(true);

            if(cell.isHasShield() && !playerMove.getPlayer().isAuto()){
                playerMove.getPlayer().getShield().updateShildCount(1);
                cell.setMark("H");
                cell.setState(squareState.STATE_OPENED);
                cell.setClicked(true);

            }
            openBlankCells(i, j + 1, playerMove);
            openBlankCells(i, j - 1, playerMove);
            openBlankCells(i + 1, j, playerMove);
            openBlankCells(i - 1, j, playerMove);
            openBlankCells(i - 1, j - 1, playerMove);
            openBlankCells(i + 1, j - 1, playerMove);
            openBlankCells(i + 1, j + 1, playerMove);
            openBlankCells(i - 1, j + 1, playerMove);
        }
        return numOfentries;

    }
}
