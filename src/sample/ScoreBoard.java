package sample;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class ScoreBoard implements Serializable {
    private int ID=1;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private Score player1Score = new Score();
    private Score player2Score = new Score();
    private SimpleDateFormat ft = new SimpleDateFormat (" yyyy.Mm.dd 'at' HH:mm:ss");
    private String p1Name;
    private String p2Name;
    private  String filepath="./src/data/ScoreBorde.ran";
    private  String Gamefilepath= "./src/data/SavedGame/SavedData1";

    public ScoreBoard() {

        this.setGamefilepath();
    }

    public String getGamefile() { return Gamefilepath; }

    private void setGamefilepath() {
        IdFile id = new IdFile();
        int i = id.getI();
        ID=i;
        Gamefilepath="./data/SavedGame/Game"+i+".ran";
    }




    public Date getStartDate() {
        System.out.println("Current Date: " + ft.format(startDate));
        return startDate;
    }

    public void setStartDate() {
        startDate = new Date();
    }

    public Date getEndDate() {
        System.out.println("Current Date: " + ft.format(endDate));
        return endDate;
    }

    public void setEndDate() {
        endDate=new Date();
    }

    public Score getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(Score player1Score) {
        this.player1Score = player1Score;
    }

    public Score getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(Score player2Score) {
        this.player2Score = player2Score;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public String getGamefilepath() {
        return Gamefilepath;
    }

    public void setGamefilepath(String gamefilepath) {
        Gamefilepath = gamefilepath;
    }

    public void  write(ScoreBoard sb) {
        try {
            ArrayList<ScoreBoard> scoreBoards =new ArrayList<>();
            sb.read(scoreBoards);
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(scoreBoards);

            objectOut.close();

            System.out.println("The Object ' ScoreBoard '  was succesfully written to  file");

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public  void read(ArrayList<ScoreBoard> sbs){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);

            ObjectInputStream objectOut = new ObjectInputStream(fileIn);

            sbs = (ArrayList<ScoreBoard>) objectOut.readObject();

            objectOut.close();

            System.out.println("The Object ' ScoreBoard '  was succesfully read from the file");

        } catch (Exception ex) { ex.printStackTrace(); }

    }


    public void GUIScoreBord(){
        ArrayList<ScoreBoard> sbs = new ArrayList<>();
        this.read(sbs);


    }
}
