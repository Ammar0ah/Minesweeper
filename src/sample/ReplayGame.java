package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class    ReplayGame {
   public SaveAndLoad saveAndLoad = new SaveAndLoad();
    GUIGame guiGame = new GUIGame();
    Stage window = new Stage();
    BorderPane borderPane = new BorderPane();

    private void loadInfos() {
        saveAndLoad.loadGameStateBinary();
        int[] settings = saveAndLoad.getAllsettingNumber();
        guiGame = new GUIGame(saveAndLoad.getPlayers(), settings[0], settings[1], settings[2], settings[3],
                saveAndLoad.isSettingsActivated());
    }

    public Stage init() {
        loadInfos();
        guiGame.grid = saveAndLoad.getGrid();
        guiGame.gridPane = new GridPane();
        guiGame.gridPane.setAlignment(Pos.CENTER);
        guiGame.gridPane.setStyle("-fx-border-style: solid ; -fx-border-width: 2px ;");
        for (int i = 0; i < guiGame.grid.getHeight(); i++) {
            for (int j = 0; j < guiGame.grid.getWidth(); j++) {
                guiGame.grid.getGameGround()[i][j].setState(squareState.STATE_CLOSED);
                Button gridButton = new Button("");
                gridButton.setId("gridButton");
                gridButton.setPrefSize(window.getWidth()/guiGame.grid.getWidth(),
                        window.getHeight()/guiGame.grid.getHeight());
                gridButton.setMinHeight(30);
                gridButton.setMinWidth(40);
                GridPane.setMargin(gridButton, new Insets(1));
                guiGame.gridPane.getChildren().add(gridButton);
                guiGame.gridPane.setConstraints(gridButton, j, i);
            }
        }
        guiGame.gridPane.getStylesheets().add("./style.css");
        for (int i = 0; i < saveAndLoad.getPlayerMoves().size(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playerMove pMove = saveAndLoad.getPlayerMoves().get(i);
            guiGame.acceptMove(pMove,saveAndLoad.getPlayers());
        }

        borderPane.setCenter(guiGame.gridPane);
        window.setScene(new Scene(borderPane));
        return window;
    }

}
