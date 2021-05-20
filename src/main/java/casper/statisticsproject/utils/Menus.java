package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.BlackjackPlayer;
import casper.statisticsproject.objects.PlayingCard;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class Menus {
    private final Main main;
    
    public Menus(Main main){
        this.main = main;
    }

    /*
    * Menu where you can deal with the actions of this hand
    * */
    public void openActions(Player player, BlackjackPlayer bjplayer, int set){
        SGMenu menu = main.getGui().create("&8Actions", 1);
        menu.setAutomaticPaginationEnabled(false);

        player.openInventory(menu.getInventory());
    }

    /*
     * Menu where the blackjack is actually played.
     * */
    public void openBlackJack(Player player, BlackjackPlayer bjplayer){
        SGMenu menu = main.getGui().create("&8Blackjack", 1);
        menu.setAutomaticPaginationEnabled(false);

        for (int i = 0; i < menu.getPageSize(); i++) {
            if (i % 8 == 0){
                //ACTIONS BUTTON
                int finalI = i;
                menu.addButton(new SGButton(new ItemBuilder(new ItemStack(Material.END_CRYSTAL)).name("&d&lACTIONS").build())
                        .withListener(event -> {
                            openActions(player, bjplayer, (finalI / 8) -1);
                        }));
            }
            for (int j = 0; j < bjplayer.getStorage().keySet().size(); j++) {
                Iterator<PlayingCard> iterator = bjplayer.getStorage().get(j).iterator();
                int slot = 0;
                while (iterator.hasNext()){
                    PlayingCard card = iterator.next();
                    
                }
            }
        }

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
