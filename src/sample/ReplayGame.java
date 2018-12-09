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

    @Override
    public void run(){
        Platform.runLater(() ->{
            init();
            loadMoves();
        });

    }
    private void loadInfos() {
        saveAndLoad.loadGameStateBinary();
        int[] settings = saveAndLoad.get_dataInfo().getAllsettingNumber();
        dataInfo = saveAndLoad.get_dataInfo();
        guiGame = new GUIGame(dataInfo.getPlayers(), dataInfo.getGrid(), settings[0], settings[1], settings[2], settings[3],
                dataInfo.isSettingsActivated());
    }

    public void init() {
        loadInfos();
        guiGame.grid = dataInfo.getGrid();
        guiGame.gridPane = new GridPane();
        guiGame.gridPane.setAlignment(Pos.CENTER);
        guiGame.gridPane.setStyle("-fx-border-style: solid ; -fx-border-width: 2px ;");
        window.setWidth(750);
        window.setHeight(750);
        for (int i = 0; i < guiGame.grid.getHeight(); i++) {
            for (int j = 0; j < guiGame.grid.getWidth(); j++) {
                guiGame.grid.getGameGround()[i][j].setState(squareState.STATE_CLOSED);
                Button gridButton = new Button("");
             //   gridButton.setId("gridButton");
                gridButton.setPrefSize(window.getWidth() / guiGame.grid.getWidth(),
                        window.getHeight() / guiGame.grid.getHeight());
                gridButton.setMinHeight(30);
                gridButton.setMinWidth(40);
               // borderPane.setTop(new HBox(dataInfo.getTimeList().get(i)));
                GridPane.setMargin(gridButton, new Insets(1));
                guiGame.gridPane.getChildren().add(gridButton);
                guiGame.gridPane.setConstraints(gridButton, j, i);
            }
        }
        guiGame.gridPane.getStylesheets().add("./style.css");

        borderPane.setCenter(guiGame.gridPane);
        window.setScene(new Scene(borderPane));
        window.show();

    }

    public void loadMoves() {
        for (int i = 0; i < dataInfo.getPlayerMoves().size(); i++) {
           // try {
              //  Thread.sleep(1000);
       //     for (int j = 0 ; j < 1000000000; j++ ){}
                playerMove pMove = dataInfo.getPlayerMoves().get(i);

                guiGame.acceptMove(pMove, dataInfo.getPlayers());
            //} catch (InterruptedException e) {
              //  e.printStackTrace();
            //}

        }
    }

}
