import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class LetterScreen extends BaseScreen {
    private JTextArea letterArea;
    private JButton nextButton;
    private JPanel letterPanel;
    private JPanel letterContentPanel;
    private Random random = new Random();
    private ImageIcon pawSealIcon;

    // Customer names for signatures
    private String[] customerNames = {
            "Miss Purrington", "Mr. Whiskerton", "Lady Fluffbottom",
            "Sir Meowington", "Professor Snugglepaws", "Duchess Biscuit",
            "Captain Cuddles", "Baron Von Purr", "Madame Mewmew",
            "Countess Catnip", "Dr. Pawsley", "Ms. Furrytail",
            "Lord Mittens", "Princess Whiskers", "Mr. Fluffykins",
            "Lady Pawsington", "Sir Reginald Purr", "Miss Kitty McFluff"
    };

    private String[] signatureStyles = {
            "Elegant cursive", "Sloppy scrawl", "Neat print",
            "Fancy signature", "Quick scribble", "Artistic flourish",
            "Bold strokes", "Delicate script", "Hurried note"
    };

    private String currentCustomer = "";
    private String currentSignatureStyle = "";

    public LetterScreen(CatBakeryGame game) {
        super(game);
        setBackgroundImage(game.getLetterBg());
        setLayout(new BorderLayout());

        // Load paw seal image from assets
        loadPawSealImage();

        // Initialize with placeholder values
        currentCustomer = "Customer";
        currentSignatureStyle = "Signature";

        createUI();
        initializeLetterContent();
    }

    private void loadPawSealImage() {
        try {
            // Try to load paw seal image
            pawSealIcon = game.loadImage("pawSeal.png", "pawSeal.jpg");
            if (pawSealIcon == null) {
                // Try alternative names
                pawSealIcon = game.loadImage("seal_paw.png", "seal_paw.jpg");
                pawSealIcon = game.loadImage("wax_seal.png", "wax_seal.jpg");
                pawSealIcon = game.loadImage("pawprint_seal.png", "pawprint_seal.jpg");
            }
        } catch (Exception e) {
            System.err.println("Could not load paw seal image: " + e.getMessage());
        }
    }

    // New method to regenerate customer for each day
    public void regenerateCustomer() {
        currentCustomer = customerNames[random.nextInt(customerNames.length)];
        currentSignatureStyle = signatureStyles[random.nextInt(signatureStyles.length)];

        // Update the letter content if it exists
        if (letterPanel != null) {
            letterPanel.repaint();
        }
    }

    private void createUI() {
        // Remove all components first
        removeAll();

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title with decorative elements
        JPanel titlePanel = createTitlePanel();
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Stylized letter panel
        letterPanel = createStylizedLetterPanel();
        contentPanel.add(letterPanel, BorderLayout.CENTER);

        // Next button with image asset
        JPanel buttonPanel = createButtonPanel();
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void initializeLetterContent() {
        // Create letter content panel and store it
        letterContentPanel = createLetterContentPanel();
        letterPanel.setLayout(new BorderLayout());
        letterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        letterPanel.add(letterContentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Decorative left icon
        JLabel leftIcon = new JLabel("🐾");
        leftIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        leftIcon.setHorizontalAlignment(JLabel.CENTER);

        // Main title
        JLabel titleLabel = new JLabel("Customer Order", JLabel.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 32));
        titleLabel.setForeground(new Color(139, 69, 19));

        // Customer name preview
        JLabel customerPreview = new JLabel("For: " + currentCustomer, JLabel.CENTER);
        customerPreview.setFont(new Font("Georgia", Font.ITALIC, 14));
        customerPreview.setForeground(new Color(139, 69, 19, 200));

        JPanel titleWithCustomer = new JPanel(new BorderLayout());
        titleWithCustomer.setOpaque(false);
        titleWithCustomer.add(titleLabel, BorderLayout.CENTER);
        titleWithCustomer.add(customerPreview, BorderLayout.SOUTH);

        // Decorative right icon
        JLabel rightIcon = new JLabel("📜");
        rightIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        rightIcon.setHorizontalAlignment(JLabel.CENTER);

        panel.add(leftIcon, BorderLayout.WEST);
        panel.add(titleWithCustomer, BorderLayout.CENTER);
        panel.add(rightIcon, BorderLayout.EAST);

        return panel;
    }

    private JPanel createStylizedLetterPanel() {
        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Enable anti-aliasing for smoother edges
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Calculate dimensions
                int width = getWidth();
                int height = getHeight();

                // Create aged parchment background with gradient
                GradientPaint parchmentGradient = new GradientPaint(
                        0, 0, new Color(255, 253, 208),
                        width, height, new Color(245, 235, 185)
                );
                g2d.setPaint(parchmentGradient);
                g2d.fillRect(0, 0, width, height);

                // Add paper texture (subtle noise)
                g2d.setColor(new Color(139, 69, 19, 15));
                for (int i = 0; i < 200; i++) {
                    int x = (int) (Math.random() * width);
                    int y = (int) (Math.random() * height);
                    int size = (int) (Math.random() * 3) + 1;
                    g2d.fillOval(x, y, size, size);
                }

                // Add aged paper edges (darker around the edges)
                g2d.setColor(new Color(139, 69, 19, 40));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(2, 2, width - 5, height - 5);

                // Add decorative corner elements
                g2d.setColor(new Color(139, 69, 19, 60));
                g2d.setStroke(new BasicStroke(2));

                // Top-left corner decoration
                drawCornerDecoration(g2d, 10, 10, true, true);
                // Top-right corner decoration
                drawCornerDecoration(g2d, width - 10, 10, false, true);
                // Bottom-left corner decoration
                drawCornerDecoration(g2d, 10, height - 10, true, false);
                // Bottom-right corner decoration
                drawCornerDecoration(g2d, width - 10, height - 10, false, false);

                // Add a subtle watermark/seal
                g2d.setColor(new Color(139, 69, 19, 15));
                g2d.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 48));
                String watermark = currentCustomer.toUpperCase();
                FontMetrics fm = g2d.getFontMetrics();
                int watermarkWidth = fm.stringWidth(watermark);
                g2d.drawString(watermark, (width - watermarkWidth) / 2, height / 2);

                // Add fold lines
                g2d.setColor(new Color(139, 69, 19, 30));
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[]{5, 5}, 0));
                g2d.drawLine(width / 3, 5, width / 3, height - 5);
                g2d.drawLine(2 * width / 3, 5, 2 * width / 3, height - 5);

                g2d.dispose();
            }

            private void drawCornerDecoration(Graphics2D g2d, int x, int y, boolean left, boolean top) {
                int size = 15;
                GeneralPath path = new GeneralPath();

                if (left && top) { // Top-left
                    path.moveTo(x, y);
                    path.lineTo(x + size, y);
                    path.moveTo(x, y);
                    path.lineTo(x, y + size);
                } else if (!left && top) { // Top-right
                    path.moveTo(x, y);
                    path.lineTo(x - size, y);
                    path.moveTo(x, y);
                    path.lineTo(x, y + size);
                } else if (left && !top) { // Bottom-left
                    path.moveTo(x, y);
                    path.lineTo(x + size, y);
                    path.moveTo(x, y);
                    path.lineTo(x, y - size);
                } else { // Bottom-right
                    path.moveTo(x, y);
                    path.lineTo(x - size, y);
                    path.moveTo(x, y);
                    path.lineTo(x, y - size);
                }

                g2d.draw(path);
            }
        };
    }

    private JPanel createLetterContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Add paw print wax seal decoration - SIMPLIFIED
        JPanel sealPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sealPanel.setOpaque(false);

        // Create simple image-only seal
        JLabel waxSeal = createSimpleImageSeal();
        waxSeal.setPreferredSize(new Dimension(80, 80));

        JLabel sealLabel = new JLabel("Official Seal");
        sealLabel.setFont(new Font("Georgia", Font.ITALIC, 12));
        sealLabel.setForeground(new Color(139, 69, 19));

        sealPanel.add(waxSeal);
        sealPanel.add(sealLabel);

        // Letter text area with handwriting-style font
        letterArea = new JTextArea(10, 50);
        letterArea.setEditable(false);
        letterArea.setLineWrap(true);
        letterArea.setWrapStyleWord(true);

        try {
            letterArea.setFont(new Font("Monotype Corsiva", Font.PLAIN, 18));
        } catch (Exception e) {
            letterArea.setFont(new Font("Serif", Font.PLAIN, 16));
        }

        letterArea.setBackground(new Color(255, 253, 208, 0));
        letterArea.setForeground(new Color(60, 30, 10));
        letterArea.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        letterArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(letterArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19, 50), 1));

        // Create signature panel with actual signature
        JPanel signaturePanel = createSignaturePanel();

        // Add stamp/mail markings
        JPanel stampPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stampPanel.setOpaque(false);

        JLabel postageLabel = new JLabel("₪ PAWSTAGE PAID ₪") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw postage stamp border with paw prints
                g2d.setColor(new Color(30, 80, 180));
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[]{2, 2}, 0));
                g2d.drawRect(2, 2, getWidth() - 5, getHeight() - 5);

                // Draw tiny paw prints in corners
                g2d.setColor(new Color(30, 80, 180, 150));
                drawTinyPawPrint(g2d, 8, 8, 4);
                drawTinyPawPrint(g2d, getWidth() - 12, 8, 4);
                drawTinyPawPrint(g2d, 8, getHeight() - 12, 4);
                drawTinyPawPrint(g2d, getWidth() - 12, getHeight() - 12, 4);

                g2d.dispose();
                super.paintComponent(g);
            }

            private void drawTinyPawPrint(Graphics2D g2d, int x, int y, int size) {
                // Miniature paw print
                g2d.fillOval(x - size/2, y - size/2, size, size); // Central pad
                g2d.fillOval(x - size, y - size, size/2, size/2); // Top-left
                g2d.fillOval(x + size/2, y - size, size/2, size/2); // Top-right
                g2d.fillOval(x - size, y + size/2, size/2, size/2); // Bottom-left
                g2d.fillOval(x + size/2, y + size/2, size/2, size/2); // Bottom-right
            }
        };
        postageLabel.setFont(new Font("Monospace", Font.BOLD, 10));
        postageLabel.setForeground(new Color(30, 80, 180));
        postageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        stampPanel.add(postageLabel);

        // Assemble all components
        JPanel letterContainer = new JPanel(new BorderLayout());
        letterContainer.setOpaque(false);

        letterContainer.add(sealPanel, BorderLayout.NORTH);
        letterContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(stampPanel, BorderLayout.WEST);
        bottomPanel.add(signaturePanel, BorderLayout.EAST);

        letterContainer.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(letterContainer, BorderLayout.CENTER);

        return contentPanel;
    }

    private JLabel createSimpleImageSeal() {
        if (pawSealIcon != null) {
            // SIMPLE: Just use the image as-is
            return new JLabel(pawSealIcon) {
                @Override
                public Dimension getPreferredSize() {
                    // Scale image to reasonable size
                    if (pawSealIcon != null) {
                        int width = pawSealIcon.getIconWidth();
                        int height = pawSealIcon.getIconHeight();
                        // Scale down if too large
                        if (width > 80 || height > 80) {
                            float scale = Math.min(80f / width, 80f / height);
                            width = (int)(width * scale);
                            height = (int)(height * scale);
                        }
                        return new Dimension(width, height);
                    }
                    return new Dimension(80, 80);
                }
            };
        } else {
            // Fallback: Simple emoji if no image
            return new JLabel("🐾") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Just draw the emoji
                    g2d.setFont(new Font("Serif", Font.PLAIN, 60));
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth("🐾")) / 2;
                    int y = (getHeight() + fm.getAscent()) / 2 - 10;
                    g2d.drawString("🐾", x, y);

                    g2d.dispose();
                }
            };
        }
    }

    private JPanel createSignaturePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Create a panel for the actual signature
        JPanel signatureContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw signature line
                g2d.setColor(new Color(139, 69, 19));
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int lineY = getHeight() - 25;
                g2d.drawLine(10, lineY, getWidth() - 10, lineY);

                // Draw the actual signature based on style
                drawSignature(g2d, currentCustomer, currentSignatureStyle);

                g2d.dispose();
            }

            private void drawSignature(Graphics2D g2d, String name, String style) {
                // Choose ink color based on signature style
                Color inkColor;
                switch(style) {
                    case "Elegant cursive":
                        inkColor = new Color(75, 0, 130); // Indigo
                        g2d.setFont(new Font("Lucida Handwriting", Font.ITALIC, 22));
                        break;
                    case "Sloppy scrawl":
                        inkColor = new Color(139, 0, 0); // Dark red
                        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
                        break;
                    case "Neat print":
                        inkColor = new Color(0, 100, 0); // Dark green
                        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
                        break;
                    case "Fancy signature":
                        inkColor = new Color(153, 50, 204); // Dark orchid
                        g2d.setFont(new Font("Monotype Corsiva", Font.BOLD, 24));
                        break;
                    case "Quick scribble":
                        inkColor = new Color(160, 82, 45); // Sienna
                        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
                        break;
                    case "Artistic flourish":
                        inkColor = new Color(186, 85, 211); // Medium orchid
                        g2d.setFont(new Font("Edwardian Script ITC", Font.BOLD, 26));
                        break;
                    case "Bold strokes":
                        inkColor = Color.BLACK;
                        g2d.setFont(new Font("Impact", Font.PLAIN, 20));
                        break;
                    case "Delicate script":
                        inkColor = new Color(72, 61, 139); // Dark slate blue
                        g2d.setFont(new Font("French Script MT", Font.PLAIN, 24));
                        break;
                    case "Hurried note":
                        inkColor = new Color(105, 105, 105); // Dim gray
                        g2d.setFont(new Font("Courier New", Font.ITALIC, 16));
                        break;
                    default:
                        inkColor = new Color(60, 30, 10);
                        g2d.setFont(new Font("Serif", Font.PLAIN, 18));
                }

                g2d.setColor(inkColor);

                // Draw the signature with appropriate style
                int x = 15;
                int y = getHeight() - 40;

                if (style.equals("Sloppy scrawl")) {
                    // Make it look sloppy by offsetting letters slightly
                    for (char c : name.toCharArray()) {
                        g2d.drawString(String.valueOf(c), x, y + (int)(Math.random() * 6 - 3));
                        x += g2d.getFontMetrics().charWidth(c) + 1;
                    }
                } else {
                    g2d.drawString(name, x, y);

                    // Add style-specific decorations
                    if (style.equals("Fancy signature")) {
                        // Add a flourish
                        g2d.setStroke(new BasicStroke(1.5f));
                        g2d.drawArc(getWidth() - 30, y - 10, 20, 10, 0, 180);
                    } else if (style.equals("Artistic flourish")) {
                        // Add decorative dots
                        for (int i = 0; i < 3; i++) {
                            g2d.fillOval(5 + i * 8, y - 5, 3, 3);
                        }
                    }
                }

                // Add signature style label
                g2d.setColor(new Color(139, 69, 19, 150));
                g2d.setFont(new Font("Georgia", Font.ITALIC, 10));
                g2d.drawString(style.toLowerCase(), 15, getHeight() - 15);
            }
        };
        signatureContainer.setPreferredSize(new Dimension(250, 60));

        panel.add(signatureContainer, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

        ImageIcon buttonIcon = game.getButtonIcon(1);

        if (buttonIcon != null) {
            nextButton = new JButton(buttonIcon);
            nextButton.setPreferredSize(new Dimension(
                    buttonIcon.getIconWidth(),
                    buttonIcon.getIconHeight()
            ));
            nextButton.setText("");
            nextButton.setToolTipText("Start Baking");
        } else {
            nextButton = new JButton("Unseal & Begin Baking") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2d.setColor(new Color(255, 253, 208));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                    g2d.setColor(new Color(139, 69, 19));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                    g2d.dispose();
                    super.paintComponent(g);
                }
            };

            nextButton.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 16));
            nextButton.setForeground(new Color(139, 69, 19));
            nextButton.setBackground(new Color(255, 253, 208));
            nextButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            nextButton.setContentAreaFilled(false);
        }

        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setOpaque(false);
        nextButton.setContentAreaFilled(false);

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (game.getButtonIcon(1) == null) {
                    nextButton.setForeground(new Color(178, 34, 34));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (game.getButtonIcon(1) == null) {
                    nextButton.setForeground(new Color(139, 69, 19));
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showScreen("BAKING");
            }
        });

        panel.add(nextButton);
        return panel;
    }

    public void displayLetter(Order order) {
        // Regenerate customer for each new day
        regenerateCustomer();

        // Make sure letterArea is initialized
        if (letterArea == null) {
            initializeLetterContent();
        }

        // Format the letter with proper spacing and include customer name
        String formattedLetter = formatLetterText(order.getLetter(), currentCustomer);
        letterArea.setText(formattedLetter);
        letterArea.setCaretPosition(0);

        // Update the title panel to show current customer
        updateTitlePanel();

        // Repaint to show new signature and seal
        revalidate();
        repaint();
    }

    private void updateTitlePanel() {
        // This would update the title panel to show the current customer
        // For simplicity, we'll just repaint the whole screen
        if (letterPanel != null) {
            letterPanel.repaint();
        }
    }

    private String formatLetterText(String letter, String customerName) {
        StringBuilder formatted = new StringBuilder();

        // Replace the placeholder with actual customer name
        //String personalizedLetter = letter.replace(game.getPlayerName(), customerName);
        String[] lines = letter.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                formatted.append("\n");
            } else if (!formatted.toString().endsWith("\n\n") && formatted.length() > 0) {
                formatted.append("    ").append(line).append("\n");
            } else {
                formatted.append(line).append("\n");
            }
        }

        // Add a personalized closing
        formatted.append("\n\n    Yours truly,");
        formatted.append("\n    ").append(customerName);
        formatted.append("\n\n    P.S. Your baking is simply purr-fect, ").append(game.getPlayerName()).append("!");

        return formatted.toString();
    }

    public String getCurrentCustomer() {
        return currentCustomer;
    }

    public String getCurrentSignatureStyle() {
        return currentSignatureStyle;
    }
}