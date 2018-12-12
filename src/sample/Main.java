package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Main extends Application {

    Stage window;
    Scene gridScene, startScene;
    int height = 0;
    int width = 0;
    String player;
    String Player2NameString;
    String themeLight ;
    String themeDark ;
    boolean themeSelector = false;
    ScoreBoard sb =new ScoreBoard();
    //Test Only
    chooseLoadedGame load = new chooseLoadedGame();
    GUIGame guiGame;
    settingsGUI settings = new settingsGUI();
    startNewGameSettings startmenu = new startNewGameSettings();

    //Gui Elements for theme
    HBox gameStartPane = new HBox();
    VBox gameOpeneingList = new VBox();
    ImageView imageView = new ImageView();
    public int blankCell = 1 ;
    public int flagCell = 5 ;
    public int bombCell = 10 ;
    public int shieldsCount = 0 ;


    public static String themePath = "./style.css" ;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//////////////////////////////////////////////////////////////////////////////////////////    NEW UI
        window = primaryStage ;
        window.setTitle("Mine Sweeper Material");
        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();

        gameStartPane.getStylesheets().add(themePath);

        Music music = new Music();
        music.start();
        Label nameGameLabel = new Label("Mine Sweeper");
        nameGameLabel.setAlignment(Pos.CENTER);
        nameGameLabel.getStyleClass().add("newGameLabels");
        nameGameLabel.setId("nameGame");
        ///////
        Label nameGameMatLabel = new Label("*/* Material Version */*");
        nameGameLabel.setAlignment(Pos.CENTER);
        nameGameMatLabel.getStyleClass().add("newGameLabels");
        nameGameMatLabel.setId("nameGameMat");


        JFXButton newGameButton = new JFXButton("New Game");
        newGameButton.getStyleClass().add("button-raised");
        newGameButton.setId("openingButtons");
        newGameButton.setAlignment(Pos.CENTER);
        newGameButton.setOnAction(e -> {
            startmenu.startingGameSettings(themeSelector );
        });
        JFXButton oldgamesButton = new JFXButton("Load Old Games");
        oldgamesButton.getStyleClass().add("button-raised");
        oldgamesButton.setId("openingButtons");
        oldgamesButton.setAlignment(Pos.CENTER);
        oldgamesButton.setOnAction(e ->{
            load.openLoadList(themeSelector);
        });

        JFXButton ScoreBoardButton = new JFXButton("Score Board");
        ScoreBoardButton.getStyleClass().add("button-raised");
        ScoreBoardButton.setId("openingButtons");
        ScoreBoardButton.setAlignment(Pos.CENTER);
        ScoreBoardButton.setOnAction(e ->{
           sb.GUIScoreBord(themeSelector);
        });

        JFXButton QuickGameButton = new JFXButton("Quick Load Game");
        QuickGameButton.getStyleClass().add("button-raised");
        QuickGameButton.setId("openingButtons");
        QuickGameButton.setAlignment(Pos.CENTER);
        QuickGameButton.setOnAction(e ->{
                try {
                    load.quickLoad(themeSelector);
                } catch (Exception ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("there is no saved files");
        }

        });
        JFXButton settingsButton = new JFXButton("Settings");
        settingsButton.getStyleClass().add("button-raised");
        settingsButton.setId("openingButtons");
        settingsButton.setAlignment(Pos.CENTER);
        settingsButton.setOnAction(e -> {
            settings.openSettingsWindow(this.blankCell, this.flagCell , this.bombCell , this.shieldsCount );
        });


        gameOpeneingList.setMinWidth(1100/2);
        gameOpeneingList.setAlignment(Pos.CENTER);
        VBox.setMargin(newGameButton , new Insets(150 , 20 ,15 ,80));
        VBox.setMargin(oldgamesButton , new Insets(15 , 20 ,15 ,80));
        VBox.setMargin(ScoreBoardButton , new Insets(15 , 20 ,15 ,80));
        VBox.setMargin(QuickGameButton , new Insets(15 , 20 ,15 ,80));
        VBox.setMargin(settingsButton , new Insets(15 , 20 ,15 ,80));
        VBox.setMargin(nameGameLabel , new Insets(15 , 20 ,15 ,80));

//        gameOpeneingList.Insets()
        gameOpeneingList.getStylesheets().add("./style.css");
        gameOpeneingList.getChildren().addAll(
                nameGameLabel,
                nameGameMatLabel ,
                newGameButton ,
                QuickGameButton,
                oldgamesButton,
                ScoreBoardButton,
                settingsButton);

        VBox pictureBox = new VBox();
        imageView = new ImageView(
                new Image("./assests/map.png")
        );

        imageView.getStyleClass().add("mapImage");
        pictureBox.getChildren().addAll(imageView);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(450);
        pictureBox.setAlignment(Pos.CENTER);
        VBox.setMargin(imageView, new Insets(70));

        settings.toggleTheme.selectedProperty().addListener( (v,oldValue,newValue) -> {
            System.out.println(newValue);
            if (newValue) {
                gameStartPane.getStylesheets().remove(themeLight);
                themeSelector = true ;
                System.out.println("scene stylesheets on button 1 click: " + gameStartPane.getStylesheets());
                if(!gameStartPane.getStylesheets().contains(themeDark))
                    gameStartPane.getStylesheets().add(themeDark);
                imageView.setImage(new Image("./assests/mapDark.png"));
                themeSelector = true;
                System.out.println("scene stylesheets on button 1 click: " + gameStartPane.getStylesheets());
            } else if(!newValue){
                gameStartPane.getStylesheets().remove(themeDark);
                themeSelector = false ;
                System.out.println("scene stylesheets on button 1 click: " + gameStartPane.getStylesheets());
                if(!gameStartPane.getStylesheets().contains(themeLight))
                    gameStartPane.getStylesheets().add(themeLight);
                imageView.setImage(new Image("./assests/map.png"));
                themeSelector = false;
                System.out.println("scene stylesheets on button 1 click: " + gameStartPane.getStylesheets());
            }
        });


                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        gameStartPane.getChildren().addAll(gameOpeneingList , pictureBox);


        startScene = new Scene(gameStartPane , 1100 , 700);
//        scenesArray[0] = startScene;
        window.setScene(startScene);
        window.show();
        window.setOnCloseRequest(e -> music.interrupt());
        }


        public static void main (String[]args){ launch(args); }


        private boolean isInt (TextField input, String message){
            try {
                int age = Integer.parseInt(input.getText());
                System.out.println("User is: " + age);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + message + " is not a number");
                return false;
            }
        }
    }
