package sample;


import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Scanner;

public class consoleGame extends NormalGame {
    public consoleGame() {
        super();
    }

    public static void main(String[] args) throws Exception {


        int w, h, nofp, i = 0, co;
        boolean b, comp= false;
        Player player = new HumanPlayer();
        playerMove pMove;
        PlayerTimer timer;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter width: ");
        w = scan.nextInt();
        System.out.println("Enter Height: ");
        h = scan.nextInt();
        System.out.println("Enter number of Mines: ");
        int m = scan.nextInt();
        Grid grid = new Grid(w, h, m);
        consoleGame game = new consoleGame();
        game.setGrid(grid);
        System.out.println("Enter the number of Players: ");
        nofp = scan.nextInt();
        if (nofp == 2) {
            System.out.println("want to play with computer? : (1 or 0)");
            int temp = scan.nextInt();
            if (temp == 1)
                comp = true;
            else
                comp = false;
        }
        ArrayList<Player> players = new ArrayList<>();

        if (comp) {
            players.add(new autoPlayer());
        }
        for (int k = 0; k < nofp; k++) {
            if (comp) {
                k++;
            }
            players.add(new HumanPlayer());
            System.out.print("Set a name for the " + k + " player: ");
            String name = scan.next();
            players.get(k).setName(name);
        }
        grid.printPatch();

        while (nofp > 0) {
            try {
                if(players.size() == 0)
                    break;

                if (i == (players.size()))
                    i = 0;

                player = game.gameRules.decideNextPlayer(players, i);
                System.out.println(player.getName() + "'s turn");
                System.out.println("current score is: " + player.getScore().getPlayerscore());
                if (player.isAuto()) {
                    Player p = players.get(i);
                    Thread.sleep(2000);
                    pMove = ((autoPlayer) p).dumbMove(grid);

                } else {

                    pMove = player.getplayermove(grid);
                    while (!pMove.isPossibleToMove()) {


                        if (players.size() > 1) {
                            i++;
                            if (i == (players.size()))
                                i = 0;

                            player = players.get(i);
                            System.out.println(player.getName() + " turn: ");
                            pMove = (player).getplayermove(grid);
                        } else {
                            System.out.println(player.getName() + "  out of time and lost the game");
                            players.remove(player);
                            break;
                        }
                    }

                }
                if(pMove.isPossibleToMove()) {
                    b = game.acceptMove(pMove, players);
                    if (!b) {
                        if( !player.isAuto && player.getShield().getShieldCount()!=0)
                        {
                            player.getShield().updateShildCount(-1);
                            player.setResult(Result.playing);
                        }
                        else {

                            pMove.getPlayer().setResult(Result.loser);
                            pMove.getPlayer().setScore(0);

                            if(players.size() == 1)
                                game.checkBombCells();

                            if (pMove.getPlayer().getScore().getPlayerscore() <= 0) {
                                grid.printPatch();
                                System.out.println(player.getName() + " lost the game");
                            }
                            if (players.size() == 1)
                                break;
                            players.remove(pMove.getPlayer());
                            nofp--;
                        }

                    }


                    game.gameRules.getScoreChanges(pMove);
                    if (player.getResult() == Result.winner)
                        break;

                    System.out.println("new score for " + player.getName() + " is: " + player.getScore().getPlayerscore());
                    System.out.println(player.getName()+ " shield count " + player.getShield().getShieldCount());

                    grid.printPatch();
                    i++;
                }
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println(ex.getMessage());
                break;

            }
            System.out.println(player.getName() + "Result: " + player.getScore().getLatestScore());

        }

    }
}





