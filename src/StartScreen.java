import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private CatBakeryGame game;
    private JComboBox<String> nameComboBox;
    private JLabel highScoreLabel;
    private JLabel catImageLabel;

    //I based these names off the assets that Jessica made.
    private String[] catBakerNames = {
            "Pippi", "Sammi", "Sophie"
    };

    public StartScreen(CatBakeryGame game) {
        this.game = game;
        setLayout(new BorderLayout());
        setBackground(new Color(255, 223, 186)); // Light orange background

        createUI();
    }

    private void createUI() {
        //Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(139, 69, 19));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JLabel titleLabel = new JLabel("\uD83D\uDC31 The Honey Bun Cakery \uD83C\uDF70", JLabel.CENTER); //Cat and Cake emojis
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Choose your baker cat and start baking!", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(255, 253, 208));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        //Center Content Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        centerPanel.setBackground(new Color(255, 223, 186));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 20, 0);

        //Cat Image Placeholder (Will need refactored in some way to use the image assets)
        catImageLabel = new JLabel("\uD83C\uDF70", JLabel.CENTER); //Cat emoji
        catImageLabel.setFont(new Font("Serif", Font.PLAIN, 80));
        catImageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        centerPanel.add(catImageLabel, gbc);

        //Player Name Selection
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(new Color(255, 223, 186));

        JLabel nameLabel = new JLabel("Choose Your Baker Cat:");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setForeground(new Color(139, 69, 19));

        nameComboBox = new JComboBox<>(catBakerNames);
        nameComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        nameComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                }
                return c;
            }
        });

        // Add action listener to update the cat emoji based on selection
        nameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCatEmoji();
            }
        });

        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameComboBox, BorderLayout.CENTER);
        centerPanel.add(namePanel, gbc);

        //High Score Display
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setBackground(new Color(255, 223, 186));

        JLabel scoreTitleLabel = new JLabel("\uD83C\uDFC6 High Score", JLabel.CENTER); //Trophy emoji
        scoreTitleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        scoreTitleLabel.setForeground(new Color(139, 69, 19));

        highScoreLabel = new JLabel("0", JLabel.CENTER);
        highScoreLabel.setFont(new Font("Serif", Font.BOLD, 48));
        highScoreLabel.setForeground(new Color(219, 112, 147)); //Pink

        scorePanel.add(scoreTitleLabel, BorderLayout.NORTH);
        scorePanel.add(highScoreLabel, BorderLayout.CENTER);
        centerPanel.add(scorePanel, gbc);

        //Start Button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 223, 186));

        JButton startButton = new JButton("Start Baking! \uD83C\uDF70"); //Cake emoji
        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.setBackground(new Color(144, 238, 144)); //Light green
        startButton.setForeground(Color.BLACK);
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 3),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        startButton.setFocusPainted(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) nameComboBox.getSelectedItem();
                game.setPlayerName(selectedName);
                game.startNewGame();
            }
        });

        buttonPanel.add(startButton);
        gbc.insets = new Insets(20, 0, 0, 0);
        centerPanel.add(buttonPanel, gbc);

        //Instructions Panel
        JPanel instructionPanel = new JPanel();
        instructionPanel.setBackground(new Color(255, 248, 220));
        instructionPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextArea instructions = new JTextArea(
                "How to play Cozy Bakery Simulator:\n" +
                        "• Read letters from customers with their orders\n" +
                        "• Choose the right cake base, frosting, and topping for the order\n" +
                        "• Select the correct delivery method\n" +
                        "• Earn points for correct choices and lose points for incorrect choices\n" +
                        "• Don't let your health reach zero!"
        );
        instructions.setEditable(false);
        instructions.setFont(new Font("Serif", Font.PLAIN, 14));
        instructions.setBackground(new Color(255, 248, 220));
        instructions.setLineWrap(false);
        instructions.setWrapStyleWord(true);
        instructions.setAlignmentX(CENTER_ALIGNMENT);

        instructionPanel.add(instructions);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(instructionPanel, BorderLayout.SOUTH);

        //Set initial cat emoji
        updateCatEmoji();
    }

    //Placeholder until we refactor to use the image assets for the cats
    private void updateCatEmoji() {
        String selectedName = (String) nameComboBox.getSelectedItem();
        String emoji = "\uD83D\uDC31"; //Default Cat emoji

        if (selectedName != null) {
            if (selectedName.contains("Pippi")) {
                emoji = "\uD83D\uDE38"; //Grinning Cat with Smiling Eyes
            } else if (selectedName.contains("Sammi")) {
                emoji = "\uD83D\uDC31"; //Grinning Cat
            } else if (selectedName.contains("Sophie")) {
                emoji = "\uD83D\uDE3B"; //Heart Eyes Cat
            }
        }

        catImageLabel.setText(emoji);
    }

    public void updateHighScore(int highScore) {
        highScoreLabel.setText(String.valueOf(highScore));
        //Keep the current selection or reset to first name
        if (game.getPlayerName() != null && !game.getPlayerName().isEmpty()) {
            for (int i = 0; i < catBakerNames.length; i++) {
                if (catBakerNames[i].equals(game.getPlayerName())) {
                    nameComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}