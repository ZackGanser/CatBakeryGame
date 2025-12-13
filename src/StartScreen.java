import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StartScreen extends BaseScreen {
    private JComboBox<String> nameComboBox;
    private JLabel highScoreLabel;
    private JLabel catImageLabel;
    private Map<String, String> nameToImageKey = new HashMap<>();

    //I based these names off the assets that Jessica made.
    private String[] catBakerNames = {
            "Pippi", "Sammi", "Sophie"
    };

    // Emoji fallbacks for each cat type
    private Map<String, String> emojiMap = new HashMap<>();

    public StartScreen(CatBakeryGame game) {
        super(game);
        setBackgroundImage(game.getStartBg());
        initializeMappings();
        createUI();
    }

    private void initializeMappings() {
        // Map names to image keys
        nameToImageKey.put("Pippi", "Pippi");
        nameToImageKey.put("Sammi", "Sammi");
        nameToImageKey.put("Sophie", "Sophie");

        // Initialize emoji fallbacks
        emojiMap.put("pippi", "\uD83D\uDE38");
        emojiMap.put("sammi", "\uD83D\uDC31");
        emojiMap.put("sophie", "\uD83D\uDE3B");
    }

    private void createUI() {
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel with transparency
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 255, 255, 1)); // Semi-transparent white
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Cat Image Display
        JPanel catDisplayPanel = new JPanel(new BorderLayout());
        catDisplayPanel.setOpaque(false);
        catDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Frame for cat image
        JPanel catFrame = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        catFrame.setOpaque(false);
        catFrame.setPreferredSize(new Dimension(200, 200));
        catFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        catImageLabel = new JLabel("", JLabel.CENTER);
        catImageLabel.setFont(new Font("Serif", Font.PLAIN, 80));
        updateCatImage((String) catBakerNames[0]); // Set initial image

        catFrame.add(catImageLabel, BorderLayout.CENTER);
        catDisplayPanel.add(catFrame, BorderLayout.CENTER);

        contentPanel.add(catDisplayPanel, gbc);

        // Name Selection
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Choose Your Baker Cat:", JLabel.CENTER);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        nameLabel.setForeground(new Color(139, 69, 19));

        nameComboBox = new JComboBox<>(catBakerNames);
        nameComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        nameComboBox.setBackground(Color.WHITE);

        // Add action listener to update cat image when selection changes
        nameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) nameComboBox.getSelectedItem();
                updateCatImage(selectedName);
            }
        });

        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameComboBox, BorderLayout.CENTER);
        contentPanel.add(namePanel, gbc);

        // High Score
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setOpaque(false);

        JLabel scoreTitleLabel = new JLabel("\uD83C\uDFC6 High Score", JLabel.CENTER);
        scoreTitleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        scoreTitleLabel.setForeground(new Color(139, 69, 19));
        scoreTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        highScoreLabel = new JLabel("0", JLabel.CENTER);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 48));
        highScoreLabel.setForeground(new Color(219, 112, 147));

        scorePanel.add(scoreTitleLabel, BorderLayout.NORTH);
        scorePanel.add(highScoreLabel, BorderLayout.CENTER);
        contentPanel.add(scorePanel, gbc);

        // Start Button with image
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton startButton = createImageButton(game.getButtonIcon(0), "Start Baking! 🎂");
        startButton.setBorderPainted(false);
        startButton.setOpaque(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) nameComboBox.getSelectedItem();
                game.setPlayerName(selectedName);
                game.startNewGame();
            }
        });

        buttonPanel.add(startButton);
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void updateCatImage(String selectedName) {
        if (selectedName == null) return;

        String imageKey = nameToImageKey.get(selectedName);

        if (imageKey != null && game.hasCatImages()) {
            // Try to load image
            ImageIcon catImage = game.getCatImage(imageKey);
            if (catImage != null) {
                // Scale image to fit
                Image scaledImage = catImage.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                catImageLabel.setIcon(new ImageIcon(scaledImage));
                catImageLabel.setText(""); // Clear text
                return;
            }
        }

        // Fallback to emoji
        String emoji = emojiMap.getOrDefault(imageKey != null ? imageKey : "default", "🐱");
        catImageLabel.setIcon(null); // Clear icon
        catImageLabel.setText(emoji);
        catImageLabel.setFont(new Font("Serif", Font.PLAIN, 100));
    }

    private JButton createImageButton(ImageIcon icon, String text) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setText(""); // Remove text if we have an image
            button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        } else {
            // Fallback styling
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(144, 238, 144));
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(34, 139, 34), 3),
                    BorderFactory.createEmptyBorder(15, 30, 15, 30)
            ));
        }
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
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