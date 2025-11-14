public class Order {
    private String correctBase;
    private String correctFrosting;
    private String correctTopping;
    private String correctDelivery;
    private String letter;

    public Order(String correctBase, String correctFrosting, String correctTopping,
                 String correctDelivery, String letter) {
        this.correctBase = correctBase;
        this.correctFrosting = correctFrosting;
        this.correctTopping = correctTopping;
        this.correctDelivery = correctDelivery;
        this.letter = letter;
    }

    public String getCorrectBase() { return correctBase; }
    public String getCorrectFrosting() { return correctFrosting; }
    public String getCorrectTopping() { return correctTopping; }
    public String getCorrectDelivery() { return correctDelivery; }
    public String getLetter() { return letter; }
}