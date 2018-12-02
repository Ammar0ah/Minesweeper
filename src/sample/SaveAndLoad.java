package sample;

import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad extends Thread {
    File saveFile ;
    HumanPlayer firstPlayer ;
    HumanPlayer secondPlayer ;
    private static int saveNumber;
    Grid grid ;
    public boolean isReplayGame;


    private ArrayList<playerMove> playerMoves = new ArrayList<>();
    int[] allsettingNumber = new int[4];
    String[] playersNames = new String[2];


    public void setPlayerMoves(ArrayList<playerMove> playerMoves) {
        this.playerMoves = playerMoves;
    }
    //Change to boolean
    public void saveGameStateBinary(Player firstPlayer , Player secondPlayer , Grid currentGrid, int bomb
            , int blank , int flag, int shields){

        try {
            saveFile = new File("./src/data/SavedData"+(++saveNumber) + ".ran");
            ObjectOutputStream objOutStream = new ObjectOutputStream(
                    new FileOutputStream(saveFile )
            );
            if (!firstPlayer.getName().equals(secondPlayer.getName())) {
                objOutStream.writeObject(firstPlayer);
                objOutStream.writeObject(secondPlayer);
            }
            else {
                objOutStream.writeObject(firstPlayer);
                objOutStream.writeObject(null);
            }
            objOutStream.writeObject(currentGrid);
            objOutStream.writeObject(bomb);
            objOutStream.writeObject(blank);
            objOutStream.writeObject(flag);
            objOutStream.writeObject(shields);

            for (playerMove pMove : playerMoves) {
                objOutStream.writeObject(pMove);
            }
            objOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameStateBinary() {
        try{
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
            firstPlayer = (HumanPlayer)objInputStream.readObject();
            secondPlayer = (HumanPlayer)objInputStream.readObject();
            grid = (Grid)objInputStream.readObject();
            allsettingNumber[0] = (int)objInputStream.readObject();
            allsettingNumber[1] = (int)objInputStream.readObject();
            allsettingNumber[2] = (int)objInputStream.readObject();
            allsettingNumber[3] = (int)objInputStream.readObject();
            try {

                while (true) {
                    playerMoves.clear();
                    playerMoves.add((playerMove) objInputStream.readObject());
                }
           }
           catch (EOFException eof){
               grid.printPatch();
               objInputStream.close();
               return;
           }

        }catch (IOException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }

    public void getPLayersName(File file) {
        try{
            ObjectInputStream objInputStream = new ObjectInputStream(
                    new FileInputStream(file)
            );
            playersNames[0] = ((Player)objInputStream.readObject()).getName();
            playersNames[1] = ((Player)objInputStream.readObject()).getName();
            objInputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


}
