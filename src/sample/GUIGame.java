package sample;


import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;

enum GameMode {NEW_GAME, CAN_BE_LOADED, CAN_BE_REPLAYED}

public class GUIGame extends NormalGame {

    // variables //

    playerMove playerM;
    SaveAndLoad saveOrLoad = new SaveAndLoad();
    Scene gridScene;
    GridPane gridPane;
    private String themepath;
    boolean actSettings = false;
    boolean isChangedPlayer = false;
    public int blankScore;
    public int flagScore;
    public int bombScore;
    public int shieldsCount;
    Player currPlayer;
    PlayerTimer timer;
    int numOfShields = 0;
    int ip = 0;
    boolean isaccepted = false;
    public boolean openedThemeSelctor;
    ScoreBoard sb = new ScoreBoard();
    private String filepath;
    GameMode gameMode;
    private DataInfo dataInfo;
    /// lists ///
    ObservableList<ScoreBoard> scoreBoards = FXCollections.observableArrayList();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<playerMove> playerMoves = new ArrayList<>();
    ArrayList<Integer> timeList = new ArrayList<>();
    /// gui ///

    ImageView timerCircle = new ImageView(
            new Image("./assests/circle.png")
    );
    Label cordX = new Label();
    Label cordY = new Label();
    Label res = new Label();
    Label winner = new Label();
    Label shieldLabelLeft = new Label();
    Label shieldLabelRight;
    Label currPlayerLabel = new Label();
    Label timerLabel = new Label();
    Label scorelabel = new Label();
    JFXButton nextBtn = new JFXButton();


    //// contsructors ////

    public GUIGame(ArrayList<Player> _players, Grid grid1, int bmb, int blnk, int flag, int shields, boolean a, GameMode gameMode1) {
        this.players = _players;
        bombScore = bmb;
        blankScore = blnk;
        flagScore = flag;
        shieldsCount = shields;
        actSettings = a;
        grid = grid1;
        playerM = new playerMove(players.get(0));
        timer = new PlayerTimer(this);
        filepath = "./src/data/SaverGames/SavedData1.ran";
        dataInfo = new DataInfo(grid, _players, bmb, blnk, flag, shields, a);
        gameMode = gameMode1;

    }

    public GUIGame(ArrayList<Player> _players, Grid grid1) {
        this.players = _players;
        grid = grid1;
        playerM = new playerMove(players.get(0));
        timer = new PlayerTimer(this);
        dataInfo = new DataInfo(grid, _players);

    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    GUIGame() {

        gameMode = GameMode.NEW_GAME;
    }

    /////////// functions ////////////////

    public Button getButtonByRowAndColumn(int row, int column, GridPane gridPane) {
        Button result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = (Button) node;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean acceptMove(playerMove playerMove, ArrayList<Player> players) {
        Square cell = playerMove.getSquare();

        int i = cell.getI();
        int j = cell.getJ();
        Button btn = getButtonByRowAndColumn(i, j, gridPane);
        if ((cell.getState() == squareState.STATE_CLOSED) && (playerMove.getMoveType() == moveType.Reveal)) {
            if (!cell.isHasBomb()) {
                openBlankCells(i, j, playerMove);
                if (playerMove.getPlayer().isAuto())
                    btn.setStyle("-fx-background-color: #aa1c00");

                return true;
            }
        } else if ((cell.getState() == squareState.STATE_FLAG) && (playerMove.getMoveType() == moveType.Unmark)) {
            btn.setGraphic(null);
            return true;
        } else if ((cell.getState() == squareState.STATE_CLOSED) && (playerMove.getMoveType() == moveType.Mark)) {
            Image image = new Image(getClass().getResourceAsStream("flag.png"), 20, 20, true, true);
            btn.setGraphic(new ImageView(image));
            cell.setState(squareState.STATE_FLAG);
            return true;
        }
        if (cell.isHasBomb()) {
            if (playerMove.getPlayer().isAuto()) {
                btn.setStyle("-fx-background-color: rgba(170,28,0,0.42)");
                checkBombCells();
            }
            if (playerM.getPlayer().getShield().getShieldCount() != 0 && !playerMove.getPlayer().isAuto()) {
                Image image = new Image(getClass().getResourceAsStream("mine.png"), 20, 20, true, true);
                btn.setGraphic(new ImageView(image));
                cell.setState(squareState.STATE_OPENED);
                return false;

            } else {
                playerM.getPlayer().setResult(Result.loser);
                if (players.size() == 1)
                    checkBombCells();
            }
            return false;
        }
        if (playerMove.getPlayer().isAuto() && cell.getState() == squareState.STATE_OPENED) {
            autoPlayer autoPlayer = (autoPlayer) playerMove.getPlayer();
            playerMove = autoPlayer.dumbMove(grid);
            playerMove.setMoveType(moveType.Reveal);
            return acceptMove(playerMove, players);
        }
        return false;
    }


    @Override
    protected void checkBombCells() {

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Square temp = grid.getGameGround()[i][j];
                Button btn = getButtonByRowAndColumn(i, j, gridPane);
                if (temp.isHasBomb() && (temp.getState() == squareState.STATE_CLOSED)) {
                    temp.setMark("&");

                    Image image = new Image(getClass().getResourceAsStream("mine.png"), 40, 40, true, true);
                    btn.setGraphic(new ImageView(image));

                } else if (temp.isHasBomb()) {
                    temp.setMark("$");
                    Image image = new Image(getClass().getResourceAsStream("mine.png"), 40, 40, true, true);
                    btn.setGraphic(new ImageView(image));


                } else if ((temp.getState() == squareState.STATE_FLAG) && temp.isHasBomb()) {
                    temp.setMark("F");
                    Image image = new Image(getClass().getResourceAsStream("flag.png"), 40, 40, true, true);
                    btn.setGraphic(new ImageView(image));

                } else if (temp.getState() == squareState.STATE_FLAG && !temp.isHasBomb()) {
                    temp.setMark("F!");
                    btn.setText("F!");
                    Image image = new Image(getClass().getResourceAsStream("wrongflag.png"), 40, 40, true, true);
                    btn.setGraphic(new ImageView(image));

                }
                btn.setDisable(true);
            }
        }
    }


