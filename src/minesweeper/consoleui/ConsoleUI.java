package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Comment;
import entity.Score;
import minesweeper.Minesweeper;
import minesweeper.Settings;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import service.CommentServiceJDBC;
import service.ScoreService;
import service.ScoreServiceJDBC;

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

    private static final String NAMEGAME = "MinesSweeper";


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
    public void newGameStarted(Field field) throws IOException {

        this.field = field;
        var instance = Minesweeper.getInstance();

        System.out.println("Input game level \n B - BEGINNER  \t I - INTERMEDIATE \t E - EXPERT");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Settings s = switch (reader.readLine()) {
                case "I" -> Settings.INTERMEDIATE;
                case "E" -> Settings.EXPERT;
                default -> Settings.BEGINNER;
            };
        instance.setSettings(s);
        this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
        } catch (IOException e) {
            e.getMessage();
        }
        do {
            update();
            processInput();//open is here

            //no update field output after open a mine
            var fieldState=this.field.getState();

            if (fieldState == GameState.FAILED) {
                new ScoreServiceJDBC().addScore(new Score(NAMEGAME, instance.nameOfPlayer, 0, new Date()));
                System.out.println("Loss. Your score is 0");
                writeYourCommentAndFinalOutput();
                System.exit(0);
            }

            if (fieldState == GameState.SOLVED) {
                new ScoreServiceJDBC().addScore(new Score(NAMEGAME, instance.nameOfPlayer, this.field.getScore(), new Date()));
                System.out.println("Win. Your score is " + this.field.getScore());
                writeYourCommentAndFinalOutput();
                System.exit(0);
            }
        } while (true);
    }

    private void writeYourCommentAndFinalOutput() throws IOException {
        var instance = Minesweeper.getInstance();
        System.out.println("Write you comment");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new CommentServiceJDBC().addComment(new Comment(NAMEGAME, instance.nameOfPlayer, reader.readLine(), new Date()));

        System.out.println("Best scores are");
        System.out.println(new ScoreServiceJDBC().getBestScores(NAMEGAME));

        System.out.println("Last comments are:");
        System.out.println(new CommentServiceJDBC().getComments(NAMEGAME));
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
        } catch (WrongFormatException e) {
            e.getMessage();
            processInput();
        }

    }

    private void handleInput(String input) throws WrongFormatException {

        if (input.equals("X")) return;
        Matcher matcher = PATTERN.matcher(input);
        String action = "", rowStr = "", colStr = "";

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
