package casper.statisticsproject.objects;

public class PlayingCard {
    private final Card card;
    private final CardType cardType;

    public PlayingCard(Card card, CardType cardType){
        this.card = card;
        this.cardType = cardType;
    }

    public Card getCard() {
        return card;
    }

    public CardType getCardType() {
        return cardType;
    }
}
