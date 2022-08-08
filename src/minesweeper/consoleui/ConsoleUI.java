package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Comment;
import entity.Rating;
import entity.Score;
import minesweeper.Minesweeper;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import service.*;

public class ConsoleUI implements minesweeper.UserInterface {

    private Field field;
    private Settings settings;
    private String nameOfPlayer;
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static final Pattern PATTERN = Pattern.compile("([MO])([A-Za-z])(\\d{1,2})");
    private static final int CHARINDEX = 65;

    private static final String NAMEGAME = "MinesSweeper";
    Minesweeper instance = Minesweeper.getInstance();

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.getMessage();
            return null;
        }
    }

    private void inputName() throws IOException {
        System.out.println("Enter you name");
        nameOfPlayer = input.readLine();
        if (nameOfPlayer.length() < 1 | nameOfPlayer.length() > 64) {
            throw new GameStudioException("Name is too short or too long. Try again");
        }
    }

    @Override
    public void newGameStarted(Field field) {
        this.field = field;

        handleInputName();

        System.out.println("Input game level \n B - BEGINNER  \t I - INTERMEDIATE \t E - EXPERT");
        try {
            Settings s = switch (input.readLine()) {
                case "I" -> Settings.INTERMEDIATE;
                case "E" -> Settings.EXPERT;
                default -> Settings.BEGINNER;
            };
            this.settings = s;
            this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
        } catch (IOException e) {
            e.getMessage();
        }
        do {
            update();
            processInput();

            var fieldState = this.field.getState();

            if (fieldState == GameState.FAILED) {
                new ScoreServiceJDBC().addScore(new Score(NAMEGAME, nameOfPlayer, 0, new Date()));
                System.out.println("Loss. Your score is 0");
                endOfGame();
                System.exit(0);
            }

            if (fieldState == GameState.SOLVED) {
                new ScoreServiceJDBC().addScore(new Score(NAMEGAME, nameOfPlayer, this.field.getScore(), new Date()));
                System.out.println("Win. Your score is " + this.field.getScore());
                endOfGame();
                System.exit(0);
            }
        }

            while (true);
    }

    private void handleInputName() {
        try {
            inputName();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            handleInputName();
        }
    }

    @Override
    public void play() {
        settings = Settings.load();
        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());
        newGameStarted(field);
    }

    private void endOfGame() {
        handlerOfWriterComment();
        handlerOfGivingRate();
        printScoresAndComment();
    }

    private void handlerOfGivingRate() {
        try {
            rateGame();
        } catch (Exception e) {
            System.out.println("From 1 to 5 only. Try again");
            handlerOfGivingRate();
        }
    }

    private void handlerOfWriterComment() {
        try {
            writeComment();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            handlerOfWriterComment();
        }
    }

    private void writeComment() throws IOException {
        System.out.println("Write you comment");
        String comment = input.readLine();
        if (comment.length() > 1000 | comment.length() == 0) {
            throw new GameStudioException("Comment is too long or too short. Try again");
        }
        new CommentServiceJDBC().addComment(new Comment(NAMEGAME, nameOfPlayer, comment, new Date()));
    }

    private void rateGame() throws IOException {
        System.out.println("Rate this game (from 1 to 5)");
        int rate = Integer.valueOf(input.readLine());
        if (rate < 1 | rate > 5) throw new GameStudioException();
        new RatingServiceJDBC().setRating(
                new Rating(NAMEGAME, nameOfPlayer, rate, new Date()));
    }

    private void printScoresAndComment() {
        System.out.println("Best scores are");
        System.out.println(new ScoreServiceJDBC().getBestScores(NAMEGAME));

        System.out.println("Last comments are:");
        System.out.println(new CommentServiceJDBC().getComments(NAMEGAME));

        System.out.println("Average rating of this game is " + new RatingServiceJDBC().getAverageRating(NAMEGAME));
    }

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

    private void processInput() {
        try {
            handleInput(readLine());
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
            case "M" -> field.markTile(row, column);
            case "O" -> field.openTile(row, column);
            default -> processInput();
        }
    }
}
