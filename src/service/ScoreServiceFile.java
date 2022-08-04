package service;

import entity.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreServiceFile implements ScoreService {
    private List<Score> scores = new ArrayList<>();
    public static final String file = "score.bin";

    @Override
    public void addScore(Score score) {
        scores = load();
        scores.add(score);
        save(scores);
    }

    @Override
    public List<Score> getBestScores(String game) {
        scores = load();
        return scores.stream().filter(s -> s.getGame().equals(game))
                .sorted((s1,s2)-> Integer.compare(s1.getPoints(), s2.getPoints()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        scores = new ArrayList<>();
        save(scores);
    }

    private void save(List<Score> scores2save) {
        try (var fos = new FileOutputStream(file);
             var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(scores2save);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }

    public List<Score> load() {
        try (var fis = new FileInputStream(file);
             var ois = new ObjectInputStream(fis)) {
            scores = (List<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStudioException(e);
        }
        return scores;
    }
}
