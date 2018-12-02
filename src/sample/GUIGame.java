package sample;


import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
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

enum GameMode {Loaded , Replayed ,}
public class GUIGame extends NormalGame {

    // variables //

    playerMove playerM;
    SaveAndLoad saveOrLoad = new SaveAndLoad();
    Scene gridScene;
    GridPane gridPane;
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

    /// lists ///

    ArrayList<Player> players = new ArrayList<>();
    ArrayList<playerMove> playerMoves = new ArrayList<>();

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

    //// contsructors ////

    public GUIGame(ArrayList<Player> _players, String bmb, String blnk, String flag, String shields, boolean a) {
        this.players = _players;
        bombScore = Integer.parseInt(bmb);
        blankScore = Integer.parseInt(blnk);
        flagScore = Integer.parseInt(flag);
        shieldsCount = Integer.parseInt(shields);
        actSettings = a;
        playerM = new playerMove(players.get(0));
        timer = new PlayerTimer(this);


    }

    public GUIGame(ArrayList<Player> _players) {
        this.players = _players;
        playerM = new playerMove(players.get(0));
        timer = new PlayerTimer(this);


    }

    GUIGame() {


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
    public boolean acceptMove(playerMove playerMove, moveType mType, ArrayList<Player> players) {
        Square cell = playerMove.getSquare();

        int i = cell.getI();
        int j = cell.getJ();
        Button btn = getButtonByRowAndColumn(i, j, gridPane);
        if (cell.getState() == squareState.STATE_CLOSED && mType == moveType.Reveal) {
            if (!cell.isHasBomb()) {
                openBlankCells(i, j, playerMove);
                if (playerMove.getPlayer().isAuto())
                    btn.setStyle("-fx-background-color: #aa1c00");

                return true;
            }
        } else if (cell.getState() == squareState.STATE_FLAG && mType == moveType.Unmark) {
            btn.setGraphic(null);
            return true;
        } else if (cell.getState() == squareState.STATE_CLOSED && mType == moveType.Mark) {
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
        if (playerMove.getPlayer().isAuto() && cell.getState() == squareState.STATE_OPENED)
        {
          autoPlayer autoPlayer = (autoPlayer)playerMove.getPlayer();
            playerMove = autoPlayer.dumbMove(grid);
            return acceptMove(playerMove,moveType.Reveal,players);
        }
        return false;
    }


    @Override
    protected void checkBombCells() {
        int score = playerM.getPlayer().getScore().getPlayerscore();

        for (int i = 0; i < Grid.height; i++) {
            for (int j = 0; j < Grid.width; j++) {
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
                    shieldLabelLeft.setText(playerMove.getPlayer().getShield().getShieldCount() + "");
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

    public GridPane setGridPaneContent(int w, int h , int buttonWidth, int buttonHeight) {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-border-style: solid ; -fx-border-width: 2px ; -fx-border-color: red ;");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Button gridButton = new Button("");
                gridButton.setId("gridButton");
                //gridButton.setPrefSize(50 , 50);
                gridButton.setMinHeight(30);
                gridButton.setMinWidth(40);
                GridPane.setMargin(gridButton, new Insets(1));
                gridPane.getChildren().add(gridButton);
                GridPane.setConstraints(gridButton, j, i);
                gridButton.setOnMouseClicked(event -> {

                    Square square = grid.getIndex(gridPane.getRowIndex(gridButton), gridPane.getColumnIndex(gridButton));
                    boolean result = OnClick(event, square, currPlayerLabel);
                    if (!result)
                        players.remove(playerM.getPlayer());
                });

            }
        }
        return  gridPane;
    }


    public GridPane loadedGridPane(Grid grid , int buttonWidth, int buttonHeight ) {
        saveOrLoad.loadGameStateBinary();
        Square gameGround[][]= saveOrLoad.grid.getGameGround();
        gridPane = new GridPane();
        for (int i =0 ; i < saveOrLoad.grid.getHeight() ; i++){
            for(int j=0 ; j < saveOrLoad.grid.getWidth() ; j++){
                Button gridButton = new Button("");
                gridButton.setId("gridButton");
                gridButton.setPrefSize(50 , 50);
                if(gameGround[i][j].getState() == squareState.STATE_OPENED){
                    gridButton.setText(gameGround[i][j].getMark());
                    gridButton.setDisable(true);
                }
                else if (gameGround[i][j].getState() == squareState.STATE_CLOSED){
                    gridButton.setText("");
                }
                else if(gameGround[i][j].getState() == squareState.STATE_FLAG){
                    Image image = new Image(getClass().getResourceAsStream("flag.png"), 40, 40, true, true);
                    gridButton.setGraphic(new ImageView(image));
                }
                else if(gameGround[i][j].isHasBomb() && gameGround[i][j].getState() == squareState.STATE_OPENED){
                    Image image = new Image(getClass().getResourceAsStream("mine.png"), 40, 40, true, true);
                    gridButton.setGraphic(new ImageView(image));
                    gridButton.setDisable(true);
                }
                GridPane.setMargin(gridButton, new Insets(1));
                gridPane.getChildren().add(gridButton);
                GridPane.setConstraints(gridButton, j, i);
                gridButton.setOnMouseClicked(event -> {
                    Square square = saveOrLoad.grid.getIndex(gridPane.getRowIndex(gridButton), gridPane.getColumnIndex(gridButton));
                    boolean result = OnClick(event, square, currPlayerLabel);
                    if (!result)
                        players.remove(playerM.getPlayer());
                });
            }
        }
        return gridPane;
    }

    public HumanPlayer guiPlayer;

    String themepath;


    public Scene returnScene(int w, int h, String name, String name2, int sceneWidth, int sceneHeight, boolean themeSelector, String shieldsNum , boolean loaded) {
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


        if(loaded)
            borderPane.setCenter(setGridPaneContent(w, h , buttonWidth, buttonHeight));
        else
            borderPane.setCenter(loadedGridPane(saveOrLoad.grid , buttonWidth, buttonHeight));

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
        leftMenu.getChildren().addAll(avatar, player1Name, shieldBoxLeft);

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
//        if (players.get(1).isAuto())
//            avatar1 = new ImageView(new Image("./assests/download.png", 75, 75, true, true));
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
        downMenu.getChildren().addAll(cordX, cordY);


        JFXButton saveGameButton = new JFXButton("save");
        saveGameButton.getStyleClass().add("button-raised");
        saveGameButton.setOnAction(e -> {
            saveOrLoad.setPlayerMoves(playerMoves);
            if (players.size() > 1)
                saveOrLoad.saveGameStateBinary(players.get(0) , players.get(1) , grid , bombScore, blankScore,
                        flagScore ,shieldsCount);
            else
                saveOrLoad.saveGameStateBinary(players.get(0) , players.get(0) , grid , bombScore, blankScore,
                        flagScore ,shieldsCount );
        });


        borderPane.setLeft(leftMenu);
        if (players.size() > 1) {
            borderPane.setRight(rightMenu);
        }

        borderPane.setBottom(downMenu);
        borderPane.setCenter(gridPane);
        borderPane.setTop(saveGameButton);
        borderPane.setMinWidth(50);
        borderPane.setMinHeight(100);
        gridScene = new Scene(borderPane);
       try {
           timer.setDaemon(true);

       }catch (Exception r){}
        return gridScene;
    }

    public boolean OnClick(javafx.scene.input.MouseEvent mouseEvent, Square square, Label playerTurnLabel) {
        timer.Reset();

        if (players.size() > 1 && players.get(1).isAuto())
            ip = 0;

        player = this.gameRules.decideNextPlayer(players, ip);


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
            playerM.setPlayer(player);
        }
        playerM.setSquare(square);
        playerMoves.add(playerM);
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            isaccepted = acceptMove(playerM, moveType.Reveal, players);

        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (playerM.getSquare().getState() == squareState.STATE_FLAG)
                isaccepted = acceptMove(playerM, moveType.Unmark, players);
            else
                isaccepted = acceptMove(playerM, moveType.Mark, players);
        }
        if (actSettings)
            getScoreChanges(playerM, true);
        else
            getScoreChanges(playerM, false);

        if (playerM.getPlayer().getResult() == Result.winner) {
            if (players.get(1).getScore().latestScore() > players.get(0).getScore().latestScore()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("WINNER !");
                alert.setContentText(players.get(1).getName() + " " + "You Won the game. You're score is:  " + String.valueOf(players.get(1).getScore().latestScore()));
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("WINNER !");
                alert.setContentText(players.get(0).getName() + " " + "You Won the game. You're score is:  " + String.valueOf(players.get(0).getScore().latestScore()));
                alert.showAndWait();

            }
            endGame();

            return true;

        } else if (!isaccepted) {
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
                playerM.getPlayer().setResult(Result.loser);
                playerM.getPlayer().setScore(0);
                checkBombCells();
                if (playerM.getPlayer().getScore().getPlayerscore() == 0) {
                    grid.printPatch();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("LOSER !");
                    alert.setContentText(playerM.getPlayer().name + " " + "You Lost the game.You're score is:  " + String.valueOf(playerM.getPlayer().getScore().latestScore()));
                    players.remove(playerM.getPlayer());
                    alert.showAndWait();
                }
                return false;
            }

        }
        ip = (ip + 1) % players.size();

        if (players.size() > 1 && players.get(1).isAuto())
            autoMove();

        return true;
    }