    @Override
    protected int findMines(Square cell) {
        int sumofMines = 0;
        cell.setState(squareState.STATE_OPENED);
        Button btn = getButtonByRowAndColumn(cell.getI(), cell.getJ(), gridPane);
        for (int i = cell.getI() - 1; i <= cell.getI() + 1; i++) {
            for (int j = cell.getJ() - 1; j <= cell.getJ() + 1; j++) {
                if (isSafe(i, j))
                    if (grid.getGameGround()[i][j].isHasBomb()) {
                        sumofMines++;
                        grid.getGameGround()[i][j].setClicked(true);
                    }
            }
        }
        cell.setMark(String.valueOf(sumofMines));
        btn.setText(sumofMines + "");
        btn.setDisable(true);
        return sumofMines;
    }


    @Override
    public int openBlankCells(int i, int j, playerMove playerMove) {
        {

            if (!isSafe(i, j))
                return numOfentries;

            Square cell = grid.getGameGround()[i][j];
            Button button = getButtonByRowAndColumn(i, j, gridPane);

            if (cell.isClicked())
                return numOfentries;

            if (findMines(cell) == 0) {
                ++numOfentries;
                cell.setMark("-");
                cell.setState(squareState.STATE_OPENED);
//                button.setStyle("-fx-background-color: #002c00;");
                button.setId("clickedButton");
                if (openedThemeSelctor)
                    button.getStylesheets().add(themepath);
                else
                    button.getStylesheets().add(themepath);
                cell.setState(squareState.STATE_OPENED);
                cell.setClicked(true);

                if (cell.isHasShield() && !playerMove.getPlayer().isAuto()) {
                    playerMove.getPlayer().getShield().updateShildCount(1);
                    System.out.println(playerMove.getPlayer().getShield().getShieldCount());
                    button.setText("");
                    Image image = new Image(getClass().getResourceAsStream("shield.png"), 40, 40, true, true);
                    button.setGraphic(new ImageView(image));
                    if (players.get(0).getName() == playerMove.getPlayer().getName()) {
                        shieldLabelLeft.setText(playerMove.getPlayer().getShield().getShieldCount() + "");
                    } else {
                        shieldLabelRight.setText(playerMove.getPlayer().getShield().getShieldCount() + "");
                    }
                }

                openBlankCells(i, j + 1, playerMove);
                openBlankCells(i, j - 1, playerMove);
                openBlankCells(i + 1, j, playerMove);
                openBlankCells(i - 1, j, playerMove);
                openBlankCells(i - 1, j - 1, playerMove);
                openBlankCells(i + 1, j - 1, playerMove);
                openBlankCells(i + 1, j + 1, playerMove);
                openBlankCells(i - 1, j + 1, playerMove);

            }
            return numOfentries;
        }
    }


