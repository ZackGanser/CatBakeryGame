import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LetterIntroScreen extends BaseScreen {
    private Timer animationTimer;
    private float fadeIn = 0f;
    private JLabel titleLabel;
    private JLabel catLabel;
    private boolean animationComplete = false;

    public LetterIntroScreen(CatBakeryGame game) {
        super(game);
        // Start with closed mailbox background
        setBackgroundImage(game.getLetterIntroBg());
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());

        // Header (minimal or none for this screen)
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content with fade-in effect
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Apply fade-in overlay
                g.setColor(new Color(0, 0, 0, (int)(255 * (1 - fadeIn))));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 30, 0);

        // Animated title
        titleLabel = new JLabel("", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 253, 208));
        titleLabel.setOpaque(false);
        contentPanel.add(titleLabel, gbc);

        // Cat/mail animation
        catLabel = new JLabel("", JLabel.CENTER);
        catLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        catLabel.setForeground(new Color(255, 253, 208));
        catLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        contentPanel.add(catLabel, gbc);

        // Instruction text
        JLabel instructionLabel = new JLabel("You've received mail!", JLabel.CENTER);
        instructionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        instructionLabel.setForeground(new Color(255, 253, 208));
        instructionLabel.setOpaque(false);
        contentPanel.add(instructionLabel, gbc);

        // Next button with proper spacing - ALWAYS ENABLED
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton nextButton = createImageButton(game.getButtonIcon(1), "Open Letter →");
        nextButton.setEnabled(true); // ALWAYS ENABLED

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAnimation();
                game.showLetterScreen();
            }
        });

        buttonPanel.add(nextButton);

        // Add button panel to content with proper constraints
        gbc.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // Store button reference (not needed for enabling anymore but kept for consistency)
        contentPanel.putClientProperty("nextButton", nextButton);
    }

    public void startAnimation() {
        // Reset animation state for new day
        resetAnimation();

        animationTimer = new Timer(50, new ActionListener() {
            private int frame = 0;
            private String[] mailAnimation = {"📫", "📬", "📭", "📨", "\uD83D\uDCEA", "📯"};
            private String[] catAnimation = {"\uD83D\uDC31", "\uD83D\uDE3A", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3B", "\uD83D\uDE38"};

            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;

                // Fade in effect
                fadeIn = Math.min(1.0f, fadeIn + 0.05f);

                // Update title with typing effect
                String fullText = "Mail Delivery!";
                int charsToShow = Math.min(frame / 3, fullText.length());
                titleLabel.setText(fullText.substring(0, charsToShow));

                // Animate cat and mail
                if (frame < 30) {
                    // Mail delivery animation
                    int mailFrame = (frame / 5) % mailAnimation.length;
                    catLabel.setText(mailAnimation[mailFrame]);
                } else if (frame < 60) {
                    // Cat receiving mail animation
                    int catFrame = ((frame - 30) / 5) % catAnimation.length;
                    catLabel.setText(catAnimation[catFrame]);
                } else {
                    // Final state - cat with letter
                    catLabel.setText("🐱📜");
                    // Switch to open mailbox background
                    setBackgroundImage(game.getLetterIntroBgOpen());
                    repaint();

                    // Animation is complete
                    animationComplete = true;
                }

                repaint();
            }
        });
        animationTimer.start();
    }

    public void resetAnimation() {
        // Stop any existing animation
        stopAnimation();

        // Reset animation state
        fadeIn = 0f;
        animationComplete = false;

        // Reset background to closed mailbox
        setBackgroundImage(game.getLetterIntroBg());

        // Reset UI elements
        if (titleLabel != null) {
            titleLabel.setText("");
        }

        if (catLabel != null) {
            catLabel.setText("");
        }

        repaint();
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
            animationTimer = null;
        }
    }

    private JButton createImageButton(ImageIcon icon, String text) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setText("");
            button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            // Ensure button has proper margins
            button.setMargin(new Insets(5, 10, 5, 10));
        } else {
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(144, 238, 144, 200));
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(34, 139, 34), 3),
                    BorderFactory.createEmptyBorder(15, 30, 15, 30)
            ));
            button.setMargin(new Insets(5, 15, 5, 15));
        }
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        return button;
    }

    @Override
    public void updateGameInfo(GameState gameState) {
        super.updateGameInfo(gameState);
        // Update any game info if needed
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            // Only start animation if this is a new day (not when returning to screen)
            // The game should call startAnimation() explicitly when starting a new day
        }
    }

    public boolean isAnimationComplete() {
        return animationComplete;
    }
}