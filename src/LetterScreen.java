import javax.swing.*;
import java.awt.*;

public class LetterScreen extends BaseScreen {
    private JTextArea letterArea;

    public LetterScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 253, 208));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("\uD83D\uDCEC Today's Order Letter", JLabel.CENTER); //Mailbox emoji
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        letterArea = new JTextArea(10, 60);
        letterArea.setEditable(false);
        letterArea.setLineWrap(true);
        letterArea.setWrapStyleWord(true);
        letterArea.setFont(new Font("Serif", Font.PLAIN, 16));
        letterArea.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));

        JButton nextButton = new JButton("Start Baking →");
        nextButton.setFont(new Font("Serif", Font.BOLD, 16));
        nextButton.addActionListener(e -> game.showScreen("BAKING"));

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(letterArea), BorderLayout.CENTER);
        contentPanel.add(nextButton, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void displayLetter(Order order) {
        letterArea.setText(order.getLetter());
    }
}