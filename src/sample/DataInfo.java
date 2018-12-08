package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInfo implements Serializable {
    public Grid grid;
    private boolean isSettingsActivated;
    private GameMode gameMode =GameMode.CAN_BE_LOADED;
    private ArrayList<playerMove> playerMoves = new ArrayList<>();
    private int[] allsettingNumber = new int[4];
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> timeList = new ArrayList<>();

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public boolean isSettingsActivated() {
        return isSettingsActivated;
    }

    public void setSettingsActivated(boolean settingsActivated) {
        isSettingsActivated = settingsActivated;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public ArrayList<playerMove> getPlayerMoves() {
        return playerMoves;
    }

    public void setPlayerMoves(ArrayList<playerMove> playerMoves) {
        this.playerMoves = playerMoves;
    }

    public int[] getAllsettingNumber() {
        return allsettingNumber;
    }

    public void setAllsettingNumber(int[] allsettingNumber) {
        this.allsettingNumber = allsettingNumber;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Integer> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<Integer> timeList) {
        this.timeList = timeList;
    }

    public DataInfo(Grid grid,ArrayList<Player> _players) {
        this.grid = grid;
        this.players = _players;
    }

    public DataInfo(Grid grid,ArrayList<Player> _players, int bomb ,int blank , int flag ,int shields,boolean isSettingsActivated) {
        this.grid = grid;
        this.allsettingNumber[0] = bomb ;
        this.allsettingNumber[1] = blank;
        this.allsettingNumber[2] = flag;
        this.allsettingNumber[3] = shields;
        this.isSettingsActivated = isSettingsActivated;
        this.players = _players;
    }

    public DataInfo(Grid grid,ArrayList<Player> _players,ArrayList<playerMove> playerMoves, int bomb ,int blank , int flag ,int shields,boolean isSettingsActivated) {
        gameMode=GameMode.CAN_BE_REPLAYED;
        this.grid = grid;
        this.allsettingNumber[0] = bomb ;
        this.allsettingNumber[1] = blank;
        this.allsettingNumber[2] = flag;
        this.allsettingNumber[3] = shields;
        this.isSettingsActivated = isSettingsActivated;
        this.players = _players;
        this.playerMoves = playerMoves;
    }

}
