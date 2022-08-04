package test;

import entity.Comment;
import entity.Score;
import org.junit.jupiter.api.Test;
import service.CommentService;
import service.CommentServiceJDBC;
import service.ScoreService;
import service.ScoreServiceFile;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentServiceTest {

    private CommentService cs = new CommentServiceJDBC();

    @Test
    public void testReset() {
        cs.addComment(new Comment("minesweeper", "Jano", "fgfg", new Date()));
        cs.reset();
        assertEquals(0, cs.getComments("minesweeper").size());
    }

    @Test
    public void testAddScore() {
        cs.reset();
        var date = new Date();
        cs.addComment(new Comment("minesweeper", "Jano", "dfgdfg", date));
        var comments = cs.getComments("minesweeper");
        assertEquals(1, comments.size());
        assertEquals("minesweeper", comments.get(0).getGame());
        assertEquals("Jano", comments.get(0).getUsername());
        assertEquals("dfgdfg", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommented_on());
    }

    @Test
    public void testGetLastComments() {
        cs.reset();
        var date = new Date();
        cs.addComment(new Comment("minesweeper", "Peto", "45345", new Date()));//2
        cs.addComment(new Comment("minesweeper", "Katka", "150", new Date()));//1
        cs.addComment(new Comment("tiles", "Zuzka", "290", new Date()));
        cs.addComment(new Comment("minesweeper", "Jergus", "dfsjkd", new Date()));//0

        var comments = cs.getComments("minesweeper");

        assertEquals(3, comments.size());

        assertEquals("minesweeper", comments.get(0).getGame());
        assertEquals("Jergus", comments.get(0).getUsername());
        assertEquals("dfsjkd", comments.get(0).getComment());
        assertEquals(1, comments.get(0).getCommented_on().compareTo(comments.get(1).getCommented_on()));

        assertEquals("minesweeper", comments.get(1).getGame());
        assertEquals("Katka", comments.get(1).getUsername());
        assertEquals("150", comments.get(1).getComment());
        assertEquals(1, comments.get(1).getCommented_on().compareTo(comments.get(2).getCommented_on()));

        assertEquals("minesweeper", comments.get(2).getGame());
        assertEquals("Peto", comments.get(2).getUsername());
        assertEquals("45345", comments.get(2).getComment());
        assertEquals(-1, comments.get(2).getCommented_on().compareTo(comments.get(0).getCommented_on()));
    }

}