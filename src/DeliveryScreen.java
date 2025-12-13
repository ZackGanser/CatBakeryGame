import javax.swing.*;
import java.awt.*;

public class DeliveryScreen extends BaseScreen {
    private JComboBox<String> deliveryComboBox;
    private JLabel selectionsLabel;
    private JLabel bakerCatLabel;
    private JLabel cakeDetailsLabel;
    private String[] deliveryMethods = {"Delivery", "Pickup", "Mail"};
    private ImageIcon bakerCatImage;

    public DeliveryScreen(CatBakeryGame game) {
        super(game);
        setBackgroundImage(game.getDeliveryBg());
        loadBakerCatImage();
        createUI();
    }

    private void loadBakerCatImage() {
        // Try to load the player's chosen baker cat image
        String playerName = game.getPlayerName();
        if (playerName != null) {
            String catKey = getCatKeyFromName(playerName);
            bakerCatImage = game.getCatImage(catKey);
        }

        // Fallback: use default cat icon from game
        if (bakerCatImage == null) {
            bakerCatImage = game.getCatIcon();
        }
    }

    private String getCatKeyFromName(String playerName) {
        String lowerName = playerName.toLowerCase();
        if (lowerName.contains("pippi")) return "pippi";
        if (lowerName.contains("sammi")) return "sammi";
        if (lowerName.contains("sophie")) return "sophie";
        return "default";
    }

    private void createUI() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Title
        JLabel titleLabel = new JLabel("Ready for Delivery!", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Center area with cat on left and cake on right
        JPanel centerPanel = createCenterPanel();
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Delivery selection panel
        JPanel deliveryPanel = createDeliveryPanel();
        contentPanel.add(deliveryPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 40, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Left: Baker cat panel
        JPanel catPanel = createCatPanel();
        panel.add(catPanel);

        // Right: Cake order panel
        JPanel cakeOrderPanel = createCakeOrderPanel();
        panel.add(cakeOrderPanel);

        return panel;
    }

    private JPanel createCatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Baker cat image
        bakerCatLabel = new JLabel();
        updateBakerCatDisplay();
        bakerCatLabel.setHorizontalAlignment(JLabel.CENTER);
        bakerCatLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel catContainer = new JPanel(new BorderLayout());
        catContainer.setOpaque(false);
        catContainer.add(bakerCatLabel, BorderLayout.CENTER);

        panel.add(catContainer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCakeOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Your Cake Order", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));

        // Cake details
        selectionsLabel = new JLabel("", JLabel.CENTER);
        selectionsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        selectionsLabel.setForeground(new Color(139, 69, 19));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setOpaque(false);
        detailsPanel.add(selectionsLabel, BorderLayout.NORTH);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(detailsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDeliveryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Delivery selection area
        JPanel selectionArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        selectionArea.setOpaque(false);

        // Delivery method label
        JLabel deliveryLabel = new JLabel("Choose delivery method:");
        deliveryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        deliveryLabel.setForeground(Color.WHITE);

        // Delivery combo box
        deliveryComboBox = new JComboBox<>(deliveryMethods);
        deliveryComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        deliveryComboBox.setBackground(Color.WHITE);
        deliveryComboBox.setPreferredSize(new Dimension(150, 35));

        selectionArea.add(deliveryLabel);
        selectionArea.add(deliveryComboBox);

        // Deliver button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton deliverButton = createImageButton(game.getButtonIcon(3), "Deliver Cake!");
        deliverButton.addActionListener(e -> deliverCake());

        deliverButton.setFocusPainted(false);
        deliverButton.setContentAreaFilled(false);
        deliverButton.setOpaque(false);
        deliverButton.setBorderPainted(false);

        buttonPanel.add(deliverButton);

        panel.add(selectionArea, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createImageButton(ImageIcon icon, String text) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setText("");
            button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        } else {
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(34, 139, 34));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 100, 0), 3),
                    BorderFactory.createEmptyBorder(12, 30, 12, 30)
            ));
        }
        button.setFocusPainted(false);
        button.setContentAreaFilled(icon == null);
        button.setOpaque(icon == null);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (icon == null) {
                    button.setBackground(new Color(50, 205, 50));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (icon == null) {
                    button.setBackground(new Color(34, 139, 34));
                }
            }
        });

        return button;
    }

    private void updateBakerCatDisplay() {
        if (bakerCatLabel != null) {
            if (bakerCatImage != null) {
                // Scale baker cat image for display
                Image scaledImage = bakerCatImage.getImage();
                bakerCatLabel.setIcon(new ImageIcon(scaledImage));
                bakerCatLabel.setText("");
            } else {
                // Fallback to emoji
                bakerCatLabel.setIcon(null);
                bakerCatLabel.setText("🐱");
                bakerCatLabel.setFont(new Font("Serif", Font.PLAIN, 120));
                bakerCatLabel.setForeground(Color.WHITE);
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            // Update baker cat image
            loadBakerCatImage();
            updateBakerCatDisplay();

            // Update cake selections
            updateSelectionsLabel();
        }
    }

    private void updateSelectionsLabel() {
        BakingScreen bakingScreen = game.getBakingScreen();
        if (bakingScreen != null) {
            String base = bakingScreen.getSelectedBase();
            String frosting = bakingScreen.getSelectedFrosting();
            String topping = bakingScreen.getSelectedTopping();

            if (base != null && frosting != null && topping != null) {
                selectionsLabel.setText(String.format(
                        "<html><div style='text-align: center;'>" +
                                "<b style='font-size: 20px;'>%s Cake</b><br><br>" +
                                "with %s Frosting<br><br>" +
                                "and %s Topping</div></html>",
                        base, frosting, topping
                ));
            } else {
                selectionsLabel.setText("<html><div style='text-align: center;'>No cake selected</div></html>");
            }
        }
    }

    private void deliverCake() {
        BakingScreen bakingScreen = game.getBakingScreen();
        if (bakingScreen != null) {
            String base = bakingScreen.getSelectedBase();
            String frosting = bakingScreen.getSelectedFrosting();
            String topping = bakingScreen.getSelectedTopping();
            String delivery = (String) deliveryComboBox.getSelectedItem();

            game.processOrder(base, frosting, topping, delivery);
        }
    }
}