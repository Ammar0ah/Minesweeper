package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;


public class ScoreBoard   implements Serializable  {
    private int ID = 1;
    private String startDate ;
    private String endDate ;
    private int player1Score ;
    private int player2Score ;
    private SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss 'at' yyyy/mm/dd");
    private String p1Name;
    private String p2Name;
    private String filepath = "./src/data/ScoreBord.ran";
    private String Gamefilepath = null;
    private boolean canBeReplayed = false;

    String themeLight;
    String themeDark;
    String themepath;

    public boolean isCanBeReplayed() {
        return canBeReplayed;
    }

    public void setCanBeReplayed(boolean canBeReplayed) {
        this.canBeReplayed = canBeReplayed;
    }

    public ScoreBoard() {
        setID();
        setStartDate();
    }

    public String getGamefile() {
        return Gamefilepath;
    }

    private void setID() {
        try {
            ObservableList<ScoreBoard> sbs;
            sbs = this.read();
            ID = sbs.get(sbs.size() - 1).getID();
            ID++;
            this.write(sbs);

        } catch (Exception e) {
            ID = 0;
        }

    }

    public void setGamefilepath(int i) {
        Gamefilepath = "./src/data/SavedGames/SavedData"+i+".ran";
    }



    public void setStartDate() {
        Date date = new Date();
        startDate =""+ft.format(date);

    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate() {
        Date date = new Date();
        endDate=ft.format(date);

    }

    public String getEndDate() {
        return endDate;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
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


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void write( ObservableList<ScoreBoard> scoreBoards) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);


            objectOut.writeObject(new ArrayList<ScoreBoard>(scoreBoards));

            objectOut.close();

            System.out.println("The Object ' ScoreBoard '  was succesfully written to  file");

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    public ObservableList<ScoreBoard> read() {
        try {
            try {

                FileInputStream fileIn = new FileInputStream(filepath);

                ObjectInputStream objectOut = new ObjectInputStream(fileIn);

                ArrayList<ScoreBoard> list = (ArrayList<ScoreBoard>) objectOut.readObject();

                objectOut.close();

                System.out.println("The Object ' ScoreBoard '  was succesfully read from the file");

                return FXCollections.observableList(list);


            } catch (IOException e) {
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return FXCollections.emptyObservableList();
    }

    public void GUIScoreBord(boolean themeSelector){
        if (themeSelector) {
            themepath = "./DarkStyle.css";
                 } else if (!themeSelector) {
            themepath = "./style.css";
                 }
        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();
        Stage stage =  new Stage();
        stage.setTitle("Score Bord");
        TableView<ScoreBoard> table;



        //ID column
        TableColumn<ScoreBoard, String> IDColumn = new TableColumn<>("Game_ID");
        IDColumn.setMinWidth(50);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //Name column
        TableColumn<ScoreBoard, String> name1Column = new TableColumn<>("first_player_Name");
        name1Column.setMinWidth(150);
        name1Column.setCellValueFactory(new PropertyValueFactory<>("p1Name"));
        TableColumn<ScoreBoard, String> name2Column = new TableColumn<>("second_player_Name");
        name2Column.setMinWidth(150);
        name2Column.setCellValueFactory(new PropertyValueFactory<>("p2Name"));

        //score column
        TableColumn<ScoreBoard, Integer> score1Column = new TableColumn<>("first_player_score");
        score1Column.setMinWidth(100);
        score1Column.setCellValueFactory(new PropertyValueFactory<>("player1Score"));

        TableColumn<ScoreBoard, Integer> score2Column = new TableColumn<>("second_player_score");
        score2Column.setMinWidth(100);
        score2Column.setCellValueFactory(new PropertyValueFactory<>("player2Score"));

        //date column
        TableColumn<ScoreBoard, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setMinWidth(200);
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<ScoreBoard, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setMinWidth(200);
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        //read ScoreBord file and get the list
        ObservableList<ScoreBoard> sbs=FXCollections.observableArrayList();
        sbs=this.read();
        //inti
        table = new TableView<>();
        table.setItems(sbs);
        table.getColumns().addAll(
                IDColumn,name1Column,
                name2Column, score1Column,
                score2Column, startDateColumn,
                endDateColumn);
        VBox vBox = new VBox();
        HBox hbox = new HBox();
        hbox.getStylesheets().add(themepath);
        hbox.setMaxHeight(50);
        JFXButton button = new JFXButton("Replay");
        button.getStyleClass().add("button-raised");



            button.setOnMouseClicked(e->{
                ReplayGame replayGame = new ReplayGame();
                    replayGame.saveAndLoad.setSaveFile(table.getSelectionModel().getSelectedItem().getGamefilepath());
                System.out.println(table.getSelectionModel().getSelectedItem().getGamefilepath());
                replayGame.start();

            });
        hbox.getChildren().addAll(button);
        vBox.getChildren().addAll(table,hbox);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }



}
