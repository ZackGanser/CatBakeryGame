public class GameState {
    private int currentDay;
    private int score;
    private int health;
    private String playerName;

    public GameState(int currentDay, int score, int health, String playerName) {
        this.currentDay = currentDay;
        this.score = score;
        this.health = health;
        this.playerName = playerName;
    }

    public int getCurrentDay() { return currentDay; }
    public int getScore() { return score; }
    public int getHealth() { return health; }
    public String getPlayerName() { return playerName; }
}