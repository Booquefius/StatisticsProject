package casper.statisticsproject.objects;

public class DealerPlayer extends BlackjackPlayer{
    PlayingCard facingCard;
    public DealerPlayer(String name) {
        super(name);
        facingCard = getFirstCard();
    }

    public PlayingCard getFacingCard() {
        return facingCard;
    }
}
