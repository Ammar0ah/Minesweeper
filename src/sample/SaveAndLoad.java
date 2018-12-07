package sample;


import javafx.scene.input.MouseEvent;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad extends Thread {
    private File saveFile;

    private IdFile ID = new IdFile();
    private int saveNumber = ID.getI();
    private DataInfo _dataInfo;

    public DataInfo get_dataInfo() {
        return _dataInfo;
    }

    public void set_dataInfo(DataInfo _dataInfo) {
        this._dataInfo = _dataInfo;
    }

    public int getSaveNumber() {
        return saveNumber;
    }

    public void setSaveNumber(int saveNumber) {
        this.saveNumber = saveNumber;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = new File(saveFile);
    }


    public void saveGameStateBinary(DataInfo dataInfo) {

        try {
            saveFile = new File("./src/data/SavedGames/SavedData" + (saveNumber) + ".ran");
            ObjectOutputStream objOutStream = new ObjectOutputStream(
                    new FileOutputStream(saveFile)
            );
            objOutStream.writeObject(dataInfo);
            objOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ID.setI(saveNumber);
    }

    public void loadGameStateBinary() {
        try {
            ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(saveFile));

            _dataInfo = (DataInfo) objInputStream.readObject();
            objInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getPLayersName(File file) {
        try {
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream objInputStream = new ObjectInputStream(fIn);
            _dataInfo = (DataInfo)objInputStream.readObject();

            objInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File has been read successfully");
        }
    }


}
