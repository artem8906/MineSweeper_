package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements minesweeper.UserInterface {
    /** Playing field. */
    private Field field;

    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads line of text from the reader.
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
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
//        do {
            update();
            //processInput();
            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
//        } while(true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        char a = 65;
        System.out.print("  ");

        for (int i = 0; i < field.getColumnCount(); i++) {
         System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < field.getRowCount(); i++) {
            if (a==91) a+=6;
            System.out.printf("%c ", a); a++;
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (j>9) System.out.print(" ");
                System.out.print(field.getTiles(i,j) + " ");
            }
            System.out.println();
        }



    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        throw new UnsupportedOperationException("Method processInput not yet implemented");
    }
}
