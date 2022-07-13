package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private ConsoleUI userInterface;

    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();

        Field field = new Field(9, 9, 10);

        Tile[][] tiles = field.getTiles();

        for (Tile[] t : tiles) {
            for (Tile a : t) {
                System.out.print(a + " ");
                System.out.println();
            }
        }

       userInterface.newGameStarted(field);

        }

        /**
         * Main method.
         * @param args arguments
         */
        public static void main (String[]args){

            new Minesweeper();

        }
    }

