package minesweeper;

import minesweeper.consoleui.ConsoleUI;

public class Minesweeper {
    public static long startMillis = System.currentTimeMillis();
    private static Minesweeper instance;

    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    private Minesweeper() {
        instance = this;
          final UserInterface userInterface = new ConsoleUI();
          userInterface.play();
    }

    public static void main(String[] args) {
        new Minesweeper();
    }

}

