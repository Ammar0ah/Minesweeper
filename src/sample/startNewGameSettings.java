package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.JFXKeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.ArrayList;

public class startNewGameSettings {
    Stage window = new Stage();
    GUIGame guiGame = new GUIGame();
    settingsGUI settings = new settingsGUI();

    String themepath = "";
    String player, Player2NameString;
    public int width, height, bombsNum;

    public void startingGameSettings(boolean themeSelector) {
        if (themeSelector) {
            themepath = "./DarkStyle.css";
        } else if (!themeSelector)
            themepath = "./style.css";

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStylesheets().add(themepath);
        //To center It
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);


        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);

        ///////////////// Set NO. of Players
        Label numberOfPlayers = new Label("Enter The Number Of Players: ");
        numberOfPlayers.getStyleClass().add("settingsLabel");

        JFXTextField numberOP = new JFXTextField();
        numberOP.setLabelFloat(true);
        numberOP.setPromptText("Number");
        numberOP.getStyleClass().add("settingsTextField");

        JFXButton submitNumber = new JFXButton("Add Players");
        submitNumber.setId("addPlayersButton");
        submitNumber.getStyleClass().add("button-raised");
        submitNumber.getStylesheets().add(themepath);


        HBox numberOPHBox = new HBox();
        numberOPHBox.setAlignment(Pos.CENTER);
        HBox.setMargin(numberOfPlayers, new Insets(25, 30, 30, 40));
        numberOPHBox.getChildren().addAll(numberOfPlayers, numberOP, submitNumber);
        ////////////////////
        /////////////////////
        Label firstPlayerNameLabel = new Label("Name: ");
        firstPlayerNameLabel.getStyleClass().add("settingsLabel");

        JFXTextField player1Name = new JFXTextField();
        player1Name.setLabelFloat(true);
        player1Name.setPromptText("First PLayer");
        player1Name.getStyleClass().add("settingsTextField");

        HBox player1 = new HBox();
        player1.setAlignment(Pos.CENTER);
        HBox.setMargin(firstPlayerNameLabel, new Insets(25, 30, 30, 40));
        player1.getChildren().addAll(firstPlayerNameLabel, player1Name);
        ////////////////////
        Label secondPlayerNameLabel = new Label("Name: ");
        secondPlayerNameLabel.getStyleClass().add("settingsLabel");

        JFXTextField player2Name = new JFXTextField();
        player2Name.setLabelFloat(true);
        player2Name.setPromptText("Second PLayer");
        player2Name.setDisable(true);
        player2Name.getStyleClass().add("settingsTextField");


        HBox player2 = new HBox();
        player2.setAlignment(Pos.CENTER);
        HBox.setMargin(secondPlayerNameLabel, new Insets(25, 30, 30, 40));
        player2.getChildren().addAll(secondPlayerNameLabel, player2Name);
        ///////////////////
        Label gridWidthLabel = new Label("Grid Width: ");
        gridWidthLabel.getStyleClass().add("settingsLabel");

        JFXTextField gridWidth = new JFXTextField();
        gridWidth.setLabelFloat(true);
        gridWidth.setPromptText("Width");
        gridWidth.getStyleClass().add("settingsTextField");

        HBox width = new HBox();
        width.setAlignment(Pos.CENTER);
        HBox.setMargin(gridWidthLabel, new Insets(25, 30, 30, 40));
        width.getChildren().addAll(gridWidthLabel, gridWidth);
        //////////////////
        Label gridHeightLabel = new Label("Grid Height: ");
        gridHeightLabel.getStyleClass().add("settingsLabel");

        JFXTextField gridHeight = new JFXTextField();
        gridHeight.setLabelFloat(true);
        gridHeight.setPromptText("Height");
        gridHeight.getStyleClass().add("settingsTextField");

        HBox height = new HBox();
        HBox.setMargin(gridHeightLabel, new Insets(25, 30, 30, 40));
        height.setAlignment(Pos.CENTER);
        height.getChildren().addAll(gridHeightLabel, gridHeight);
        //////////////////
        Label minesNumLabel = new Label("No. Mines: ");
        minesNumLabel.getStyleClass().add("settingsLabel");

        JFXTextField minesNum = new JFXTextField();
        minesNum.setLabelFloat(true);
        minesNum.setPromptText("Mines");
        minesNum.getStyleClass().add("settingsTextField");

        HBox mines = new HBox();
        mines.setAlignment(Pos.CENTER);
        HBox.setMargin(minesNumLabel, new Insets(25, 30, 30, 40));
        mines.getChildren().addAll(minesNumLabel, minesNum);
