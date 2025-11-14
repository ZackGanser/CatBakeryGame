import javax.swing.*;
import java.awt.*;

public class DeliveryScreen extends BaseScreen {
    private JComboBox<String> deliveryComboBox;
    private JLabel selectionsLabel;
    private String[] deliveryMethods = {"Delivery", "Pickup", "Mail"};

    public DeliveryScreen(CatBakeryGame game) {
        super(game);
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("\uD83D\uDE9A Delivery Method", JLabel.CENTER); //Delivery Truck emoji
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        selectionsLabel = new JLabel("", JLabel.CENTER);
        selectionsLabel.setFont(new Font("Serif", Font.PLAIN, 14));

        JPanel deliveryPanel = new JPanel(new FlowLayout());
        deliveryPanel.setOpaque(false);
        deliveryPanel.add(new JLabel("Choose delivery method:"));
        deliveryComboBox = new JComboBox<>(deliveryMethods);
        deliveryComboBox.setFont(new Font("Serif", Font.PLAIN, 14));
        deliveryPanel.add(deliveryComboBox);

        JButton deliverButton = new JButton("Deliver Cake!");
        deliverButton.setFont(new Font("Serif", Font.BOLD, 16));
        deliverButton.addActionListener(e -> deliverCake());

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(selectionsLabel, BorderLayout.CENTER);
        contentPanel.add(deliveryPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
        add(deliverButton, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            updateSelectionsLabel();
        }
    }

    private void updateSelectionsLabel() {
        BakingScreen bakingScreen = game.getBakingScreen();
        String base = bakingScreen.getSelectedBase();
        String frosting = bakingScreen.getSelectedFrosting();
        String topping = bakingScreen.getSelectedTopping();

        selectionsLabel.setText(String.format(
                "Your cake: %s base with %s frosting and %s topping",
                base, frosting, topping
        ));
    }

    private void deliverCake() {
        BakingScreen bakingScreen = game.getBakingScreen();
        String base = bakingScreen.getSelectedBase();
        String frosting = bakingScreen.getSelectedFrosting();
        String topping = bakingScreen.getSelectedTopping();
        String delivery = (String) deliveryComboBox.getSelectedItem();

        game.processOrder(base, frosting, topping, delivery);
    }
}