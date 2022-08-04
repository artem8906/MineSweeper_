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
    public static long startMillis = System.currentTimeMillis();
    private static Minesweeper instance;
    private BestTimes bestTimes = new BestTimes();

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public String nameOfPlayer;

    private Settings settings;

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) throws IOException {
        this.settings = settings;
        settings.save();
    }

    public static Minesweeper getInstance() throws IOException {
        if (instance == null) {new Minesweeper();}
        return instance;
    }

    /**
     * Constructor.
     */
    private Minesweeper() throws IOException {
        instance = this;
        settings = Settings.load();
        try {
            System.out.println("Enter you name");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nameOfPlayer = reader.readLine();
        } catch (IOException e) {
            e.getMessage();
        }


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
    public static void main(String[] args) throws IOException {

        new Minesweeper();

    }

}

