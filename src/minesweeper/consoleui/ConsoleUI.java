package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements minesweeper.UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static final Pattern PATTERN = Pattern.compile("([MO])([A-Za-z])(\\d{1,2})");
    private static final int CHARINDEX = 65;


    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();//open is here

            //no update field output after open a mine

            if (field.getState().equals(GameState.FAILED)) {
                System.out.println("Loss");
                System.exit(0);
            }

            if (field.isSolved()) {
                System.out.println("Win");
                Minesweeper instance = Minesweeper.getInstance();
                instance.getBestTimes().addPlayerTime(instance.nameOfPlayer, instance.getPlayingSeconds());
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        char a = CHARINDEX;
        System.out.print("  ");
        for (int i = 0; i < field.getColumnCount(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            if (a == 91) a += 6;
            System.out.printf("%c ", a);
            a++;
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (j > 9) System.out.print(" ");
                System.out.print(field.getTiles(i, j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        String line = readLine();

        try {
            handleInput(line);
        }
        catch (WrongFormatException e) {
            e.getMessage();
            processInput();
        }

    }

    private void handleInput(String input) throws WrongFormatException {

        if (input.equals("X")) return;
        Matcher matcher = PATTERN.matcher(input);
        String action = "",rowStr = "", colStr = "";

        if (matcher.find()) {
            action = matcher.group(1);
            rowStr = matcher.group(2);
            colStr = matcher.group(3);
        } else processInput();

        int column = Integer.parseInt(colStr);
        int row = (int) rowStr.charAt(0) - CHARINDEX;

        if (column >= field.getColumnCount() | row < 0 | (row > 25 & row < 32) | row > 57 | row >= field.getRowCount()) {
            throw new WrongFormatException("Wrong parameters of tile");
        }

        switch (action) {
            case "M":
                field.markTile(row, column);
                break;
            case "O":
                field.openTile(row, column);
                break;
            default:
                processInput();
        }
    }
}
