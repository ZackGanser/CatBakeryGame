import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BakingScreen extends BaseScreen {
    private JComboBox<String> baseComboBox, frostingComboBox, toppingComboBox;
    private String[] cakeBases = {"Chocolate", "Vanilla", "Strawberry"};
    private String[] frostings = {"Buttercream", "Chocolate Ganache", "Fruit Glaze"};
    private String[] toppings = {"Sprinkles", "Strawberries", "Chocolate Chips"};

    // Image assets
    private Map<String, ImageIcon> baseImages = new HashMap<>();
    private Map<String, ImageIcon> frostingImages = new HashMap<>();
    private Map<String, ImageIcon> toppingImages = new HashMap<>();
    private ImageIcon bakerCatImage;

    // Preview components
    private JLabel cakePreviewLabel;
    private JLabel bakerCatLabel;
    private JPanel cakePreviewPanel;

    // Current selections
    private String currentBase = null;
    private String currentFrosting = null;
    private String currentTopping = null;

    public BakingScreen(CatBakeryGame game) {
        super(game);
        setBackgroundImage(game.getBakingBg());

        // Load images
        loadIngredientImages();
        loadBakerCatImage();

        createUI();
    }

    private void loadIngredientImages() {
        // Load cake base images
        for (String base : cakeBases) {
            String filename = "cake_" + base.toLowerCase().replace(" ", "_") + ".png";
            ImageIcon icon = game.loadImage("ingredients/" + filename, "ingredients/" + filename.replace(".png", ".jpg"));
            if (icon != null) {
                baseImages.put(base, icon);
            } else {
                System.err.println("Could not load base image: " + filename);
            }
        }

        // Load frosting images
        for (String frosting : frostings) {
            String filename = "frosting_" + frosting.toLowerCase().replace(" ", "_") + ".png";
            ImageIcon icon = game.loadImage("ingredients/" + filename, "ingredients/" + filename.replace(".png", ".jpg"));
            if (icon != null) {
                frostingImages.put(frosting, icon);
            } else {
                System.err.println("Could not load frosting image: " + filename);
            }
        }

        // Load topping images
        for (String topping : toppings) {
            String filename = "topping_" + topping.toLowerCase().replace(" ", "_") + ".png";
            ImageIcon icon = game.loadImage("ingredients/" + filename, "ingredients/" + filename.replace(".png", ".jpg"));
            if (icon != null) {
                toppingImages.put(topping, icon);
            } else {
                System.err.println("Could not load topping image: " + filename);
            }
        }

        System.out.println("Loaded images - Bases: " + baseImages.size() +
                ", Frostings: " + frostingImages.size() +
                ", Toppings: " + toppingImages.size());
    }

    private void loadBakerCatImage() {
        String playerName = game.getPlayerName();
        if (playerName != null) {
            String catKey = getCatKeyFromName(playerName);
            bakerCatImage = game.getCatImage(catKey);
        }

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

        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Title
        JLabel titleLabel = new JLabel("Bake Your Cake!", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainContent.add(titleLabel, BorderLayout.NORTH);

        // Center: Cat and cake preview
        JPanel previewArea = createPreviewArea();
        mainContent.add(previewArea, BorderLayout.CENTER);

        // Bottom: Ingredient selection and next button
        JPanel bottomPanel = createBottomPanel();
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createPreviewArea() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left: Baker cat
        JPanel catPanel = createCatPanel();
        panel.add(catPanel, BorderLayout.WEST);

        // Center: Spacer
        panel.add(Box.createHorizontalStrut(40), BorderLayout.CENTER);

        // Right: Cake preview
        JPanel cakePanel = createCakePreviewPanel();
        panel.add(cakePanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createCatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(250, 350));

        // Cat image
        bakerCatLabel = new JLabel();
        updateBakerCatDisplay();
        bakerCatLabel.setHorizontalAlignment(JLabel.CENTER);

        // Decorative frame
        JPanel framePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.dispose();
            }
        };
        framePanel.setOpaque(false);
        framePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        framePanel.add(bakerCatLabel, BorderLayout.CENTER);

        panel.add(framePanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCakePreviewPanel() {
        cakePreviewPanel = new JPanel(new BorderLayout());
        cakePreviewPanel.setOpaque(false);
        cakePreviewPanel.setPreferredSize(new Dimension(350, 350));

        // Cake preview label
        cakePreviewLabel = new JLabel("", JLabel.CENTER);
        cakePreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        cakePreviewLabel.setVerticalAlignment(JLabel.CENTER);

        // Decorative cake stand/plate
        JPanel cakeStandPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        cakeStandPanel.setOpaque(false);
        cakeStandPanel.add(cakePreviewLabel, BorderLayout.CENTER);

        // Preview title
        JLabel previewTitle = new JLabel("Your Cake Creation", JLabel.CENTER);
        previewTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        previewTitle.setForeground(Color.WHITE);
        previewTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Current selections display
        JPanel selectionsPanel = createSelectionsPanel();

        cakePreviewPanel.add(previewTitle, BorderLayout.NORTH);
        cakePreviewPanel.add(cakeStandPanel, BorderLayout.CENTER);
        cakePreviewPanel.add(selectionsPanel, BorderLayout.SOUTH);

        return cakePreviewPanel;
    }

    private JPanel createSelectionsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Create selection displays
        JLabel baseSelection = createSelectionDisplay("Base:");
        JLabel frostingSelection = createSelectionDisplay("Frosting:");
        JLabel toppingSelection = createSelectionDisplay("Topping:");

        panel.add(baseSelection);
        panel.add(frostingSelection);
        panel.add(toppingSelection);

        // Store references for updating
        baseSelection.setName("baseDisplay");
        frostingSelection.setName("frostingDisplay");
        toppingSelection.setName("toppingDisplay");

        return panel;
    }

    private JLabel createSelectionDisplay(String label) {
        JLabel displayLabel = new JLabel(label + " None", JLabel.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 12));
        displayLabel.setForeground(new Color(255, 253, 208));
        displayLabel.setOpaque(true);
        displayLabel.setBackground(new Color(139, 69, 19, 180));
        displayLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 253, 208, 100), 1),
                BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        return displayLabel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Ingredient selection panel
        JPanel selectionPanel = createSelectionPanel();
        panel.add(selectionPanel, BorderLayout.NORTH);

        // Next button panel
        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);
        panel.setBackground(new Color(255, 253, 208, 100));

        // Create dropdowns
        baseComboBox = createCompactComboBox(cakeBases, "Select Base");
        frostingComboBox = createCompactComboBox(frostings, "Select Frosting");
        toppingComboBox = createCompactComboBox(toppings, "Select Topping");

        // Add labels above dropdowns
        JPanel basePanel = createDropdownWithLabel("Cake Base:", baseComboBox);
        JPanel frostingPanel = createDropdownWithLabel("Frosting:", frostingComboBox);
        JPanel toppingPanel = createDropdownWithLabel("Topping:", toppingComboBox);

        panel.add(basePanel);
        panel.add(frostingPanel);
        panel.add(toppingPanel);

        // Add action listeners
        baseComboBox.addActionListener(e -> updateCakePreview());
        frostingComboBox.addActionListener(e -> updateCakePreview());
        toppingComboBox.addActionListener(e -> updateCakePreview());

        return panel;
    }

    private JComboBox<String> createCompactComboBox(String[] items, String placeholder) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setMaximumRowCount(6);

        // Make it compact
        Dimension size = new Dimension(140, 28);
        comboBox.setPreferredSize(size);
        comboBox.setMaximumSize(size);
        comboBox.setMinimumSize(size);

        return comboBox;
    }

    private JPanel createDropdownWithLabel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton nextButton = createImageButton(game.getButtonIcon(2), "Choose Delivery →");
        nextButton.addActionListener(e -> proceedToDelivery());

        panel.add(nextButton);
        return panel;
    }

    private JButton createImageButton(ImageIcon icon, String text) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setText("");
            button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        } else {
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(144, 238, 144));
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
        }

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);

        return button;
    }

    private void updateCakePreview() {
        // Update current selections
        currentBase = (String) baseComboBox.getSelectedItem();
        currentFrosting = (String) frostingComboBox.getSelectedItem();
        currentTopping = (String) toppingComboBox.getSelectedItem();

        // Update selection displays
        updateSelectionDisplays();

        // Create layered cake image
        createLayeredCakeImage();
    }

    private void updateSelectionDisplays() {
        // Find and update the display labels
        Component[] components = cakePreviewPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                Component[] subComps = ((JPanel)comp).getComponents();
                for (Component subComp : subComps) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        String name = label.getName();
                        if (name != null) {
                            switch (name) {
                                case "baseDisplay":
                                    label.setText("Base: " + (currentBase != null ? currentBase : "None"));
                                    break;
                                case "frostingDisplay":
                                    label.setText("Frosting: " + (currentFrosting != null ? currentFrosting : "None"));
                                    break;
                                case "toppingDisplay":
                                    label.setText("Topping: " + (currentTopping != null ? currentTopping : "None"));
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void createLayeredCakeImage() {
        // Determine target size for cake preview
        int targetWidth = 220;
        int targetHeight = 220;

        // Create a transparent image to layer components
        BufferedImage composite = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = composite.createGraphics();

        // Enable anti-aliasing for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Draw layers in correct order (base → frosting → topping)
        boolean hasBase = drawImageLayer(g2d, currentBase, baseImages, "base", targetWidth, targetHeight);
        boolean hasFrosting = drawImageLayer(g2d, currentFrosting, frostingImages, "frosting", targetWidth, targetHeight);
        boolean hasTopping = drawImageLayer(g2d, currentTopping, toppingImages, "topping", targetWidth, targetHeight);

        g2d.dispose();

        // Update the preview label
        if (hasBase || hasFrosting || hasTopping) {
            cakePreviewLabel.setIcon(new ImageIcon(composite));
            cakePreviewLabel.setText("");
        } else {
            // Show placeholder if no images
            cakePreviewLabel.setIcon(null);
            cakePreviewLabel.setText("🎂");
            cakePreviewLabel.setFont(new Font("Serif", Font.PLAIN, 100));
        }
    }

    private boolean drawImageLayer(Graphics2D g2d, String ingredient, Map<String, ImageIcon> imageMap,
                                   String layerType, int targetWidth, int targetHeight) {
        if (ingredient != null && imageMap.containsKey(ingredient)) {
            ImageIcon icon = imageMap.get(ingredient);
            if (icon != null) {
                Image image = icon.getImage();

                // Calculate scaling to fit target size
                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);

                // Scale image to fit preview area while maintaining aspect ratio
                double scale = Math.min((double)targetWidth / imgWidth, (double)targetHeight / imgHeight);
                int scaledWidth = (int)(imgWidth * scale);
                int scaledHeight = (int)(imgHeight * scale);

                // Center the image
                int x = (targetWidth - scaledWidth) / 2;
                int y = (targetHeight - scaledHeight) / 2;

                // Draw the image
                g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);
                return true;
            }
        }
        return false;
    }

    private void proceedToDelivery() {
        game.showScreen("DELIVERY");
    }

    public String getSelectedBase() {
        return currentBase;
    }

    public String getSelectedFrosting() {
        return currentFrosting;
    }

    public String getSelectedTopping() {
        return currentTopping;
    }

    private void updateBakerCatDisplay() {
        if (bakerCatLabel != null) {
            if (bakerCatImage != null) {
                // Scale cat image for display
                Image scaledImage = bakerCatImage.getImage();
                bakerCatLabel.setIcon(new ImageIcon(scaledImage));
                bakerCatLabel.setText("");
            } else {
                bakerCatLabel.setIcon(null);
                bakerCatLabel.setText("🐱");
                bakerCatLabel.setFont(new Font("Serif", Font.PLAIN, 120));
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            // Reload images
            loadBakerCatImage();
            updateBakerCatDisplay();

            // Update cake preview with current selections
            updateCakePreview();
        }
    }
}