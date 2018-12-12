package sample;


import javafx.scene.control.Alert;
import java.io.*;

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

    public void setSaveFile (String saveFile) {

            this.saveFile = new File(saveFile);


    }
    public void SaveAndLoad(){
        saveFile = new File("./src/data/SavedGames/SavedData" + (saveNumber) + ".ran");
    }

    @Override
    public void run(){
        if(_dataInfo.getChoice() == Choice.SAVE)
            saveGameStateBinary();
        else
            loadGameStateBinary();
    }

    public void saveGameStateBinary() {

        try {
            saveFile = new File("./src/data/SavedGames/SavedData" + (saveNumber) + ".ran");
            ObjectOutputStream objOutStream = new ObjectOutputStream(
                    new FileOutputStream(saveFile)
            );
            objOutStream.writeObject(_dataInfo);
            objOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ID.setI(saveNumber);
    }

    public void loadGameStateBinary() {
        try {
            try {
                ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(saveFile));

                _dataInfo = (DataInfo) objInputStream.readObject();
                objInputStream.close();
            }catch (FileNotFoundException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("there is no saved files");
            }
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
            fIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File has been read successfully");
        }
    }


}
