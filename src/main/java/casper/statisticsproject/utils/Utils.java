package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.doctree.HiddenTree;
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

    public void flipCard (BlackjackPlayer player, PlayingCard card, int value, int set){
        if (card.getCard().isAce()){
            player.getStorage().get(set).set(value, new PlayingCard(card.getCard().setValue(card.getCard().getValue() == 11 ? 1 : 11), card.getCardType()));
        }
    }

    public double bustChance(BlackjackPlayer player, int set){
        int value = 0;
        for (PlayingCard entry : player.getStorage().get(set)) {
            value += entry.getCard().getValue();
        }
        if (value >= 21){
            return 1.0;
        }
        return value / 21.0;
    }
    public BestMove getBestMove(BlackjackPlayer player, DealerPlayer dealer, int playerSet){
        //If player has already hit then default to stand
        if (player.getStorage().get(playerSet).size() > 2){
            return BestMove.STAND;
        }
        PlayingCard dealerCard = dealer.getFacingCard();

        PlayingCard[] cards = (PlayingCard[]) player.getStorage().get(playerSet).toArray();
        int value = cards[0].getCard().getValue() + cards[1].getCard().getValue();

        //make sure player isnt bust
        if (value >= 21){
            return BestMove.STAND;
        }

        //Deal with all the ones that have consistent rules
        if (cards[0].getCard().isAce() && cards[1].getCard().isAce())
            return BestMove.SPLIT;
        if (cards[0].getCard().getValue() == 8 && cards[1].getCard().getValue() == 8)
            return BestMove.SPLIT;
        if (cards[0].getCard().getValue() == 10 && cards[1].getCard().getValue() == 10)
            return BestMove.STAND;

        // if your cards contain an ace
        // soft cards
        if (cards[0].getCard().isAce() || cards[1].getCard().isAce()){
            if (value == 19 || value == 20){
                return BestMove.STAND;
            }
            switch (value){
                case 13: case 14: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 3: case 4: case 7: case 8: case 9: case 10: case 11: case 1:
                            return BestMove.HIT;
                        case 5: case 6:
                            return BestMove.DOUBLE;
                    }
                }
                case 15: case 16: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 3: case 7: case 8: case 9: case 10: case 11: case 1:
                            return BestMove.HIT;
                        case 4: case 5: case 6:
                            return BestMove.DOUBLE;
                    }
                }
                case 17: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 7: case 8: case 9: case 10: case 11: case 1:
                            return BestMove.HIT;
                        case 3: case 4: case 5: case 6:
                            return BestMove.DOUBLE;
                    }
                }
                case 18: {
                    switch (dealerCard.getCard().getValue()){
                        case 3: case 4: case 5: case 6:
                            return BestMove.DOUBLE;
                        case 2: case 7: case 8:
                            return BestMove.STAND;
                        case 9: case 10: case 1: case 11:
                            return BestMove.HIT;
                    }
                }
            }
        } else if (cards[0].getCard().getValue() == cards[1].getCard().getValue()){
            // pairs
            switch (cards[0].getCard().getValue()){
                case 1: case 11: case 8:
                    return BestMove.SPLIT;
                case 10:
                    return BestMove.STAND;
                case 2: case 3: case 7:{
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 3: case 4: case 5: case 6: case 7:
                            return BestMove.SPLIT;
                        default:
                            return BestMove.HIT;
                    }
                }
                case 4: {
                    switch (dealerCard.getCard().getValue()){
                        case 5: case 6:
                            return BestMove.SPLIT;
                        default:
                            return BestMove.HIT;
                    }
                }
                case 5: {
                    switch (dealerCard.getCard().getValue()){
                        case 10: case 11: case 1:
                            return BestMove.HIT;
                        default:
                            return BestMove.DOUBLE;
                    }
                }
                case 6: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 3: case 4: case 5: case 6:
                            return BestMove.SPLIT;
                        default:
                            return BestMove.HIT;
                    }
                }
                case 9: {
                    switch (dealerCard.getCard().getValue()){
                        case 7: case 10: case 11: case 1:
                            return BestMove.STAND;
                        default:
                            return BestMove.SPLIT;
                    }
                }
            }
        } else {
            // if the cards dont contain an ace
            // hard cards
            switch (value){
                case 5: case 6: case 7: case 8:
                    return BestMove.HIT;
                case 9: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 7: case 8: case 9: case 10: case 1: case 11:
                            return BestMove.HIT;
                        case 3: case 4: case 5: case 6:
                            return BestMove.DOUBLE;
                    }
                }
                case 10: {
                    switch (dealerCard.getCard().getValue()){
                        default:
                            return BestMove.DOUBLE;
                        case 10: case 1: case 11:
                            return BestMove.HIT;
                    }
                }
                case 11: {
                    switch (dealerCard.getCard().getValue()){
                        default:
                            return BestMove.DOUBLE;
                        case 1: case 11:
                            return BestMove.HIT;
                    }
                }
                case 12: {
                    switch (dealerCard.getCard().getValue()){
                        case 2: case 3: case 7: case 8: case 9: case 10: case 1: case 11:
                            return BestMove.HIT;
                        default:
                            return BestMove.STAND;
                    }
                }
                case 13: case 14: case 15: case 16: {
                    switch (dealerCard.getCard().getValue()){
                        case 7: case 8: case 9: case 10: case 1: case 11:
                            return BestMove.HIT;
                        default:
                            return BestMove.STAND;
                    }
                }
            }
        }

        return null;
    }

    public void sendUsage(Player player, String message){
        player.sendMessage(ChatColor.RED+"Usage: "+message);
    }

    public static PlayingCard getRandomCard(){
        int random = new Random().nextInt(Main.playingCards.size());
        return Main.playingCards.get(random);
    }
}
