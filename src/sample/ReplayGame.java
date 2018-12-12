package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReplayGame extends Thread {
    public SaveAndLoad saveAndLoad = new SaveAndLoad();
    GUIGame guiGame = new GUIGame();
    Stage window = new Stage();
    BorderPane borderPane = new BorderPane();
    DataInfo dataInfo;
    int[] settings;


    @Override
    public void run() {
        try {

            Platform.runLater(() -> {
                init();
            });

        } catch (IllegalStateException e) {

        }
    }

    private void loadInfos() {
        saveAndLoad.loadGameStateBinary();
        dataInfo = saveAndLoad.get_dataInfo();
        settings = saveAndLoad.get_dataInfo().getAllsettingNumber();
        guiGame = new GUIGame(dataInfo.getPlayers(), dataInfo.getGrid(), settings[0], settings[1], settings[2], settings[3],
                dataInfo.isSettingsActivated(), dataInfo.getGameMode());
        guiGame.playerMoves = dataInfo.getPlayerMoves();
        guiGame.scores = dataInfo.getScores();

    }

    public void init() {
        loadInfos();
        window.setWidth(850);
        window.setHeight(850);
        window.show();
        if (dataInfo.getPlayers().size() > 1)
            window.setScene(guiGame.returnScene(dataInfo.getGrid().getWidth(), dataInfo.getGrid().getHeight(),
                    dataInfo.getPlayers().get(0).getName(), dataInfo.getPlayers().get(1).getName(),
                    1100, 700, false, String.valueOf(settings[3])));

        window.setScene(guiGame.returnScene(dataInfo.getGrid().getWidth(), dataInfo.getGrid().getHeight(),
                dataInfo.getPlayers().get(0).getName(), "",
        1100, 700, false, String.valueOf(settings[3])));
    }


}
