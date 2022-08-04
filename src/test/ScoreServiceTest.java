package test;

import entity.Score;
import org.junit.jupiter.api.Test;
import service.ScoreService;
import service.ScoreServiceFile;
import service.ScoreServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreServiceTest {
//    private ScoreService ss = new ScoreServiceJDBC();
    private ScoreService ss = new ScoreServiceFile();

    @Test
    public void testReset() {
        ss.addScore(new Score("minesweeper", "Jano", 123, new Date()));
        ss.reset();
        assertEquals(0, ss.getBestScores("minesweeper").size());
    }

    @Test
    public void testAddScore() {
        ss.reset();
        var date = new Date();
        ss.addScore(new Score("minesweeper", "Jano", 123, date));
        var score = ss.getBestScores("minesweeper");
        assertEquals(1, score.size());
        assertEquals("minesweeper", score.get(0).getGame());
        assertEquals("Jano", score.get(0).getUsername());
        assertEquals(123, score.get(0).getPoints());
        assertEquals(date, score.get(0).getPlayedOn());
    }

    @Test
    public void testGetBestScores() {
        ss.reset();
        var date = new Date();
        ss.addScore(new Score("minesweeper", "Peto", 140, date));
        ss.addScore(new Score("minesweeper", "Katka", 150, date));
        ss.addScore(new Score("tiles", "Zuzka", 290, date));
        ss.addScore(new Score("minesweeper", "Jergus", 100, date));

        var scores = ss.getBestScores("minesweeper");

        assertEquals(3, scores.size());

        assertEquals("minesweeper", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getUsername());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("minesweeper", scores.get(1).getGame());
        assertEquals("Peto", scores.get(1).getUsername());
        assertEquals(140, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("minesweeper", scores.get(2).getGame());
        assertEquals("Jergus", scores.get(2).getUsername());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());


    }
}
