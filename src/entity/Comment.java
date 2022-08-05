package entity;

import java.util.Date;

public class Comment {
    private String game;
    private String username;
    private String comment;
    private Date commented_on;

    public Comment(String game, String username, String comment, Date commented_on) {
        this.game = game;
        this.username = username;
        this.comment = comment;
        this.commented_on = commented_on;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommented_on() {
        return commented_on;
    }

    public void setCommented_on(Date commented_on) {
        this.commented_on = commented_on;
    }

    @Override
    public String toString() {
        return "\n{" +
                "game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", commented_on=" + commented_on;
    }
}
