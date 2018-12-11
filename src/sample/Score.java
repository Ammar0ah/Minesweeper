package sample;

import java.io.Serializable;

public class Score implements Serializable {
    private int playerscore =0;
    private int playerFlagsScore =0;
    private int playerShieldScore=0;
 public int bombscore =0 ;
 public int blankscore =0 ;
 private int latestScore;

    public int getLatestScore() {
        return playerscore+playerFlagsScore+playerShieldScore;
    }

    public int getPlayerShieldScore() {
        return playerShieldScore;
    }

    public void setPlayerShieldScore(int playerShieldScore) {
        this.playerShieldScore = playerShieldScore;
    }

    public int getPlayerFlagsScore() {
        return playerFlagsScore;
    }

    public void setPlayerFlagsScore(int playerFlagsScore) {
        this.playerFlagsScore = playerFlagsScore;
    }

    public int getPlayerscore() {
        return playerscore;
    }

    public void setPlayerscore(int score) {
        this.playerscore = score;
    }
    public int getPlayerLostscore() {
        this.playerscore = 0;
        this.playerFlagsScore = 0;
        return 0;

    }
    public void updateScore(int score){
        playerscore += score;

    }
    public void updateFlagsScore(int score){
        playerFlagsScore += score;

    }

    public void updateShieldScore(HumanPlayer p){
        playerShieldScore += 50*(p.getShield().getShieldCount());

    }

}