//////////////////////////


        Label toggleLabel = new Label("Activate Second Player");
        toggleLabel.getStyleClass().add("settingsLabel");
        Label AutoPlayerLabel  = new Label("Activate Auto Player");
        AutoPlayerLabel.getStyleClass().add("settingLabel");

        Label autoPlayer= new Label("Activate Auto player");
        JFXToggleButton autoplayerToggleButton = new JFXToggleButton();
        autoplayerToggleButton.setSelected(false);
        autoplayerToggleButton.setId("toggleButton");
        autoplayerToggleButton.setOnAction(e ->{
            if(autoplayerToggleButton.isSelected()){
                player2Name.setText("");
                player2Name.setDisable(true);

            }
            else player2Name.setDisable(false);
        });
        HBox autoplayerBox = new HBox();
        autoplayerBox.setAlignment(Pos.CENTER);
        HBox.setMargin(autoPlayer,new Insets(10,30,30,40));
        autoplayerBox.getChildren().addAll(autoPlayer,autoplayerToggleButton);


        JFXToggleButton toggleSecPlayer = new JFXToggleButton();
        toggleSecPlayer.setSelected(false);
        toggleSecPlayer.setId("toggleButton");
        toggleSecPlayer.getStylesheets().add(themepath);
        toggleSecPlayer.setOnAction(e -> {
            if (!toggleSecPlayer.isSelected()) {
                toggleSecPlayer.setSelected(false);
                player2Name.setDisable(true);
            } else {
                toggleSecPlayer.setSelected(true);
                player2Name.setDisable(false);
            }
        });

        HBox toggle = new HBox();
        toggle.setAlignment(Pos.CENTER);
        HBox.setMargin(toggleLabel, new Insets(5, 30, 20, 40));
        toggle.getChildren().addAll(toggleLabel, toggleSecPlayer);
        ///////////////////

        JFXButton startGameButton = new JFXButton("Start !");
        startGameButton.getStyleClass().add("button-raised");
        startGameButton.setOnAction(e -> {

            ///////////////////////////////////////////////////////////  INIT NEW GAME ON ACTION HERE


            ArrayList<Player> players = new ArrayList<>();
            try {


                this.Player2NameString = player2Name.getText();
                this.player = player1Name.getText();
                this.width = Integer.parseInt(gridWidth.getText());
                this.height = Integer.parseInt(gridHeight.getText());
                this.bombsNum = Integer.parseInt(minesNum.getText());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Wrong input");
                alert.setContentText("rechange the field properly!");
                alert.showAndWait();
                return;
            }
            Player p1 = new HumanPlayer();
            Player p2 = new HumanPlayer();
            p1.getShield().setShieldCount(Integer.parseInt(settings.shieldsCount));
            p2.getShield().setShieldCount(Integer.parseInt(settings.shieldsCount));
            p1.setName(player1Name.getText());
            p2.setName(player2Name.getText());
            players.add(p1);
            if (toggleSecPlayer.isSelected())
                players.add(p2);
            if(autoplayerToggleButton.isSelected())
            {
                toggleSecPlayer.setSelected(false);
                p2= new autoPlayer();
                p2.getShield().setShieldCount(0);
                players.add(p2);
            }
            System.out.println(player + width + height);
            Grid grid = new Grid(this.width, this.height, bombsNum);
            System.out.println(settings.bombkScore);
            guiGame = new GUIGame(players, settings.bombkScore, settings.blankScore, settings.flagScore, settings.shieldsCount, true);
            guiGame.setGrid(grid);
            window.setScene(guiGame.returnScene(this.width, this.height, player, Player2NameString, 1100, 700, themeSelector, settings.shieldsCount, true));
            window.setMaximized(true);
            window.setOnCloseRequest(event ->guiGame.timer.interrupt());
        });

        menu.setAlignment(Pos.CENTER);
        menu.getStylesheets().

                add(themepath);
        menu.getChildren().

                addAll(player1, player2, width, height, mines, toggle,autoplayerBox, startGameButton);

        Scene newGameSettings = new Scene(menu, 1100, 700);
        window.setScene(newGameSettings);
        window.setTitle("Mine Sweeper - The Game");
        window.show();


    }
}
