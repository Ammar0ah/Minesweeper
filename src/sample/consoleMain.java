package sample;

import java.util.ArrayList;
import java.util.Scanner;

public class consoleMain {
    public static void main(String[] args) {
        int w, h, nofp, i = 0, co;
        boolean b, comp = false;
        Player player = new HumanPlayer();
        playerMove pMove;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter width: ");
        w = scan.nextInt();
        System.out.println("Enter Height: ");
        h = scan.nextInt();
        Grid grid = new Grid(w, h, 4);
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

        while (nofp > 0 || player.getResult() != Result.winner) {
            try {
                if (i == (players.size()))
                    i = 0;

                player = game.gameRules.decideNextPlayer(players, i);
                System.out.println(player.getName() + "'s turn");
                System.out.println("current score is: " + player.getScore().getPlayerscore());
                if (player.isAuto()) {
                    Player p = new autoPlayer();
                    p = players.get(i);
                    Thread.sleep(2000);
                    pMove = ((autoPlayer) p).dumbMove(grid);

                } else
                    pMove = player.getplayermove(grid);


                b = game.acceptMove(pMove, players);
                if (!b) {
                    pMove.getPlayer().setResult(Result.loser);
                    pMove.getPlayer().setScore(0);
                    if (pMove.getPlayer().getScore().getPlayerscore() == 0) {
                        grid.printPatch();
                        System.out.println(player.getName() + " lost the game");
                    }
                    if (players.size() == 1)
                        break;
                    players.remove(pMove.getPlayer());
                    nofp--;

                }


                //}
                game.gameRules.getScoreChanges(pMove);
                System.out.println("new score for " + player.getName() + " is: " + player.getScore().getPlayerscore());

                grid.printPatch();
                i++;

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;

            }
            System.out.println(player.getName() + "Result: " + player.getScore().latestScore());

        }
    }

}