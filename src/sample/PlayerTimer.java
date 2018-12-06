package sample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class PlayerTimer extends Thread {
    private int timer;
    private playerMove playerMove;
    private ArrayList<Player> players = new ArrayList<>();
    private int index = 0;
    private GUIGame guiGame;

    public boolean isPossibleToPlay;

    public sample.playerMove getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(sample.playerMove playerMove) {
        this.playerMove = playerMove;
    }

    public PlayerTimer() {
        timer = 10;
        isPossibleToPlay = true;
    }

    public PlayerTimer(GUIGame g) {
        timer = 10;
        isPossibleToPlay = true;
        guiGame = g;
        players = guiGame.players;

    }

    public int getTimer() {
        return timer;
    }

    public boolean dostop() {
        return false;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    timer--;
                    if (timer < 0)
                        if (players.size() > 1)
                            changePlayers();
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("You Lost");
                            alert.setContentText("You're out of time");
                            guiGame.playerM.getPlayer().setResult(Result.loser);
                            guiGame.gameMode = GameMode.CAN_BE_REPLAYED;
                            guiGame.endGame();
                            this.interrupt();
                            alert.showAndWait();

                        }

                });
                if (Thread.interrupted()) {
                    break;
                }

            } catch (InterruptedException | IllegalStateException e) {
                System.out.println(e.getMessage());
                break;

            }
        }
        if (!Thread.interrupted())
            isPossibleToPlay = dostop();

    }

    public void TimerGUI(Label label) {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            label.setText(" " + timer);
        }));

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();


    }

    public void Reset() {
        timer = 10;
    }

    public void changePlayers() {
        index = (index + 1) % 2;
        if (index == players.size())
            index = 0;

        playerMove.setPlayer(guiGame.gameRules.decideNextPlayer(players, index));
        timer = 10;
        guiGame.currPlayerLabel.setText(players.get(index).getName());


    }

}
