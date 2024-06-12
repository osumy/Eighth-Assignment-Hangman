package hangman;

import java.util.UUID;

public class Game {
    private UUID gameId;
    private String username;
    private String word;
    private int wrongGuesses;
    private int time;
    private boolean win;

    public Game(String username, String word, int wrongGuesses, int time, boolean win) {
        this.gameId = UUID.randomUUID();
        this.username = username;
        this.word = word;
        this.wrongGuesses = wrongGuesses;
        this.time = time;
        this.win = win;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public void setWrongGuesses(int wrongGuesses) {
        this.wrongGuesses = wrongGuesses;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}