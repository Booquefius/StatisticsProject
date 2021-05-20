package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.BlackjackPlayer;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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



        for (BlackjackPlayer blackjackPlayer : main.getBlackjackPlayers()) {
            menu.addButton(new SGButton(new ItemBuilder(Material.PLAYER_HEAD).name(blackjackPlayer.getName()).get())
                    .withListener(event -> {
                        main.addPlayer(player, blackjackPlayer);
                        player.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SUCCESS! "+ChatColor.WHITE+"You have joined the game as "+blackjackPlayer.getName());
                    }));
        }

        player.openInventory(menu.getInventory());
    }
}
