package casper.statisticsproject.objects;

public enum Card {
    TWO("Two", 2, false),
    THREE("Three", 3, false),
    FOUR("Four", 4, false),
    FIVE("Five", 5, false),
    SIX("Six", 6, false),
    SEVEN("Seven", 7, false),
    EIGHT("Eight", 8, false),
    NINE("Nine", 9, false),
    TEN("Ten", 10, false),
    JACK("Jack", 10, false),
    QUEEN("Queen", 10, false),
    KING("King", 10, false),
    ACE("Ace", 11, true);

    private int value;
    private final String formattedName;
    private final boolean isAce;

    Card(String formattedName, int value, boolean isAce){
        this.formattedName = formattedName;
        this.value = value;
        this.isAce = isAce;
    }

    public int getValue() {
        return value;
    }

    public Card setValue(int value) {
        this.value = value;
        return this;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public boolean isAce() {
        return isAce;
    }
}
