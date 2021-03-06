package minesweeper;

import javax.tools.FileObject;
import java.io.*;
import java.util.Objects;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;

    public static final Settings BEGINNER = new Settings(9, 9, 10);
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
    public static final Settings EXPERT = new Settings(16, 30, 99);

    private static final String SETTING_FILE = System.getProperty("user.home") +
            System.getProperty("file.separator") + "minesweeper.settings";

    public Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return rowCount == settings.rowCount && columnCount == settings.columnCount && mineCount == settings.mineCount;
    }

    @Override
    public int hashCode() {
        return rowCount * columnCount * mineCount;
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(SETTING_FILE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();
    }

    public static Settings load() {
        try {
            FileInputStream fis = new FileInputStream(SETTING_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Settings) ois.readObject();
        } catch (Exception e) {
            return BEGINNER;
        }
        finally {

        }
    }
}
