package casper.statisticsproject;

import casper.statisticsproject.commands.GameCommand;
import casper.statisticsproject.utils.BlackjackPlayer;
import casper.statisticsproject.utils.Utils;
import com.samjakob.spigui.SpiGUI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    Utils utils;
    SpiGUI gui;
    List<BlackjackPlayer> blackjackPlayers = new ArrayList<>();
    public boolean isGameRunning;



    @Override
    public void onEnable() {
        gui = new SpiGUI(this);
        utils = new Utils(this);

        isGameRunning = false;

        getCommand("game").setExecutor(new GameCommand(this));


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