    //////////////////////////////////////////////////////////////////

    public GridPane setGridPaneContent(int w, int h, int buttonWidth, int buttonHeight) {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Button gridButton = new Button("");
                dataInfo.getGrid().getIndex(i, j).setState(squareState.STATE_CLOSED);
                dataInfo.getGrid().getIndex(i, j).setClicked(false);
                gridButton.setId("gridButton");
                gridButton.setPrefSize(buttonWidth, buttonHeight);
                gridButton.setMinHeight(30);
                gridButton.setMinWidth(40);
                GridPane.setMargin(gridButton, new Insets(1));
                gridPane.getChildren().add(gridButton);
                GridPane.setConstraints(gridButton, j, i);
                if (gameMode == GameMode.NEW_GAME)
                    gridButton.setOnMouseClicked(event -> {
                        dataInfo.getTimeList().add(timer.getTimer());
                        Square square = grid.getIndex(gridPane.getRowIndex(gridButton), gridPane.getColumnIndex(gridButton));
                        boolean result = OnClick(event, square, currPlayerLabel);
                        if (!result)
                            timer.interrupt();
                    });
            }
        }
        for (playerMove pm : playerMoves) {
            nextBtn.setOnMouseClicked(e -> {
                acceptMove(pm, players);
                scorelabel.setText(pm.getPlayer().getScore().getLatestScore() + "");
            });
        }
        return gridPane;
    }


    public GridPane loadedGridPane(int buttonWidth, int buttonHeight) {
        Square gameGround[][] = this.dataInfo.getGrid().getGameGround();
        this.dataInfo.getGrid().printPatch();
        gridPane = new GridPane();
        for (int i = 0; i < this.dataInfo.getGrid().getHeight(); i++) {
            for (int j = 0; j < this.dataInfo.getGrid().getWidth(); j++) {
                Button gridButton = new Button("");
                gridButton.setId("gridButton");
                gridButton.setPrefSize(buttonWidth, buttonHeight);
                if (gameGround[i][j].getState() == squareState.STATE_OPENED) {
                    gridButton.setText(gameGround[i][j].getMark());
                    gridButton.setDisable(true);
                } else if (gameGround[i][j].getState() == squareState.STATE_CLOSED) {
                    gridButton.setText("");
                } else if (gameGround[i][j].getState() == squareState.STATE_FLAG) {
                    Image image = new Image(getClass().getResourceAsStream("flag.png"), 40, 40, true, true);
                    gridButton.setGraphic(new ImageView(image));
                } else if (gameGround[i][j].isHasBomb() && gameGround[i][j].getState() == squareState.STATE_OPENED) {
                    Image image = new Image(getClass().getResourceAsStream("mine.png"), 40, 40, true, true);
                    gridButton.setGraphic(new ImageView(image));
                    gridButton.setDisable(true);
                }
                GridPane.setMargin(gridButton, new Insets(1));
                gridPane.getChildren().add(gridButton);
                GridPane.setConstraints(gridButton, j, i);
                if (gameMode == GameMode.CAN_BE_LOADED)
                    gridButton.setOnMouseClicked(event -> {
                        this.dataInfo.getTimeList().add(timer.getTimer());
                        Square square = this.dataInfo.getGrid().getIndex(GridPane.getRowIndex(gridButton), GridPane.getColumnIndex(gridButton));
                        boolean result = OnClick(event, square, currPlayerLabel);
                        if (!result)
                            timer.interrupt();
                    });


            }
        }
        return gridPane;
    }


    public Scene returnScene(int w, int h, String name, String name2, int sceneWidth, int sceneHeight,
                             boolean themeSelector, String shieldsNum) {
        timer.setPlayerMove(playerM);
        timer.start();
        if (themeSelector) {
            themepath = "./DarkStyle.css";
            openedThemeSelctor = true;
        } else if (!themeSelector) {
            themepath = "./style.css";
            openedThemeSelctor = false;
        }


        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(themepath);


        Label player1Name = new Label(name);
        player1Name.setAlignment(Pos.CENTER);
        player1Name.getStyleClass().add("settingsLabel");
        Label scorePlayer1 = new Label("_Number");
        scorePlayer1.setAlignment(Pos.CENTER_RIGHT);


        Label player2Name = new Label(name2);
        player2Name.setAlignment(Pos.CENTER);
        player2Name.getStyleClass().add("settingsLabel");
        Label scorePlayer2 = new Label("_Number");
        scorePlayer2.setAlignment(Pos.CENTER_RIGHT);

        ////////////////////////////
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        int buttonWidth = (sceneWidth / w) - 10;
        int buttonHeight = (sceneHeight / h) - 10;


        if (gameMode == GameMode.NEW_GAME || gameMode == GameMode.CAN_BE_REPLAYED)
            borderPane.setCenter(setGridPaneContent(w, h, buttonWidth, buttonHeight));
        else
            borderPane.setCenter(loadedGridPane(buttonWidth, buttonHeight));

        //Common Shield Img
        ImageView shieldImgLeft = new ImageView(
                new Image("./assests/shield.png", 40, 40, true, true)
        );
        /////////////////////////////////////////


        TranslateTransition transition = new TranslateTransition();
        transition.setToX(1890);
        transition.setDuration(Duration.seconds(10));
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(timerCircle);
        transition.play();
        ///label timer
        HBox timerView = new HBox();
        timerView.setAlignment(Pos.TOP_CENTER);
        timerView.minWidth(150);
        timerLabel = new Label("10");
        timerLabel.setFont(Font.font(60));
        timer.TimerGUI(timerLabel);
        Label turnLabel = new Label(" turn");
        turnLabel.setFont(Font.font(60));
        currPlayerLabel = new Label(playerM.getPlayer().getName());
        currPlayerLabel.setFont(Font.font(60));

        timerView.setPadding(new Insets(30));
        timerView.getChildren().addAll(currPlayerLabel, timerLabel, turnLabel);

        HBox circleBox = new HBox();

        circleBox.getChildren().addAll(timerCircle);

        VBox topMenuBox = new VBox();
        topMenuBox.setAlignment(Pos.CENTER);
        topMenuBox.getChildren().addAll(timerView, circleBox);
        ////////////////
        VBox leftMenu = new VBox();
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setMinWidth(150);
        double random = Math.random() * 9;
        String picUrl = "./assests/pic" + Math.round(random) + ".png";
        ImageView avatar = new ImageView(
                new Image(picUrl, 75, 75, true, true)
        );
        avatar.getStyleClass().add("avatarStyle");
        HBox shieldBoxLeft = new HBox();
        shieldLabelLeft.setText(shieldsNum);
        shieldBoxLeft.getChildren().addAll(shieldImgLeft, shieldLabelLeft);
        leftMenu.getChildren().addAll(avatar, player1Name, shieldBoxLeft, scorelabel);

        //////
        ImageView shieldImgRight = new ImageView(
                new Image("./assests/shield.png", 40, 40, true, true)
        );
        ///

        VBox rightMenu = new VBox();
        rightMenu.setMinWidth(150);
        rightMenu.setAlignment(Pos.CENTER);
        double random1 = Math.random() * 9;
        String picUrl1 = "./assests/pic" + Math.round(random1) + ".png";
        ImageView avatar1 = new ImageView(
                new Image(picUrl1, 75, 75, true, true)
        );
        if (players.size() > 1 && players.get(1).isAuto())
            avatar1 = new ImageView(new Image("./assests/download.png", 75, 75, true, true));
        avatar.getStyleClass().add("avatarStyle");
        HBox shieldBoxRight = new HBox();
        shieldLabelRight = new Label(shieldsNum);
        shieldBoxRight.getChildren().addAll(shieldImgRight, shieldLabelRight);
        rightMenu.getChildren().addAll(avatar1, player2Name, shieldBoxRight);

        HBox downMenu = new HBox();
        downMenu.setAlignment(Pos.CENTER);
        downMenu.setMinHeight(60);
        cordX.setAlignment(Pos.CENTER);
        cordY.setAlignment(Pos.CENTER);
        cordX.getStyleClass().add("gameText");
        cordY.getStyleClass().add("gameText");
        downMenu.getChildren().addAll(nextBtn);
        HBox topItems = new HBox();

        if (gameMode != GameMode.CAN_BE_REPLAYED) {
            JFXButton saveGameButton = new JFXButton("save");
            saveGameButton.getStyleClass().add("button-raised");
            saveGameButton.setOnAction(e -> {
                dataInfo.setGameMode(GameMode.CAN_BE_LOADED);
                dataInfo.setPlayerMoves(playerMoves);
                saveOrLoad.set_dataInfo(dataInfo);
                saveOrLoad.saveGameStateBinary();

            });
            topItems.getChildren().addAll(saveGameButton, timerView);

        } else
            topItems.getChildren().addAll(timerView);


        borderPane.setLeft(leftMenu);
        if (players.size() > 1) {
            borderPane.setRight(rightMenu);
        }

        borderPane.setBottom(downMenu);
        borderPane.setCenter(gridPane);
        if (gameMode != GameMode.CAN_BE_REPLAYED)
            borderPane.setTop(topItems);
        else timer.interrupt();
        borderPane.setMinWidth(50);
        borderPane.setMinHeight(150);
        gridScene = new Scene(borderPane);
        try {
            timer.setDaemon(true);

        } catch (Exception r) {
        }
        return gridScene;
    }

    public boolean OnClick(javafx.scene.input.MouseEvent mouseEvent, Square square, Label playerTurnLabel) {
        playerM.setSquare(square);
        timer.Reset();

        if (players.size() > 1 && players.get(1).isAuto())
            ip = 0;

        player = this.gameRules.decideNextPlayer(players, ip);
        playerM.setPlayer(player);


        if (players.size() > 1) {
            if (players.get(1).isAuto() && ip == 1) {
                playerTurnLabel.setText(players.get(0).getName());
            } else {
                int turn = ip;
                if (players.size() > 1)
                    if (turn == 1)
                        turn = 0;

                    else turn = 1;
                playerTurnLabel.setText(players.get(turn).getName());

            }
        }
        System.out.println(playerM.getSquare().getI() + " " + playerM.getSquare().getJ());
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            playerM.setMoveType(moveType.Reveal);


        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (playerM.getSquare().getState() == squareState.STATE_FLAG) {
                playerM.setMoveType(moveType.Unmark);

            } else {
                playerM.setMoveType(moveType.Mark);

            }
        }
        isaccepted = acceptMove(playerM, players);
        getScoreChanges(playerM);

        if (!isaccepted) {
            if (!player.isAuto && player.getShield().getShieldCount() != 0) {
                player.getShield().updateShildCount(-1);
                player.setResult(Result.shielded);
                numOfShields++;
                if (player.name.equals(players.get(0).name))
                    shieldLabelLeft.setText(player.getShield().getShieldCount() + "");
                else
                    shieldLabelRight.setText(player.getShield().getShieldCount() + "");

                return true;
            } else {
                gameMode = GameMode.CAN_BE_REPLAYED;
                playerM.getPlayer().setResult(Result.loser);
                playerM.getPlayer().setScore(0);
                checkBombCells();
                if (playerM.getPlayer().getScore().getPlayerscore() == 0) {
                    grid.printPatch();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("LOSER !");
                    alert.setContentText(playerM.getPlayer().name + " " + "You Lost the game.You're score is:  " + String.valueOf(playerM.getPlayer().getScore().getLatestScore()));
                    alert.showAndWait();
                    timer.interrupt();
                    endGame();
                    saveData();
                    updateScoreBoard();
                }
                return false;
            }

        }


        playerMoves.add(playerM);
        ip = (ip + 1) % players.size();
        if (players.size() > 1 && players.get(1).isAuto())
            autoMove();
        if (playerM.getPlayer().getResult() == Result.winner) {
            for (playerMove pm : playerMoves) {
                System.out.println("The moves list");
                System.out.println(pm.getSquare().getI() + " " + pm.getSquare().getJ());
            }
            try {
                if (players.get(1).getScore().getLatestScore() > players.get(0).getScore().getLatestScore()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WINNER !");
                    alert.setContentText(players.get(1).getName() + " " + "You Won the game. You're score is:  " +
                            String.valueOf(players.get(1).getScore().getLatestScore()));
                    alert.showAndWait();

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WINNER !");
                    alert.setContentText(players.get(0).getName() + " " + "You Won the game. You're score is:  " +
                            String.valueOf(players.get(0).getScore().getLatestScore()));
                    alert.showAndWait();

                }

            } catch (IndexOutOfBoundsException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("WINNER !");
                alert.setContentText(players.get(0).getName() + " " + "You Won the game. You're score is:  " +
                        String.valueOf(players.get(0).getScore().getLatestScore()));
                alert.showAndWait();
            }
            saveData();
            updateScoreBoard();
            endGame();

        }
        playerM = new playerMove();


        return true;
    }

    public void getScoreChanges(playerMove pM) {
        Player player = pM.getPlayer();
        Square square = pM.getSquare();
        Score score = player.getScore();
        int s = score.getPlayerscore();

        if (player.getResult() == Result.loser)
            if (actSettings) {
                s -= bombScore;
                score.updateScore(s);

            } else
                score.setPlayerscore(0);

        else if (isNumeric(square.getMark()))
            score.updateScore(Integer.parseInt(square.getMark()));

        else if (square.getState() == squareState.STATE_FLAG && square.isHasBomb()) {
            if (actSettings) {
                int f = score.getPlayerFlagsScore();
                f += flagScore;
                score.updateScore(f);
            } else score.updateScore(5);
            grid.numberofFlags++;
        } else if (square.getState() == squareState.STATE_FLAG && !square.isHasBomb())
            score.updateFlagsScore(-1);

        else if (actSettings) {
            s += (numOfentries - 1) * blankScore;
            score.updateScore(s);
        } else
            score.updateScore((10 + numOfentries - 1));

        if (grid.numberofFlags == grid.getNumberofMines() || (calculateBlankCells() + grid.getNumberofMines() == grid.getWidth() * grid.getHeight())) {
            player.setResult(Result.winner);
            //score.updateScore(grid.getNumberofMines() * 100);
            endGame();
        }

    }

    public void endGame() {
        for (int i = 0; i < grid.getHeight(); i++)
            for (int j = 0; j < grid.getWidth(); j++) {
                Square cell = grid.getIndex(i, j);
                Button button = getButtonByRowAndColumn(i, j, gridPane);
                if (cell.getState() == squareState.STATE_FLAG || cell.getState() == squareState.STATE_CLOSED)
                    button.setDisable(true);
            }
    }

    public void autoMove() {
        player = this.gameRules.decideNextPlayer(players, 1);
        playerM = ((autoPlayer) player).dumbMove(grid);
        playerM.setMoveType(moveType.Reveal);
        boolean isaccepted = acceptMove(playerM, players);
        getScoreChanges(playerM);
        playerMoves.add(playerM);
        if (!isaccepted) {
            saveData();
            updateScoreBoard();
            checkBombCells();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Computer Lost");
            alert.setContentText("Computer Lost ... The winner is " + players.get(0).getName());
            alert.showAndWait();
        }
        if (playerM.getPlayer().getResult() == Result.winner) {
            saveData();
            updateScoreBoard();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Computer win ");
            alert.setContentText("Computer is The winner  " + players.get(1).getScore().getLatestScore());
            alert.showAndWait();
        }
    }

    public void saveData() {
        DataInfo data = new DataInfo(grid, players, playerMoves, bombScore, blankScore, flagScore, shieldsCount, actSettings);
        data.setGameMode(GameMode.CAN_BE_REPLAYED);
        saveOrLoad.set_dataInfo(data);
        saveOrLoad.saveGameStateBinary();
        timer.interrupt();
    }


    public void updateScoreBoard() {
        if (players.size() > 1) {
            sb.setEndDate();
            sb.setP1Name(players.get(0).getName());
            sb.setP2Name(players.get(1).getName());
            sb.setPlayer1Score(players.get(0).getScore().getLatestScore());
            sb.setPlayer2Score(players.get(1).getScore().getLatestScore());

        } else {
            sb.setEndDate();
            sb.setP1Name(players.get(0).getName());
            sb.setPlayer1Score(players.get(0).getScore().getLatestScore());

        }
        if (gameMode == GameMode.CAN_BE_REPLAYED) {
            sb.setCanBeReplayed(true);
            sb.setGamefilepath(saveOrLoad.getSaveNumber());
        }
        try {
            scoreBoards = sb.read();
            scoreBoards.add(sb);
            sb.write(scoreBoards);
        }catch (Exception e){
            sb.write(scoreBoards);
            updateScoreBoard();
        }

    }


}




