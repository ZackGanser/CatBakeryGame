import javax.swing.*;
import java.awt.*;

public class BakingScreen extends BaseScreen {
    private JComboBox<String> baseComboBox, frostingComboBox, toppingComboBox;
    private String[] cakeBases = {"Chocolate", "Vanilla", "Strawberry"};
    private String[] frostings = {"Buttercream", "Chocolate Ganache", "Fruit Glaze"};
    private String[] toppings = {"Sprinkles", "Strawberries", "Chocolate Chips"};

    public BakingScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 196));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("\uD83C\uDF82 Bake the Cake", JLabel.CENTER); //Cake emoji
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel ingredientsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        ingredientsPanel.setOpaque(false);

        ingredientsPanel.add(createIngredientPanel("Cake Base", baseComboBox = new JComboBox<>(cakeBases)));
        ingredientsPanel.add(createIngredientPanel("Frosting", frostingComboBox = new JComboBox<>(frostings)));
        ingredientsPanel.add(createIngredientPanel("Topping", toppingComboBox = new JComboBox<>(toppings)));

        JButton nextButton = new JButton("Choose Delivery →");
        nextButton.setFont(new Font("Serif", Font.BOLD, 16));
        nextButton.addActionListener(e -> proceedToDelivery());

        contentPanel.add(titleLabel);
        contentPanel.add(ingredientsPanel);

        add(contentPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);
    }

    private void proceedToDelivery() {
        //Thought I might need these, but haven't yet. I'll keep them here in case we end up needing to use
        /*String base = (String) baseComboBox.getSelectedItem();
        String frosting = (String) frostingComboBox.getSelectedItem();
        String topping = (String) toppingComboBox.getSelectedItem();*/

        game.showScreen("DELIVERY");
    }

    //Getter methods for the selections
    public String getSelectedBase() {
        return (String) baseComboBox.getSelectedItem();
    }

    public String getSelectedFrosting() {
        return (String) frostingComboBox.getSelectedItem();
    }

    public String getSelectedTopping() {
        return (String) toppingComboBox.getSelectedItem();
    }

    private JPanel createIngredientPanel(String title, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(139, 69, 19));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        comboBox.setFont(new Font("Serif", Font.PLAIN, 14));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);

        return panel;
    }
}