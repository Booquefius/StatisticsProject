package casper.statisticsproject.objects;

public enum BestMove {
    STAND("Stand"),
    HIT("Hit"),
    DOUBLE("Double Down"),
    SPLIT("Split");
    private final String formattedName;

    BestMove(String formattedName){
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
