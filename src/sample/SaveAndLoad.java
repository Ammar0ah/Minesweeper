package sample;

import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad extends Thread {
    File saveFile ;
    HumanPlayer firstPlayer ;
    HumanPlayer secondPlayer ;
    Grid grid ;
    public boolean isReplayGame;
    public int  numP =0 ;
    IdFile ID =new IdFile();
    private int saveNumber=ID.getI();


    private ArrayList<playerMove> playerMoves = new ArrayList<>();
    int[] allsettingNumber = new int[4];
    ArrayList<String> playersNames = new ArrayList<>();

    public void setPlayerMoves(ArrayList<playerMove> playerMoves) {
        this.playerMoves = playerMoves;
    }
    //Change to boolean
    public void saveGameStateBinary(Player firstPlayer , Player secondPlayer , Grid currentGrid, int bomb
            , int blank , int flag, int shields, int numofp){

        try {
            saveFile = new File("./src/data/SavedGames/SavedData"+(saveNumber) + ".ran");
            ObjectOutputStream objOutStream = new ObjectOutputStream(
                    new FileOutputStream(saveFile )
            );
            objOutStream.writeObject(numofp);
            if (numofp==2) {
                objOutStream.writeObject(firstPlayer);
                objOutStream.writeObject(secondPlayer);
            }
            else {
                objOutStream.writeObject(firstPlayer);
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
        ID.setI(saveNumber);
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
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream objInputStream = new ObjectInputStream(fIn);
            numP = (int) objInputStream.readObject();
            playersNames.add(((Player)objInputStream.readObject()).getName());
            if(numP==2)
            playersNames.add(((Player)objInputStream.readObject()).getName());
            objInputStream.close();
        }catch (IOException | ClassNotFoundException  e ){
            e.printStackTrace();
        }
    }


}
