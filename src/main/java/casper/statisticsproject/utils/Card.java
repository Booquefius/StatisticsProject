package casper.statisticsproject.utils;

public enum Card {
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10),
    ACE_ONE("Ace", 1),
    ACE_ELEVEN("Ace", 11);

    private final int value;
    private final String formattedName;

    Card(String formattedName, int value){
        this.formattedName = formattedName;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
