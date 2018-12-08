package sample;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private boolean canBeReplayed=false;
    String themeLight ;
    String themeDark ;
    String themepath;

    public boolean isCanBeReplayed() { return canBeReplayed; }

    public void setCanBeReplayed(boolean canBeReplayed) { this.canBeReplayed = canBeReplayed; }

    public ScoreBoard() {
        setID();
    }

    public String getGamefile() { return Gamefilepath; }

    private void setID() {
        try {
            ArrayList<ScoreBoard> sbs;
            sbs=this.read();
            ID = sbs.get(sbs.size()-1).getID();
            ID++;
            this.write(sbs);

        }catch (Exception e){ID=0;}

    }

    public void setGamefilepath(int i){
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
            try {

                FileInputStream fileIn = new FileInputStream(filepath);

                ObjectInputStream objectOut = new ObjectInputStream(fileIn);

                sbs = (ArrayList<ScoreBoard>) objectOut.readObject();

                objectOut.close();

                System.out.println("The Object ' ScoreBoard '  was succesfully read from the file");

            }catch (IOException e){ }

        } catch (Exception ex  ) { ex.printStackTrace();}
        return sbs;
    }



        public void GUIScoreBord(boolean themeSelector){
            if (themeSelector) {
                themepath = "./DarkStyle.css";
            } else if (!themeSelector)
                themepath = "./style.css";
            themeLight = getClass().getResource("../style.css").toExternalForm();
            themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();
            Stage stage =  new Stage();
            VBox mainScoreBoard = new VBox();
            mainScoreBoard.getStylesheets().add(themepath);
            mainScoreBoard.setAlignment(Pos.CENTER);
//            mainScoreBoard.setStyle("-fx-border-style: solid; -fx-border-color: red ; -fx-border-width: 2px;");
//        mainScoreBoard.setFillWidth(true);
            Label scoreBoardWord = new Label("Score Board !");
            scoreBoardWord.setAlignment(Pos.CENTER);
            scoreBoardWord.getStyleClass().add("defaultLabel");
            scoreBoardWord.setId("scoreBoardWord");
            HBox scoreBoardFields = new HBox();
            scoreBoardFields.setAlignment(Pos.CENTER);
            VBox.setMargin(scoreBoardFields, new Insets(0 , 150, 0 ,0));
            scoreBoardFields.getStylesheets().add(themepath);
            Label idField = new Label("ID");
            HBox.setMargin(idField, new Insets(20 , 0 ,10,0));
            idField.getStyleClass().add("defaultLabel");
            Label firstPlayerField = new Label("First Player");
            firstPlayerField.getStyleClass().add("defaultLabel");
            HBox.setMargin(firstPlayerField, new Insets( 20, 0 ,0,50));
            Label firstPlayerScoreField = new Label("Score");
            firstPlayerScoreField.getStyleClass().add("defaultLabel");
            HBox.setMargin(firstPlayerScoreField, new Insets( 20, 0 ,0,70));
            Label secondPlayerField = new Label("Second Player");
            secondPlayerField.getStyleClass().add("defaultLabel");
            HBox.setMargin(secondPlayerField, new Insets( 20, 0 ,0,50));
            Label secondPlayerScoreField = new Label("Score");
            secondPlayerScoreField.getStyleClass().add("defaultLabel");
            HBox.setMargin(secondPlayerScoreField, new Insets( 20, 0 ,0,40));
            Label startDateField = new Label("Start Date");
            startDateField.getStyleClass().add("defaultLabel");
            HBox.setMargin(startDateField, new Insets( 20, 0 ,0,100));
            Label endDateField = new Label("End Date");
            endDateField.getStyleClass().add("defaultLabel");
            HBox.setMargin(endDateField, new Insets( 20, 0 ,0,150));

            scoreBoardFields.getChildren().addAll(idField, firstPlayerField, firstPlayerScoreField, secondPlayerField, secondPlayerScoreField, startDateField, endDateField);
            mainScoreBoard.getChildren().addAll(scoreBoardWord, scoreBoardFields);
            ScrollPane scoreBoardScrollable = new ScrollPane();
            JFXButton sortByIdButton = new JFXButton("Sort By ID");
            JFXButton sortBystartDateButton = new JFXButton("Sort By Start Date");
            JFXButton sortByEndDateButton = new JFXButton("Sort By End Date");


            ArrayList<ScoreBoard> sbs = new ArrayList<>();
            sbs = this.read();
            ScoreBoard sb = new ScoreBoard();


            HBox bordItem[] = new HBox[sbs.size()];
            ScrollPane  mainScrollPane = new ScrollPane();


            Label Idlabel[] = new Label[sbs.size()];
            Label player1label[] = new Label[sbs.size()];
            Label player2label[] = new Label[sbs.size()];
            Label score1label[] = new Label[sbs.size()];
            Label score2label[] = new Label[sbs.size()];
            Label startTimelabel[]= new Label[sbs.size()];
            Label endTimelabel[]= new Label[sbs.size()];
            JFXButton load[] = new JFXButton[sbs.size()] ;
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
                load[i] = new JFXButton("load");
                Idlabel[i].getStyleClass().add("defaultLabel");
                Idlabel[i].setPrefWidth(30);
//                Idlabel[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                Idlabel[i].setText(""+sb.getID());
                HBox.setMargin(Idlabel[i], new Insets( 30, 0 ,0,0));
                player1label[i].setText(sb.getP1Name());
//                player1label[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                player1label[i].setPrefWidth(110);
                HBox.setMargin(player1label[i], new Insets( 30, 0 ,0,50));
                player1label[i].getStyleClass().add("defaultLabel");
                player2label[i].setText(sb.getP2Name());
//                player2label[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                HBox.setMargin(player2label[i], new Insets( 30, 0 ,0,50));
                player2label[i].setPrefWidth(110);
                player2label[i].getStyleClass().add("defaultLabel");
                score1label[i].setText(""+sb.getPlayer1Score().getLatestScore());
                score1label[i].setPrefWidth(50);
//                score1label[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                HBox.setMargin(score1label[i], new Insets( 30, 0 ,0,40));
                score1label[i].getStyleClass().add("defaultLabel");
                score2label[i].setText(""+sb.getPlayer2Score().getLatestScore());
                score2label[i].setPrefWidth(50);
//                score2label[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                HBox.setMargin(score2label[i], new Insets( 30, 0 ,0,40));
                score2label[i].getStyleClass().add("defaultLabel");
                startTimelabel[i].setText(""+sb.getStartDate());
//                startTimelabel[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                HBox.setMargin(startTimelabel[i], new Insets( 30, 0 ,0,75));
                startTimelabel[i].setPrefWidth(200);
                startTimelabel[i].getStyleClass().add("defaultLabel");
                endTimelabel[i].setText(""+sb.getEndDate());
                endTimelabel[i].setPrefWidth(200);
//                endTimelabel[i].setStyle("-fx-border-width: 2px ; -fx-border-color: red");
                HBox.setMargin(endTimelabel[i], new Insets( 30, 0 ,0,60));
                endTimelabel[i].getStyleClass().add("defaultLabel");
                HBox.setMargin(load[i], new Insets( 30, 0 ,0,0));
                bordItem[i].getStylesheets().add(themepath);
                bordItem[i].getChildren().addAll(
                        Idlabel[i],
                        player1label[i],
                        score1label[i],
                        player2label[i],
                        score2label[i],
                        startTimelabel[i],
                        endTimelabel[i],
                        load[i]);
                bordItem[i].getStyleClass().add("scoreBoardBorderButtom");
                mainScoreBoard.getStylesheets().add(themepath);
                mainScoreBoard.getChildren().add(bordItem[i]);
            }
            mainScrollPane.setContent(mainScoreBoard);
            HBox mHBox = new HBox();
            mHBox.getChildren().add(mainScrollPane);
            mHBox.getStylesheets().add(themepath);
            mHBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(mHBox);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Score Bord");
            stage.show();
        }

    }

