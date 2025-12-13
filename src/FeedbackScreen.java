import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FeedbackScreen extends BaseScreen {
    private JTextArea feedbackArea;
    private JLabel customerImageLabel;
    private ImageIcon[] customerImages = new ImageIcon[3];
    private ImageIcon backgroundImageIcon;

    public FeedbackScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());

        // Load background image
        backgroundImageIcon = game.getFeedbackBg();
        if (backgroundImageIcon != null) {
            setBackgroundImage(backgroundImageIcon);
        } else {
            // Fallback color if no image
            setBackground(new Color(240, 248, 255));
        }

        loadCustomerImages();
        createUI();
    }

    private void loadCustomerImages() {
        // Try to load customer images - dog, fox, rabbit
        customerImages[0] = game.loadImage("customers/dog.png", "customers/dog.jpg");
        customerImages[1] = game.loadImage("customers/fox.png", "customers/fox.jpg");
        customerImages[2] = game.loadImage("customers/rabbit.png", "customers/rabbit.jpg");

        // If no customer images found, use emoji fallbacks
        if (customerImages[0] == null && customerImages[1] == null && customerImages[2] == null) {
            System.out.println("No customer images found, using emoji fallbacks");
        }
    }

    private ImageIcon getRandomCustomerImage() {
        // Randomly select a customer image or emoji
        int index = (int)(Math.random() * 3);

        if (customerImages[index] != null) {
            return customerImages[index];
        } else {
            // Fallback emojis
            String[] emojis = {"🐶", "🦊", "🐰"};
            // Create a simple icon with emoji
            return createEmojiIcon(emojis[index]);
        }
    }

    private ImageIcon createEmojiIcon(String emoji) {
        // Create a simple image with the emoji
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        label.setSize(120, 120);

        BufferedImage image = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        label.paint(g2d);
        g2d.dispose();

        return new ImageIcon(image);
    }

    private void createUI() {
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title panel
        JPanel titlePanel = createTitlePanel();
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Center panel with customer and feedback
        JPanel centerPanel = createCenterPanel();
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with next button
        JPanel bottomPanel = createBottomPanel();
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Left decorative icon
        JLabel leftIcon = new JLabel("💬");
        leftIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        leftIcon.setHorizontalAlignment(JLabel.CENTER);

        // Title
        JLabel titleLabel = new JLabel("Customer Feedback", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        // Add text shadow for better visibility
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 0, 0),
                BorderFactory.createEmptyBorder(0, 0, 2, 2)
        ));

        // Right decorative icon
        JLabel rightIcon = new JLabel("⭐");
        rightIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        rightIcon.setHorizontalAlignment(JLabel.CENTER);

        panel.add(leftIcon, BorderLayout.WEST);
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(rightIcon, BorderLayout.EAST);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);

        // Left: Customer image
        JPanel customerPanel = createCustomerPanel();
        panel.add(customerPanel, BorderLayout.WEST);

        // Right: Feedback area
        JPanel feedbackPanel = createFeedbackPanel();
        panel.add(feedbackPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Customer image frame
        JPanel framePanel = new JPanel(new BorderLayout()) {};
        framePanel.setOpaque(false);
        framePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Customer image label
        customerImageLabel = new JLabel();
        updateCustomerImage();
        customerImageLabel.setHorizontalAlignment(JLabel.CENTER);
        customerImageLabel.setVerticalAlignment(JLabel.CENTER);

        framePanel.add(customerImageLabel, BorderLayout.CENTER);

        JPanel customerContainer = new JPanel(new BorderLayout());
        customerContainer.setOpaque(false);
        customerContainer.add(framePanel, BorderLayout.CENTER);

        panel.add(customerContainer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Feedback area with decorative background
        JPanel feedbackContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw parchment-like background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 253, 208, 200),
                        getWidth(), getHeight(), new Color(245, 235, 185, 200)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Add paper texture
                g2d.setColor(new Color(139, 69, 19, 10));
                for (int i = 0; i < 50; i++) {
                    int x = (int)(Math.random() * getWidth());
                    int y = (int)(Math.random() * getHeight());
                    int size = (int)(Math.random() * 2) + 1;
                    g2d.fillOval(x, y, size, size);
                }

                // Border
                g2d.setColor(new Color(139, 69, 19, 150));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                g2d.dispose();
            }
        };
        feedbackContainer.setOpaque(false);
        feedbackContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Feedback text area
        feedbackArea = new JTextArea(8, 40);
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setFont(new Font("Serif", Font.PLAIN, 16));
        feedbackArea.setBackground(new Color(255, 253, 208, 0));
        feedbackArea.setForeground(new Color(60, 30, 10));
        feedbackArea.setMargin(new Insets(10, 10, 10, 10));
        feedbackArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // If font not available, use fallback
        if (!feedbackArea.getFont().getFamily().equals("Serif")) {
            feedbackArea.setFont(new Font("Georgia", Font.PLAIN, 14));
        }

        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Thought bubble decoration at top
        JLabel thoughtBubble = new JLabel("💭 What they thought...", JLabel.CENTER);
        thoughtBubble.setFont(new Font("Serif", Font.ITALIC, 14));
        thoughtBubble.setForeground(new Color(139, 69, 19));
        thoughtBubble.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        feedbackContainer.add(thoughtBubble, BorderLayout.NORTH);
        feedbackContainer.add(scrollPane, BorderLayout.CENTER);

        panel.add(feedbackContainer, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Next Day button with image
        JButton nextDayButton = createImageButton(game.getButtonIcon(4), "Next Day →");
        nextDayButton.addActionListener(e -> game.nextDay());

        // Add hover effect for button
        nextDayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextDayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextDayButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        panel.add(nextDayButton);

        return panel;
    }

    private JButton createImageButton(ImageIcon icon, String text) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setText("");
            button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        } else {
            // Fallback styled button
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(144, 238, 144));
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(34, 139, 34), 3),
                    BorderFactory.createEmptyBorder(12, 25, 12, 25)
            ));
            button.setFocusPainted(false);
        }

        button.setContentAreaFilled(icon == null);
        button.setOpaque(icon == null);
        button.setBorderPainted(icon == null);

        return button;
    }

    private void updateCustomerImage() {
        if (customerImageLabel != null) {
            ImageIcon customerIcon = getRandomCustomerImage();
            if (customerIcon != null) {
                // Scale image if needed
                Image scaledImage = customerIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                customerImageLabel.setIcon(new ImageIcon(scaledImage));
                customerImageLabel.setText("");
            } else {
                // Fallback emoji
                customerImageLabel.setIcon(null);
                customerImageLabel.setText("🐶");
                customerImageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
            }
        }
    }

    public void displayFeedback(String feedback) {
        // Update customer image with random customer
        updateCustomerImage();

        // Display feedback text immediately (no animation)
        feedbackArea.setText(feedback);
        feedbackArea.setCaretPosition(0);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            // Update customer image when screen becomes visible
            updateCustomerImage();
        }
    }
}