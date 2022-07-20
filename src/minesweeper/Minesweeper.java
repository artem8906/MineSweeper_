package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private ConsoleUI userInterface;
    private long startMillis = System.currentTimeMillis();
    private static Minesweeper instance;
    private BestTimes bestTimes;

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public String nameOfPlayer;

    public static Minesweeper getInstance() {
        if (instance == null) new Minesweeper();
        return instance;
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter you name");
            nameOfPlayer = reader.readLine();
        } catch (IOException e) {
            e.getMessage();
        }

        instance = this;
        userInterface = new ConsoleUI();
        Field field = new Field(10, 10, 20);
        userInterface.newGameStarted(field);
    }

    public int getPlayingSeconds() {
        return (int) (System.currentTimeMillis() - startMillis) / 1000;
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
//        BestTimes bt = new BestTimes();
//        bt.addPlayerTime("Artem", 656);
//        bt.addPlayerTime("Baba", 65);
//        System.out.println(bt);

        new Minesweeper();

    }

}