    public void getScoreChanges(playerMove pM, boolean activated) {
        Player player = pM.getPlayer();
        Square square = pM.getSquare();
        Score score = player.getScore();
        int s = score.getPlayerscore();

        if (player.getResult() == Result.shielded && numOfShields == grid.numberofMines) {
            if (players.get(0).getScore().latestScore() > players.get(1).getScore().latestScore()) {
                players.get(0).setResult(Result.winner);
            } else {
                players.get(1).setResult(Result.winner);
            }
            endGame();
        }


        if (player.getResult() == Result.loser)
            if (activated) {
                s -= bombScore;
                score.updateScore(s);

            } else
                score.setPlayerscore(0);

        else if (isNumeric(square.getMark()))
            score.updateScore(Integer.parseInt(square.getMark()));

        else if (square.getState() == squareState.STATE_FLAG && square.isHasBomb()) {
            if (activated) {
                int f = score.getPlayerFlagsScore();
                f += flagScore;
                score.updateScore(f);
            } else score.updateScore(5);
            grid.numberofFlags++;
        } else if (square.getState() == squareState.STATE_FLAG && !square.isHasBomb())
            score.updateFlagsScore(-1);

        else if (activated) {
            s += (numOfentries - 1) * blankScore;
            score.updateScore(s);
        } else
            score.updateScore((10 + numOfentries - 1));

        if (grid.numberofFlags == grid.getNumberofMines() || (calculateBlankCells() + grid.getNumberofMines() == Grid.width * Grid.height)) {
            player.setResult(Result.winner);
            System.out.println(player.getName() + " is the WINNER!!! :D");
            score.updateScore(grid.getNumberofMines() * 100);
            endGame();
        }

    }

    public void endGame() {
        for (int i = 0; i < Grid.height; i++)
            for (int j = 0; j < Grid.width; j++) {
                Square cell = grid.getIndex(i, j);
                Button button = getButtonByRowAndColumn(i, j, gridPane);
                if (cell.getState() == squareState.STATE_FLAG || cell.getState() == squareState.STATE_CLOSED)
                    button.setDisable(true);
            }
    }

    public void autoMove() {
        player = this.gameRules.decideNextPlayer(players, 1);
        playerM = ((autoPlayer) player).dumbMove(grid);
        boolean isaccepted = acceptMove(playerM, moveType.Reveal, players);
        getScoreChanges(playerM, false);
        playerMoves.add(playerM);
        if (!isaccepted) {
            checkBombCells();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Computer Lost");
            alert.setContentText("Computer Lost ... The winner is " + players.get(0).getName());
            alert.showAndWait();
        }
    }
}




