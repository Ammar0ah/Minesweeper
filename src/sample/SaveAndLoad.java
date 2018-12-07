package sample;


import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad extends Thread {
    private File saveFile;
    public Grid grid;
    private boolean isSettingsActivated;
    public int numP = 0;
    private IdFile ID = new IdFile();
    private int saveNumber = ID.getI();
    private GameMode gameMode;
    private ArrayList<playerMove> playerMoves = new ArrayList<>();
    private int[] allsettingNumber = new int[4];
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<MouseEvent> mouseEvents = new ArrayList<>();
    private ArrayList<Integer> timeList = new ArrayList<>();

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getSaveNumber() {
        return saveNumber;
    }

    public void setSaveNumber(int saveNumber) {
        this.saveNumber = saveNumber;
    }

    ArrayList<String> playersNames = new ArrayList<>();
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = new File(saveFile);
    }

    public boolean isSettingsActivated() {
        return isSettingsActivated;
    }

    public void setSettingsActivated(boolean settingsActivated) {
        isSettingsActivated = settingsActivated;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public int[] getAllsettingNumber() {
        return allsettingNumber;
    }

    public ArrayList<playerMove> getPlayerMoves() {
        return playerMoves;
    }

    public ArrayList<Integer> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<Integer> timeList) {
        this.timeList = timeList;
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

    public ArrayList<MouseEvent> getMouseEvents() {
        return mouseEvents;
    }

    public void setMouseEvents(ArrayList<MouseEvent> mouseEvents) {
        this.mouseEvents = mouseEvents;
    }

    public void setPlayerMoves(ArrayList<playerMove> playerMoves) {
        this.playerMoves = playerMoves;
    }

    public void saveGameStateBinary(ArrayList<Player> players, Grid currentGrid, int bomb
            , int blank, int flag, int shields,ArrayList<MouseEvent>mEvents , boolean settingsChanged,ArrayList<Integer>tList) {

        try {
            saveFile = new File("./src/data/SavedGames/SavedData" + (saveNumber) + ".ran");
            ObjectOutputStream objOutStream = new ObjectOutputStream(
                    new FileOutputStream(saveFile)
            );
            objOutStream.writeObject(players);
            objOutStream.writeObject(currentGrid);
            objOutStream.writeObject(bomb);
            objOutStream.writeObject(blank);
            objOutStream.writeObject(flag);
            objOutStream.writeObject(shields);
            objOutStream.writeObject(gameMode);
            objOutStream.writeObject(mEvents);
            objOutStream.writeObject(settingsChanged);
            objOutStream.writeObject(tList);
            if(gameMode == GameMode.CAN_BE_LOADED)
            objOutStream.writeObject(playerMoves);
            objOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ID.setI(saveNumber);
    }

    public void loadGameStateBinary() {
        try {
            ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(saveFile));
            players = (ArrayList<Player>) objInputStream.readObject();
            grid = (Grid) objInputStream.readObject();
            for (int i = 0; i < 4; i++)
                allsettingNumber[i] = (int) objInputStream.readObject();
            gameMode = (GameMode) objInputStream.readObject();
            mouseEvents = (ArrayList<MouseEvent>) objInputStream.readObject();
            isSettingsActivated = (boolean)objInputStream.readObject();
            timeList = (ArrayList<Integer>)objInputStream.readObject();
            if (gameMode == GameMode.CAN_BE_REPLAYED)
                playerMoves = (ArrayList<playerMove>) objInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getPLayersName(File file) {
        try {
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream objInputStream = new ObjectInputStream(fIn);
            numP = (int) objInputStream.readObject();
            playersNames.add(((Player) objInputStream.readObject()).getName());
            if (numP == 2)
                playersNames.add(((Player) objInputStream.readObject()).getName());
            objInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
