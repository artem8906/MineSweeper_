package entity;

import java.util.Date;

public class Rating {

    private String game;
    private String username;
    private int rate;
    private Date ratedOn;

    public Rating(String game, String username, int rate, Date ratedOn) {
        this.game = game;
        this.username = username;
        this.rate = rate;
        this.ratedOn = ratedOn;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return Integer.toString(rate);
    }
}
