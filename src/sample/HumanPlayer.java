package sample;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.scene.control.Button;

public class HumanPlayer extends Player {
    PlayerTimer playertimer;

    @Override
    public playerMove getplayermove(Grid grid) {
        try {
            playertimer = new PlayerTimer();
            playertimer.start();
            playerMove p = new playerMove(this);
            p.setPlayer(this);
            Scanner scan = new Scanner(System.in);
            System.out.print("enter the Row: ");
            int r = scan.nextInt();

            System.out.print("enter the Column: ");
            char c = scan.next().charAt(0);
            int c1 = Character.toUpperCase(c);
            if (c1 < 65 || c1 > 91) {
                System.out.println("you're not entering a character ! try again");
                return getplayermove(grid);
            }
            if (!playertimer.isPossibleToPlay) {
                System.out.println("you're out of time. ");
                p.setPossibleToMove(false);
                return p;
            }

            p.setSquare(grid.getIndex(r, c1 - 65));
            System.out.println("Open = O , Flag = F  :");
            String stat = scan.next();
            if (stat.equals("F") || stat.equals("f"))
                p.setMoveType(moveType.Mark);
            else if (stat.equals("!F") || stat.equals("!f"))
                p.setMoveType(moveType.Unmark);
            else {
                p.setMoveType(moveType.Reveal);
            }
            if (!playertimer.isPossibleToPlay) {
                System.out.println("you're out of time. ");
                p.setPossibleToMove(false);
                return p;

            }
            if (playertimer.getTimer() != 0)
                playertimer.interrupt();


            return p;
        } catch (IndexOutOfBoundsException | NullPointerException | InputMismatchException  e ) {
            System.out.println("You're out of the array !!! ");
            return getplayermove(grid);
        }


    }


}
