package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import com.samjakob.spigui.SGMenu;
import org.bukkit.entity.Player;

public class Menus {
    private final Main main;
    
    public Menus(Main main){
        this.main = main;
    }


    /*
     * Menu where the blackjack is actually played.
     * */
    public void openBlackJack(Player player){
        SGMenu menu = main.getGui().create("&8Blackjack", 1);
        menu.setAutomaticPaginationEnabled(false);

        player.openInventory(menu.getInventory());
    }

    /*
     * Menu where users pick their player
     * */
    public void openSelection(Player player){
        SGMenu menu = main.getGui().create("&8Please select who you are.", 1);
        menu.setAutomaticPaginationEnabled(false);

        player.openInventory(menu.getInventory());
    }
}
