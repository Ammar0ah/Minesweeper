package sample;

import java.io.Serializable;

enum moveType {Reveal,Mark,Unmark}
public class playerMove implements Serializable {
    private Square square;
    private Player player ;
    private  moveType MoveType ;
    private boolean isPossibleToMove;

    public boolean isPossibleToMove() {
        return isPossibleToMove;
    }

    public void setPossibleToMove(boolean possibleToMove) {
        isPossibleToMove = possibleToMove;
    }



    public moveType getMoveType() {
        return MoveType;
    }

    public void setMoveType(moveType moveType) {
        MoveType = moveType;
    }
    public playerMove(){
    }

    public playerMove(Player player){
        this.player = player;
        player.setScore(0);
        this.square = square;
        isPossibleToMove = true;

    }
    public playerMove(Player player,Square square){
        this.player = player;
        player.setScore(0);
        this.square = square;
        isPossibleToMove = true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
