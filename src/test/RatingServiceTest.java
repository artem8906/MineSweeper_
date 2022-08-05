package test;

import entity.Rating;
import entity.Score;
import org.junit.Test;
import service.*;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingServiceTest {

    private RatingService rs = new RatingServiceJDBC();

    @Test
    public void returnNullifEmptyRatingTest() {
        rs.reset();
        assertEquals(0, rs.getAverageRating("minesweeper"));
    }

    @Test
    public void averageRatingTest() {
        rs.reset();
        var date = new Date();
        rs.setRating(new Rating("minesweeper", "Peto", 5, date));
        rs.setRating(new Rating("minesweeper", "Katka", 1, date));
        rs.setRating(new Rating("tiles", "Zuzka", 4, date));
        rs.setRating(new Rating("minesweeper", "Jergus", 3, date));

        assertEquals((3+5+1)/3,rs.getAverageRating("minesweeper"));

    }
    @Test
    public void updateRateFromSameUserAndGame() {
        rs.reset();
        var date = new Date();
        rs.setRating(new Rating("minesweeper", "Katka", 5, date));
        rs.setRating(new Rating("minesweeper", "Katka", 1, date));
        assertEquals(1, rs.getRating("minesweeper", "Katka"));
    }
    @Test
    public void rateIllegalValue() {
        rs.reset();
        try {
            rs.setRating(new Rating("minesweeper", "Katka", 7, new Date()));
            assertTrue(false);
        }
        catch (GameStudioException e) {
            assertTrue(true);
        }

    }
}
