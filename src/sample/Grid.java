package sample;


import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Grid implements Serializable {
    public  int width;
    public  int height;
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public int numberofMines = 0;
    public int numberofFlags = 0;

    public int getNumberofMines() {
        return numberofMines;
    }

    public void setNumberofMines(int numberofMines) {
        this.numberofMines = numberofMines;
    }

    public  int getHeight() {
        return height;
    }

    private Square[][] gameGround;


    public Square[][] getGameGround() {
        return gameGround;
    }

    public void setGameGround(Square[][] gameGround) {
        this.gameGround = gameGround;
    }


    public int getWidth() {
        return width;
    }

    public void setHeight(int h) {
        height = h;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Grid(int w, int h) {
        setWidth(w);
        setHeight(h);
        gameGround = new Square[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Square s = new Square();
                s.setI(i);
                s.setJ(j);
                gameGround[i][j] = s;

            }

        }

    }


    public Grid(int w, int h, int BombsNum) {
        this(w, h);
        int n = 0;
        int v;

        if((w*h)>100){
            v = (int) (((w * h) - BombsNum) * (0.01));
        } else{
             v = (int) (((w * h) - BombsNum) * (0.1));
        }
        int shieldNum = 0;
        while (n < BombsNum || v!=0) {
            double randomi = Math.random()*(h-1);
            double randomj = Math.random()*(w-1);
            {
                    Square square = gameGround[Math.round((float)randomi) ][Math.round((float)randomj)];
                    if (!square.isHasBomb() && n < BombsNum) {
                        square.setHasBomb(true);
                        n++;
                        numberofMines++;

                     }

                    else if (!square.isHasBomb()){
                       if(!square.isHasShield())
                           square.setHasShield(true);
                            v--;

                    }
            }

        }
    }


    public Square getIndex(int i, int j) {
        return gameGround[i][j];
    }

    public void printPatch() {
        System.out.print("  ");
        for (int i = 0; i < width; i++)
            System.out.print(alphabet[i] + " ");
        System.out.println();
        for (int i = 0; i < height; i++) {
            System.out.print(i + " ");

            for (int j = 0; j < width; j++) {

                System.out.print(gameGround[i][j].getMark() + " ");
            }
            System.out.println();
        }

    }


}





