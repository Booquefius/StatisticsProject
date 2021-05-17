package casper.statisticsproject;

import casper.statisticsproject.commands.GameCommand;
import casper.statisticsproject.commands.GameTabCompleter;
import casper.statisticsproject.objects.BlackjackPlayer;
import casper.statisticsproject.objects.Card;
import casper.statisticsproject.objects.CardType;
import casper.statisticsproject.objects.PlayingCard;
import casper.statisticsproject.utils.Menus;
import casper.statisticsproject.utils.Utils;
import com.samjakob.spigui.SpiGUI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    Utils utils;
    SpiGUI gui;
    List<BlackjackPlayer> blackjackPlayers = new ArrayList<>();
    public static List<PlayingCard> playingCards = new ArrayList<>();
    public boolean isGameRunning;
    public Menus menus;


    @Override
    public void onEnable() {
        menus = new Menus(this);
        gui = new SpiGUI(this);
        utils = new Utils(this);

        isGameRunning = false;

        for (Card card : Card.values()) {
            for (CardType cardType : CardType.values()) {
                playingCards.add(new PlayingCard(card, cardType));
            }
        }

        getCommand("game").setExecutor(new GameCommand(this));
        getCommand("game").setTabCompleter(new GameTabCompleter(this));
//        getCommand("oldgame").setExecutor(new OldGameCommand(this));


    }

    @Override
    public void onDisable() {

    }

    public SpiGUI getGui() {
        return gui;
    }


    public Utils getUtils() {
        return utils;
    }

    public Menus getMenus() { return menus; }

    public List<BlackjackPlayer> getBlackjackPlayers() {
        return blackjackPlayers;
    }
    public  BlackjackPlayer getBlackjackPlayer(String name){
        return blackjackPlayers.stream().filter(blackjackPlayer -> blackjackPlayer.getName().equals(name)).findFirst().get();
    }
    public void addBlackjackPlayer(BlackjackPlayer players){
        blackjackPlayers.add(players);
    }

    public void removeBlackjackPlayer(BlackjackPlayer players){
        blackjackPlayers.removeIf(players1 -> players==players1);
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}
