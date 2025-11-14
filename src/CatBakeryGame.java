import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CatBakeryGame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private int currentDay = 1;
    private int score = 0;
    private int health = 100;
    private int highScore = 0;
    private String playerName = "Pippi";
    private Random random = new Random();
    private Order currentOrder;

    private StartScreen startScreen;
    private LetterScreen letterScreen;
    private BakingScreen bakingScreen;
    private DeliveryScreen deliveryScreen;
    private FeedbackScreen feedbackScreen;
    private GameOverScreen gameOverScreen;

    public CatBakeryGame() {
        setTitle("Cozy Bakery Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        setupScreens();
        showStartScreen();
    }

    private void setupScreens() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        startScreen = new StartScreen(this);
        letterScreen = new LetterScreen(this);
        bakingScreen = new BakingScreen(this);
        deliveryScreen = new DeliveryScreen(this);
        feedbackScreen = new FeedbackScreen(this);
        gameOverScreen = new GameOverScreen(this);

        mainPanel.add(startScreen, "START");
        mainPanel.add(letterScreen, "LETTER");
        mainPanel.add(bakingScreen, "BAKING");
        mainPanel.add(deliveryScreen, "DELIVERY");
        mainPanel.add(feedbackScreen, "FEEDBACK");
        mainPanel.add(gameOverScreen, "GAME_OVER");

        add(mainPanel);
    }

    public void initializeGame() {
        health = 100;
        score = 0;
        currentDay = 1;
    }

    public void startNewGame() {
        initializeGame();
        startNewDay();
    }

    private void startNewDay() {
        currentOrder = generateOrder();
        letterScreen.displayLetter(currentOrder);
        showScreen("LETTER");
        updateUI();
    }

    //Getter methods in case we end up needing them. We can just get rid of any we don't end up using.
    public DeliveryScreen getDeliveryScreen() { return deliveryScreen; }
    public BakingScreen getBakingScreen() { return bakingScreen; }
    public LetterScreen getLetterScreen() { return letterScreen; }
    public FeedbackScreen getFeedbackScreen() { return feedbackScreen; }
    public GameOverScreen getGameOverScreen() { return gameOverScreen; }
    public StartScreen getStartScreen() { return startScreen; }
    public int getHighScore() { return highScore; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String name) { this.playerName = name; }

    public void showStartScreen() {
        startScreen.updateHighScore(highScore);
        showScreen("START");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    private void updateUI() {
        GameState gameState = new GameState(currentDay, score, health, playerName);
        letterScreen.updateGameInfo(gameState);
        bakingScreen.updateGameInfo(gameState);
        deliveryScreen.updateGameInfo(gameState);
        feedbackScreen.updateGameInfo(gameState);
    }

    private Order generateOrder() {
        //I based the cake bases and toppings on the existing assets that Jessica made. The frostings are just
        //a placeholder. I like the idea of cake toppers. It will be easy to swap the frostings out for the toppers.
        Map<String, List<String>> baseHints = new HashMap<>();
        baseHints.put("Chocolate", Arrays.asList("rich and dark", "decadent", "cocoa delight"));
        baseHints.put("Vanilla", Arrays.asList("classic and simple", "light and fluffy", "traditional"));
        baseHints.put("Strawberry", Arrays.asList("fruity and delicious", "slightly tangy", "elegant and red"));

        Map<String, List<String>> frostingHints = new HashMap<>();
        frostingHints.put("Buttercream", Arrays.asList("sweet and creamy", "smooth and rich", "buttery"));
        frostingHints.put("Chocolate Ganache", Arrays.asList("glossy and rich", "intense chocolate", "shiny coating"));
        frostingHints.put("Fruit Glaze", Arrays.asList("fruity and shiny", "fresh fruit coating", "glossy finish"));

        Map<String, List<String>> toppingHints = new HashMap<>();
        toppingHints.put("Sprinkles", Arrays.asList("colorful bits", "party-ready", "festive decoration"));
        toppingHints.put("Strawberries", Arrays.asList("natural and fresh", "fruit decoration", "healthy topping"));
        toppingHints.put("Chocolate Chips", Arrays.asList("chocolate pieces", "melty bits", "chocolate dots"));

        Map<String, List<String>> deliveryHints = new HashMap<>();
        deliveryHints.put("Delivery", Arrays.asList("bring it to my door", "drop it off", "home delivery"));
        deliveryHints.put("Pickup", Arrays.asList("I'll come get it", "pick it up myself", "store pickup"));
        deliveryHints.put("Mail", Arrays.asList("send it through mail", "ship it to me", "postal delivery"));

        String[] cakeBases = {"Chocolate", "Vanilla", "Strawberry"};
        String[] frostings = {"Buttercream", "Chocolate Ganache", "Fruit Glaze"};
        String[] toppings = {"Sprinkles", "Strawberries", "Chocolate Chips"};
        String[] deliveryMethods = {"Delivery", "Pickup", "Mail"};

        String correctBase = cakeBases[random.nextInt(cakeBases.length)];
        String correctFrosting = frostings[random.nextInt(frostings.length)];
        String correctTopping = toppings[random.nextInt(toppings.length)];
        String correctDelivery = deliveryMethods[random.nextInt(deliveryMethods.length)];

        List<String> baseHintList = baseHints.get(correctBase);
        List<String> frostingHintList = frostingHints.get(correctFrosting);
        List<String> toppingHintList = toppingHints.get(correctTopping);
        List<String> deliveryHintList = deliveryHints.get(correctDelivery);

        String letter = String.format(
                "Dear %s,\n\nI would like to order a cake that is %s with %s frosting and %s on top. %s.\n\nLooking forward to your creation!",
                playerName,
                baseHintList.get(random.nextInt(baseHintList.size())),
                frostingHintList.get(random.nextInt(frostingHintList.size())),
                toppingHintList.get(random.nextInt(toppingHintList.size())),
                deliveryHintList.get(random.nextInt(deliveryHintList.size()))
        );

        return new Order(correctBase, correctFrosting, correctTopping, correctDelivery, letter);
    }

    public void processOrder(String base, String frosting, String topping, String delivery) {
        int correctChoices = 0;
        StringBuilder feedback = new StringBuilder();

        feedback.append("Customer's Text Message:\n\n");

        if (base.equals(currentOrder.getCorrectBase())) {
            correctChoices++;
            feedback.append("✓ The cake base is perfect!\n");
        } else {
            feedback.append("✗ This isn't the base I wanted...\n");
            health -= 10;
        }

        if (frosting.equals(currentOrder.getCorrectFrosting())) {
            correctChoices++;
            feedback.append("✓ The frosting is exactly right!\n");
        } else {
            feedback.append("✗ The frosting doesn't match what I expected...\n");
            health -= 10;
        }

        if (topping.equals(currentOrder.getCorrectTopping())) {
            correctChoices++;
            feedback.append("✓ The topping is wonderful!\n");
        } else {
            feedback.append("✗ I was hoping for a different topping...\n");
            health -= 10;
        }

        if (delivery.equals(currentOrder.getCorrectDelivery())) {
            correctChoices++;
            feedback.append("✓ Delivery method was perfect!\n");
        } else {
            feedback.append("✗ This isn't how I wanted to receive it...\n");
            health -= 15;
        }

        int pointsEarned = correctChoices * 25;
        score += pointsEarned;

        feedback.append("\nPoints earned today: ").append(pointsEarned);
        feedback.append("\nCorrect choices: ").append(correctChoices).append("/4");

        feedbackScreen.displayFeedback(feedback.toString());
        showScreen("FEEDBACK");
        updateUI();

        if (health <= 0) {
            gameOver();
        }
    }

    public void gameOver() {
        if (score > highScore) {
            highScore = score;
        }
        gameOverScreen.displayGameOver(currentDay, score, highScore, playerName);
        showScreen("GAME_OVER");
    }

    public void nextDay() {
        currentDay++;
        startNewDay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CatBakeryGame().setVisible(true);
        });
    }
}