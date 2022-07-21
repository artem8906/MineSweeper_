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
    private BestTimes bestTimes = new BestTimes();

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public String nameOfPlayer;

    private Settings settings;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) throws IOException {
        this.settings = settings;
        settings.save();
    }

    public static Minesweeper getInstance() {
        if (instance == null) new Minesweeper();
        return instance;
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
//        System.out.println("Input game level \n B - BEGINNER  \n I - INTERMEDIATE \n E - EXPERT");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            String s = reader.readLine();
//        } catch (IOException e) {
//            e.getMessage();
//        }
        settings = Settings.load();

        try {
            System.out.println("Enter you name");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nameOfPlayer = reader.readLine();

        } catch (IOException e) {
            e.getMessage();
        }

        instance = this;
        userInterface = new ConsoleUI();

        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
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


        new Minesweeper();

    }

}

