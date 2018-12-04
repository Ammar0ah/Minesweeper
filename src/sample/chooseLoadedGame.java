package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class chooseLoadedGame {
   private Stage window = new Stage();
   private ScrollPane mainScrollPane ;
   private VBox mainLoadList ;
   private Scene scene ;
   private Label gamesName[] ;
   private Label firstPlayerName[] ;
   private Label secondPlayerName[] ;
   private JFXButton loadGameButton[] ;
   private JFXButton replayGameButton[] ;
   private VBox namesVbox[] ;
   private HBox loadFileBoxes[] ;


    String themeLight ;
    String themeDark ;

    SaveAndLoad saveAndLoad = new SaveAndLoad();

    File dir = new File("./src/data/SavedGames");


    public void openLoadList() {
        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();
        mainLoadList = new VBox();
        mainScrollPane = new ScrollPane();
        File filesArray[] =  dir.listFiles();
        if (filesArray != null) {
                gamesName = new Label[filesArray.length];
                firstPlayerName = new Label[filesArray.length];
                secondPlayerName = new Label[filesArray.length];
                namesVbox = new VBox[filesArray.length];
                loadGameButton = new JFXButton[filesArray.length];
                replayGameButton = new JFXButton[filesArray.length];
                loadFileBoxes = new HBox[filesArray.length];
                for (int i =0 ; i<filesArray.length ; i++){
                    saveAndLoad.getPLayersName(filesArray[i]);
                    System.out.println(filesArray[i].getName());
                    gamesName[i] = new Label();
                    gamesName[i].setText(filesArray[i].getName()) ;
                    firstPlayerName[i] = new Label();
                    firstPlayerName[i].getStyleClass().add("defaultLabel");
                    firstPlayerName[i].setText(saveAndLoad.playersNames.get(0));
                    secondPlayerName[i] = new Label();
                    if (saveAndLoad.numP==2)
                    secondPlayerName[i].setText(saveAndLoad.playersNames.get(1));
                    namesVbox[i] = new VBox();
                    namesVbox[i].setId("namesBox");
                    namesVbox[i].getChildren().addAll(gamesName[i] ,firstPlayerName[i] , secondPlayerName[i]);
                    loadGameButton[i] = new JFXButton("Load Game");
                    loadGameButton[i].getStyleClass().add("button-raised");
                    loadGameButton[i].setId("loadListButton");
                    HBox.setMargin(loadGameButton[i], new Insets(10 ,10 , 10 , 20));
                    loadGameButton[i].setOnAction(e -> {
                        System.out.println("Game Loaded");
                    });
                    //
                    replayGameButton[i] = new JFXButton("Replay");
                    replayGameButton[i].getStyleClass().add("button-raised");
                    replayGameButton[i].setId("loadListButton");
                    HBox.setMargin(replayGameButton[i], new Insets(10 ,0 , 10 , 70));
                    replayGameButton[i].setOnAction(e -> {

                    });
                    loadFileBoxes[i] = new HBox();
                    loadFileBoxes[i].getStylesheets().add(themeLight);
                    loadFileBoxes[i].getStyleClass().add("loadedFileBox");
                    loadFileBoxes[i].getChildren().addAll( namesVbox[i] , replayGameButton[i], loadGameButton[i]);
                    VBox.setMargin(loadFileBoxes[i], new Insets(10));
                    mainLoadList.getStylesheets().add(themeLight);
                    mainLoadList.getChildren().add(loadFileBoxes[i]);

                }
                window.setTitle("Mine Sweeper Material");
                mainScrollPane.setContent(mainLoadList);
                scene = new Scene(mainScrollPane , 1100, 700);
                window.setTitle("Load A Game");
                window.setScene(scene);
                window.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty History");
                alert.setContentText("You don't Have Saved Games Yet go play then you can load your old games");
                alert.showAndWait();
            }

    }
}
