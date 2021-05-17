package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {

    private final Main main;

    public Utils(Main main) {
        this.main = main;
    }

    public void flipCard (BlackjackPlayer player, PlayingCard card){
        if (Arrays.stream(player.getStorage()).filter(card1 -> card1==card).anyMatch(card1 -> true)){
            
        }
    }

    public double bustChance(BlackjackPlayer player){
        int value = 0;
        for (PlayingCard entry : player.getStorage()) {
            value += entry.getCard().getValue();
        }
        if (value >= 21){
            return 1.0;
        }
        return value / 21.0;
    }
    public BestMove getBestMove(BlackjackPlayer player, DealerPlayer dealer){
        //If player has already hit then default to stand
        if (player.getStorage().length > 2){
            return BestMove.STAND;
        }
        PlayingCard dealerCard = dealer.getFacingCard();

        PlayingCard[] cards = player.getStorage();
        int value = cards[0].getCard().getValue() + cards[1].getCard().getValue();

        //Deal with all the ones that have consistent rules
        if (cards[0].getCard().isAce() && cards[1].getCard().isAce())
            return BestMove.SPLIT;
        if (cards[0].getCard().getValue() == 8 && cards[1].getCard().getValue() == 8)
            return BestMove.SPLIT;
        if (cards[0].getCard().getValue() == 10 && cards[1].getCard().getValue() == 10)
            return BestMove.STAND;
        if (cards[0].getCard().isAce() || cards[1].getCard().isAce() && value == 20 || value == 19)
            return BestMove.STAND;
        if (value >= 17)
            return BestMove.STAND;

        // if your cards contain an ace
        // soft cards
        if (cards[0].getCard().isAce() || cards[1].getCard().isAce()){
            switch (value){
                case 13: {}
                case 14: {}
                case 15: {}
                case 16: {}
                case 17: {}
                case 18: {}
                case 19: {}
                case 20: {}
            }
        } else if (cards[0].getCard().getValue() == cards[1].getCard().getValue()){
            // pairs
        } else {
            // if the cards dont contain an ace
            // hard cards
        }

        return null;
    }

    public void sendUsage(Player player, String message){
        player.sendMessage(ChatColor.RED+"Usage: "+message);
    }

    public static PlayingCard getRandomCard(){
        int random = new Random().nextInt(Main.playingCards.size());
        return Main.playingCards.get(Main.playingCards.size());
//        int card = new Random().nextInt(Card.values().length);
//        int type = new Random().nextInt(CardType.values().length);
//        return new PlayingCard(Card.values()[card], CardType.values()[type]);
    }
}
