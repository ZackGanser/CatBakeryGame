import javax.swing.*;
import java.awt.*;

public class FeedbackScreen extends BaseScreen {
    private JTextArea feedbackArea;

    public FeedbackScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("\uD83D\uDCF1 Customer Feedback", JLabel.CENTER); //Cell Phone emoji
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        feedbackArea = new JTextArea(10, 60);
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setFont(new Font("Serif", Font.PLAIN, 14));
        feedbackArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton nextDayButton = new JButton("Next Day →");
        nextDayButton.setFont(new Font("Serif", Font.BOLD, 16));
        nextDayButton.addActionListener(e -> game.nextDay());

        buttonPanel.add(nextDayButton);

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(feedbackArea), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void displayFeedback(String feedback) {
        feedbackArea.setText(feedback);
    }
}