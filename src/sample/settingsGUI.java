package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class settingsGUI {
    BorderPane borderPane = new BorderPane();
    public static String blankScore = "0";
    public static String flagScore = "5";
    public static String bombkScore = "0";
    public static String shieldsCount = "0";

    public JFXToggleButton toggleTheme  = new JFXToggleButton();
    VBox settingsBox = new VBox();
    ImageView topGearImg = new ImageView(
            new Image("./assests/topGear.png")
    );
    ImageView secondGearImg = new ImageView(
            new Image("./assests/SecondGear.png")
    );

    private static boolean darkOrLight = false;

    String themeLight ;
    String themeDark ;
    public boolean theme = false ;

    Stage window = new Stage();
    private String themePath;


    public void openSettingsWindow(int blank , int flag , int bomb , int shields) {


        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();

        if(darkOrLight){
            themePath = themeDark;
            settingsBox.getStylesheets().add(themeDark);
            topGearImg.setImage(
                    new Image("./assests/darkTopGear.png")
            );
            secondGearImg.setImage(
                    new Image("./assests/darkSecondGear.png")
            );
        }else if(!darkOrLight){
            themePath = themeLight;
            settingsBox.getStylesheets().add(themeLight);
        }
        HBox settingsBox = new HBox();
        Label settingsWord = new Label("Settings :");
        settingsWord.setAlignment(Pos.TOP_CENTER);
        settingsWord.getStyleClass().add("settingsLabel");
        settingsWord.setId("settingsWord");



        Label blankCell = new Label("Blank Cell Score");
        blankCell.getStyleClass().add("settingsLabel");

        JFXTextField blankScoreField = new JFXTextField();
        blankScoreField.setLabelFloat(true);
        blankScoreField.setPromptText("Value");
        blankScoreField.getStyleClass().add("settingsTextField");

        HBox blankBlock = new HBox();
        HBox.setMargin(blankCell , new Insets(10 , 30  , 50 , 40));
        blankBlock.getChildren().addAll(blankCell , blankScoreField);
        ////////////////////////
        Label flagCell = new Label("Flag Cell Score");
        flagCell.getStyleClass().add("settingsLabel");

        JFXTextField flagScoreField = new JFXTextField();
        flagScoreField.setLabelFloat(true);
        flagScoreField.setPromptText("Value");
        flagScoreField.getStyleClass().add("settingsTextField");

        HBox flagBlock = new HBox();
        HBox.setMargin(flagCell , new Insets(10 , 30  , 50 , 40));
        flagBlock.getChildren().addAll(flagCell , flagScoreField);
        ////////////////////////
        Label bombCell = new Label("Bomb Cell Score");
        bombCell.getStyleClass().add("settingsLabel");

        JFXTextField bombScoreField = new JFXTextField();
        bombScoreField.setLabelFloat(true);
        bombScoreField.setPromptText("Value");
        bombScoreField.getStyleClass().add("settingsTextField");

        HBox bombBlock = new HBox();
        HBox.setMargin(bombCell , new Insets(10 , 30  , 30 , 40));
        bombBlock.getChildren().addAll(bombCell , bombScoreField);

        Label shieldsLabel = new Label(" Shields: ");
        shieldsLabel.getStyleClass().add("settingsLabel");

        JFXTextField shieldsInput= new JFXTextField();
        shieldsInput.setLabelFloat(true);
        shieldsInput.setPromptText("Value");
        shieldsInput.getStyleClass().add("settingsTextField");

        HBox shieldsBlock = new HBox();
        HBox.setMargin(shieldsLabel , new Insets(10 , 30  , 30 , 40));
        shieldsBlock.getChildren().addAll(shieldsLabel , shieldsInput);

        JFXButton settingsButton = new JFXButton("Save Settings");
        settingsButton.getStyleClass().add("button-raised");
        settingsButton.setOnAction(e -> {
            if (!blankScoreField.getText().trim().isEmpty())
                 this.blankScore = blankScoreField.getText();
            if (!flagScoreField.getText().trim().isEmpty())
                this.flagScore = flagScoreField.getText();
            if (!bombScoreField.getText().trim().isEmpty())
                this.bombkScore = bombScoreField.getText();
            if (!shieldsInput.getText().trim().isEmpty())
                 this.shieldsCount = shieldsInput.getText();
                window.close();
        });
        VBox.setMargin(settingsButton , new Insets(50 , 0 , 0, 0));


        VBox allSettingsBlock = new VBox();
        allSettingsBlock.getStylesheets().add(themePath);
        allSettingsBlock.setAlignment(Pos.CENTER);
        allSettingsBlock.setId("allSettingsBlock");
        VBox.setMargin(settingsWord , new Insets(0 , 0 , 40 , 0));
        allSettingsBlock.getChildren().addAll(settingsWord , blankBlock , flagBlock , bombBlock , shieldsBlock , settingsButton);



        Label blankDefault = new Label("Default: " + blank );
        blankDefault.getStyleClass().add("defaultLabel");
        Label flagDefault = new Label("Default: " + flag);
        flagDefault.getStyleClass().add("defaultLabel");
        Label bombDefault = new Label("Default : " + bomb);
        bombDefault.getStyleClass().add("defaultLabel");
        Label shieldsDefault = new Label("Default : " + shields);
        shieldsDefault.getStyleClass().add("defaultLabel");

        VBox allSettingsDefaults = new VBox();
        allSettingsDefaults.getStylesheets().add(themePath);
        allSettingsDefaults.setAlignment(Pos.CENTER);
        VBox.setMargin(blankDefault , new Insets(55 , 20  , 40 , 40));
        VBox.setMargin(flagDefault , new Insets(40, 20  , 10, 40));
        VBox.setMargin(bombDefault , new Insets(60 , 20  , 0 , 40));
        VBox.setMargin(shieldsDefault , new Insets(60 , 20  , 0 , 40));

        allSettingsDefaults.getChildren().addAll(blankDefault, flagDefault , bombDefault , shieldsDefault);
        HBox.setMargin(allSettingsDefaults , new Insets(0 , 20, 70,10 ));


        VBox pictureBox = new VBox();


        topGearImg.getStyleClass().add("mapImage");
        topGearImg.setPreserveRatio(true);
        topGearImg.setFitWidth(300);


        secondGearImg.getStyleClass().add("mapImage");
        secondGearImg.setPreserveRatio(true);
        secondGearImg.setFitWidth(250);

        HBox movingGears = new HBox();
        movingGears.setAlignment(Pos.CENTER);
        movingGears.getStyleClass().add("movingGearsBox");
        movingGears.getChildren().addAll(topGearImg , secondGearImg);
        HBox.setMargin(secondGearImg , new Insets(120, 0, 0, 0 ));

        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setMinWidth(550);
        pictureBox.getChildren().add(movingGears);

        RotateTransition rt = new RotateTransition(Duration.seconds(5), topGearImg);
        rt.setByAngle(360);
        rt.setCycleCount(1000);
        rt.play();

        RotateTransition rt1 = new RotateTransition(Duration.seconds(5), secondGearImg);
        rt1.setByAngle(-360);
        rt1.setCycleCount(1000);
        rt1.play();


        toggleTheme.setOnAction(e -> {
            if (!toggleTheme.isSelected()) {
                toggleTheme.setSelected(false) ;
                this.theme = false ;
                darkOrLight = false ;
                settingsBox.getStylesheets().remove(themeDark);
                allSettingsBlock.getStylesheets().remove(themeDark);
                allSettingsDefaults.getStylesheets().remove(themeDark);
                System.out.println("scene stylesheets on button 1 click: " + settingsBox.getStylesheets());
                if(!settingsBox.getStylesheets().contains(themeLight)){
                    settingsBox.getStylesheets().add(themeLight);
                    allSettingsBlock.getStylesheets().add(themeLight);
                    allSettingsDefaults.getStylesheets().add(themeLight);
                    topGearImg.setImage(
                            new Image("./assests/topGear.png")
                    );
                    secondGearImg.setImage(
                            new Image("./assests/SecondGear.png")
                    );
                }
                System.out.println("scene stylesheets on button 1 click: " + settingsBox.getStylesheets());

            } else{
                toggleTheme.setSelected(true);
                this.theme = true ;
                darkOrLight = true;
                settingsBox.getStylesheets().remove(themeLight);
                allSettingsBlock.getStylesheets().remove(themeLight);
                allSettingsDefaults.getStylesheets().remove(themeLight);
                System.out.println("scene stylesheets on button 1 click: " + settingsBox.getStylesheets());
                if(!settingsBox.getStylesheets().contains(themeDark)){
                    settingsBox.getStylesheets().add(themeDark);
                    allSettingsBlock.getStylesheets().add(themeDark);
                    allSettingsDefaults.getStylesheets().add(themeDark);
                    topGearImg.setImage(
                            new Image("./assests/darkTopGear.png")
                    );
                    secondGearImg.setImage(
                            new Image("./assests/darkSecondGear.png")
                    );
                }
                System.out.println("scene stylesheets on button 1 click: " + settingsBox.getStylesheets());
            }
        });

        settingsBox.getChildren().addAll(allSettingsBlock , allSettingsDefaults , pictureBox , toggleTheme);
        Scene settings = new Scene( settingsBox, 1300 , 700 );
        window.setScene(settings);
        window.show();


    }
}
