package minesweeper;

import minesweeper.core.Field;

import java.io.IOException;

public interface UserInterface {
    void newGameStarted(Field field) throws IOException;

    void update();
}
