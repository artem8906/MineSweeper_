package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.Tile;
import service.GameStudioException;

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

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String nameOfPlayer;

    private Settings settings;

    private void inputName() throws IOException {
        System.out.println("Enter you name");
        nameOfPlayer = reader.readLine();
        if (nameOfPlayer.length() < 1 | nameOfPlayer.length() > 64) {
            throw new GameStudioException("Name is too short or too long. Try again");
        }
    }


    public void setSettings(Settings settings) throws IOException {
        this.settings = settings;
        settings.save();
    }

    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        settings = Settings.load();
        try {
            inputName();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Minesweeper();
        }

        userInterface = new ConsoleUI();
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        userInterface.newGameStarted(field);
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

