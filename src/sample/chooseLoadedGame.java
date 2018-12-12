package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class chooseLoadedGame {
    private Stage window = new Stage();
    private ScrollPane mainScrollPane;
    private VBox mainLoadList;
    private Scene scene;
    private Label gamesName[];
    private Label firstPlayerName[];
    private Label secondPlayerName[];
    private JFXButton loadGameButton[];
    private JFXButton replayGameButton[];
    private VBox namesVbox[];
    private HBox loadFileBoxes[];


    String themeLight;
    String themeDark;
    String themepath;
    boolean loadedThemeSelctor;

    SaveAndLoad saveAndLoad = new SaveAndLoad();

    File dir = new File("./src/data/SavedGames");


    public void openLoadList(boolean themeSelector) {
        if (themeSelector) {
            themepath = "./DarkStyle.css";
            loadedThemeSelctor = true;
        } else if (!themeSelector) {
            themepath = "./style.css";
            loadedThemeSelctor = false;
        }
        int i = 0;
        themeLight = getClass().getResource("../style.css").toExternalForm();
        themeDark = getClass().getResource("../DarkStyle.css").toExternalForm();
        mainLoadList = new VBox();
        mainScrollPane = new ScrollPane();
        File filesArray[] = dir.listFiles();
        System.out.println("length" + filesArray.length);
        if (filesArray.length > 0) {
            gamesName = new Label[filesArray.length];
            firstPlayerName = new Label[filesArray.length];
            secondPlayerName = new Label[filesArray.length];
            namesVbox = new VBox[filesArray.length];
            loadGameButton = new JFXButton[filesArray.length];
            replayGameButton = new JFXButton[filesArray.length];
            loadFileBoxes = new HBox[filesArray.length];
            for (File file : filesArray) {
                saveAndLoad.getPLayersName(file);
                System.out.println(file.getName());
                gamesName[i] = new Label();
                gamesName[i].setId("loadedGameFileName");
                gamesName[i].setText(file.getName());
                firstPlayerName[i] = new Label();
                firstPlayerName[i].getStyleClass().add("defaultLabel");
                firstPlayerName[i].setText(saveAndLoad.get_dataInfo().getPlayers().get(0).getName());
                secondPlayerName[i] = new Label();
                if (saveAndLoad.get_dataInfo().getPlayers().size() == 2) {
                    secondPlayerName[i].getStyleClass().add("defaultLabel");
                    secondPlayerName[i].setText(saveAndLoad.get_dataInfo().getPlayers().get(1).getName());
                }
                namesVbox[i] = new VBox();
                namesVbox[i].setId("namesBox");
                namesVbox[i].getChildren().addAll(gamesName[i], firstPlayerName[i], secondPlayerName[i]);
                loadGameButton[i] = new JFXButton("Load Game");
                loadGameButton[i].getStyleClass().add("button-raised");
                loadGameButton[i].setId("loadListButton");
                HBox.setMargin(loadGameButton[i], new Insets(10, 10, 10, 20));
                loadGameButton[i].setOnAction(e -> {
                    saveAndLoad.setSaveFile("./src/data/SavedGames/" + file.getName());
                    saveAndLoad.loadGameStateBinary();
                    System.out.println("this" + file.getName());
                    if (saveAndLoad.get_dataInfo().getGameMode() == GameMode.CAN_BE_LOADED) {
                        GUIGame guiGame = new GUIGame(
                                saveAndLoad.get_dataInfo().getPlayers(),
                                saveAndLoad.get_dataInfo().getGrid(),
                                saveAndLoad.get_dataInfo().getAllsettingNumber()[0],
                                saveAndLoad.get_dataInfo().getAllsettingNumber()[1],
                                saveAndLoad.get_dataInfo().getAllsettingNumber()[2],
                                saveAndLoad.get_dataInfo().getAllsettingNumber()[3],
                                saveAndLoad.get_dataInfo().isSettingsActivated(),
                                saveAndLoad.get_dataInfo().getGameMode()
                        );
                        guiGame.scores = saveAndLoad.get_dataInfo().getScores();
                        if (saveAndLoad.get_dataInfo().getPlayers().size() == 1) {
                            window.setScene(guiGame.returnScene(
                                    saveAndLoad.get_dataInfo().getGrid().getWidth(),
                                    saveAndLoad.get_dataInfo().getGrid().getHeight(),
                                    saveAndLoad.get_dataInfo().getPlayers().get(0).getName(),
                                    "",
                                    1100,
                                    700,
                                    loadedThemeSelctor,
                                    saveAndLoad.get_dataInfo().getAllsettingNumber()[3] + ""

                            ));
                        } else {
                            window.setScene(guiGame.returnScene(
                                    saveAndLoad.get_dataInfo().getGrid().getWidth(),
                                    saveAndLoad.get_dataInfo().getGrid().getHeight(),
                                    saveAndLoad.get_dataInfo().getPlayers().get(0).getName(),
                                    saveAndLoad.get_dataInfo().getPlayers().get(1).getName(),
                                    1100,
                                    700,
                                    loadedThemeSelctor,
                                    saveAndLoad.get_dataInfo().getAllsettingNumber()[3] + ""

                            ));
                        }
                        window.show();
                    }

                });

                replayGameButton[i] = new JFXButton("Replay");
                replayGameButton[i].getStyleClass().add("button-raised");
                replayGameButton[i].setId("loadListButton");
                HBox.setMargin(replayGameButton[i], new Insets(10, 0, 10, 70));
                if (saveAndLoad.get_dataInfo().getGameMode() != GameMode.CAN_BE_REPLAYED) {
                    JFXButton btn = replayGameButton[i];
                    btn.setOnMouseEntered(e -> {
                        Tooltip tooltip = new Tooltip("this Game isn't complete and can't be replayed");
                        btn.setTooltip(tooltip);
                        btn.setText("no replay file");
                    });
                    ;
                } else {
                    replayGameButton[i].setOnAction(e -> {
                        ReplayGame replayGame = new ReplayGame();
                        replayGame.saveAndLoad.setSaveFile("./src/data/SavedGames/" + file.getName());
                        replayGame.start();
                    });
                }
                loadFileBoxes[i] = new HBox();
                loadFileBoxes[i].getStylesheets().add(themepath);
                loadFileBoxes[i].getStyleClass().add("loadedFileBox");
                loadFileBoxes[i].getChildren().addAll(namesVbox[i], replayGameButton[i], loadGameButton[i]);
                VBox.setMargin(loadFileBoxes[i], new Insets(10));
                mainLoadList.getStylesheets().add(themepath);
                mainLoadList.getChildren().add(loadFileBoxes[i]);
                i++;
            }
            window.setTitle("Mine Sweeper Material");
            mainScrollPane.setContent(mainLoadList);
            mainScrollPane.getStylesheets().add(themepath);
            scene = new Scene(mainScrollPane, 640, 800);
            window.setTitle("Load A Game");
            window.setScene(scene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty History");
            alert.setContentText("You don't Have Saved Games Yet go play then you can load your old games");
            alert.showAndWait();
        }

    }
}
