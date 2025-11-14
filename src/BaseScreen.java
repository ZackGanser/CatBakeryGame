import javax.swing.*;
import java.awt.*;

public abstract class BaseScreen extends JPanel {
    protected CatBakeryGame game;
    protected JLabel dayLabel, scoreLabel, playerNameLabel;
    protected JProgressBar healthBar;

    public BaseScreen(CatBakeryGame game) {
        this.game = game;
    }

    protected JPanel createHeaderPanel() {
        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setBackground(new Color(139, 69, 19));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        playerNameLabel = new JLabel("Baker: ", JLabel.CENTER);
        dayLabel = new JLabel("Day: 1", JLabel.CENTER);
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100);
        healthBar.setStringPainted(true);

        styleHeaderLabel(playerNameLabel);
        styleHeaderLabel(dayLabel);
        styleHeaderLabel(scoreLabel);
        healthBar.setForeground(Color.GREEN);

        header.add(playerNameLabel);
        header.add(dayLabel);
        header.add(scoreLabel);
        header.add(healthBar);

        return header;
    }

    protected void styleHeaderLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif", Font.BOLD, 14));
    }

    protected void updateHealthBarColor(int health) {
        if (health > 70) {
            healthBar.setForeground(Color.GREEN);
        } else if (health > 30) {
            healthBar.setForeground(Color.ORANGE);
        } else {
            healthBar.setForeground(Color.RED);
        }
    }

    public void updateGameInfo(GameState gameState) {
        playerNameLabel.setText("Baker: " + gameState.getPlayerName());
        dayLabel.setText("Day: " + gameState.getCurrentDay());
        scoreLabel.setText("Score: " + gameState.getScore());
        healthBar.setValue(gameState.getHealth());
        updateHealthBarColor(gameState.getHealth());
    }
}