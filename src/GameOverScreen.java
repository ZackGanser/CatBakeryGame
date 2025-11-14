import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends BaseScreen {
    private JLabel resultLabel;

    public GameOverScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());
        setBackground(new Color(47, 79, 79));

        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));
        contentPanel.setBackground(new Color(47, 79, 79));

        JLabel titleLabel = new JLabel("Game Over", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);

        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        resultLabel.setForeground(Color.WHITE);

        JLabel messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        messageLabel.setForeground(Color.LIGHT_GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton restartButton = new JButton("Play Again");
        restartButton.setFont(new Font("Serif", Font.BOLD, 16));
        restartButton.addActionListener(e -> game.showStartScreen());

        buttonPanel.add(restartButton);

        contentPanel.add(titleLabel);
        contentPanel.add(resultLabel);
        contentPanel.add(messageLabel);
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void displayGameOver(int days, int finalScore, int highScore, String playerName) {
        resultLabel.setText(String.format(
                "<html>Days Survived: %d<br>Final Score: %d<br>High Score: %d</html>",
                days, finalScore, highScore
        ));

        // Update the message with the player's name
        Component[] components = ((JPanel)getComponent(0)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getFont().getStyle() == Font.ITALIC) {
                    label.setText("Thank you for playing, " + playerName + "!");
                    break;
                }
            }
        }
    }

    @Override
    public void updateGameInfo(GameState gameState) {
        //Game over screen doesn't need to update regular game info, but we need it for the Override
    }
}