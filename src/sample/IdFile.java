package sample;

import java.io.*;
import java.util.ArrayList;

public class IdFile {
    private  String filepath="./src/data/gameID.ran";
    private int i=0;


    public IdFile(){}

    public int getI() {
        this.read();
        return i;
    }

    public void setI(int j) {
        this.write(j);
    }

    private void  write(int j) {
        try {
            j++;
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(j);

            objectOut.close();

            System.out.println("The Object  was succesfully written to  file");

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    private void  writeI() {
        try {
            i=0;
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(i);

            objectOut.close();

            System.out.println("The Object  was succesfully written to  file");

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    private  void read(){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);

            ObjectInputStream objectOut = new ObjectInputStream(fileIn);

            i = (int) objectOut.readObject();

            objectOut.close();

            System.out.println("The Object  was succesfully read from the file");

        } catch (Exception ex) {
           writeI();
        }

    }
}
