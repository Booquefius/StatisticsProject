package casper.statisticsproject.objects;

public enum CardType {
    CLUB("Clubs"),
    SPADE("Spades"),
    HEART("Hearts"),
    DIAMOND("Diamonds");

    private final String formattedName;

    CardType(String formattedName){
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
