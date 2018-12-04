package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        setID(i);
        Gamefilepath="./data/SavedGame/Game"+i+".ran";
        id.setI(i);
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

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID;}

    public void  write(ArrayList<ScoreBoard> scoreBoards) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);


             objectOut.writeObject(scoreBoards);

            objectOut.close();

            System.out.println("The Object ' ScoreBoard '  was succesfully written to  file");

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public  ArrayList<ScoreBoard> read(){
        ArrayList<ScoreBoard> sbs = new ArrayList<>();
        try {

            FileInputStream fileIn = new FileInputStream(filepath);

            ObjectInputStream objectOut = new ObjectInputStream(fileIn);

            sbs = (ArrayList<ScoreBoard>) objectOut.readObject();

            objectOut.close();

            System.out.println("The Object ' ScoreBoard '  was succesfully read from the file");



        } catch (Exception ex  ) { ex.printStackTrace();}
        return sbs;
    }


    public Scene GUIScoreBord(){

        ArrayList<ScoreBoard> sbs = new ArrayList<>();
        sbs = this.read();
        ScoreBoard sb = new ScoreBoard();

        String themeLight ;
        String themeDark ;

        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();

        VBox mainLoadList = new VBox();
        HBox bordItem[] = new HBox[sbs.size()];
        ScrollPane  mainScrollPane = new ScrollPane();

        Label Idlabel[] = new Label[sbs.size()];
        Label player1label[] = new Label[sbs.size()];
        Label player2label[] = new Label[sbs.size()];
        Label score1label[] = new Label[sbs.size()];
        Label score2label[] = new Label[sbs.size()];
        Label startTimelabel[]= new Label[sbs.size()];
        Label endTimelabel[]= new Label[sbs.size()];
        for(int i=0;i<sbs.size();i++){
            sb = sbs.get(i);
            Idlabel[i] = new Label();
            player1label[i]= new Label();
            player2label[i]=new Label();
            score1label[i] = new Label();
            score2label[i] = new Label();
            startTimelabel[i]= new Label();
            endTimelabel[i]= new Label();
            bordItem[i] =new HBox();

            Idlabel[i].setText(""+sb.getID());
            player1label[i].setText(sb.getP1Name());
            if(p2Name != null)
            player2label[i].setText(sb.getP2Name());
            score1label[i].setText(""+sb.getPlayer1Score().getLatestScore());
            if(p2Name != null)
            score2label[i].setText(""+sb.getPlayer2Score().getLatestScore());
            startTimelabel[i].setText(""+sb.getStartDate());
            endTimelabel[i].setText(""+sb.getEndDate());
            bordItem[i].getStylesheets().add(themeLight);
            bordItem[i].getChildren().addAll(
                    Idlabel[i],
                    player2label[i],
                    player1label[i],
                    score1label[i],
                    score2label[i],
                    startTimelabel[i],
                    endTimelabel[i]);
            mainLoadList.getStylesheets().add(themeLight);
            mainLoadList.getChildren().add(bordItem[i]);

        }


        mainScrollPane.setContent(mainLoadList);
        Scene scene = new Scene(mainScrollPane , 1100, 700);
        return scene;


    }
}
