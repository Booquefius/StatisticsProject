package casper.statisticsproject.utils;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.BlackjackPlayer;
import casper.statisticsproject.objects.DealerPlayer;
import casper.statisticsproject.objects.PlayingCard;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

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

        String[] stats_lore = new String[]{
                "&7Use this item to check",
                "&7the odds of the game.",
                " ",
                "&d&lBEST MOVE: &7" + main.getUtils().getBestMove(bjplayer, new DealerPlayer("Dealer"), set).getFormattedName() +""
        };

        menu.setButton(8, new SGButton(new ItemBuilder(Material.KNOWLEDGE_BOOK)
        .name("&8STATISTICS")   
        .lore(stats_lore).get()));

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
                menu.setButton(i, new SGButton(new ItemBuilder(new ItemStack(Material.END_CRYSTAL)).name("&d&lACTIONS").build())
                        .withListener(event -> {
                            openActions(player, bjplayer, (finalI / 8) -1);
                        }));
                continue;
            }
            for (int j = 0; j < bjplayer.getStorage().size(); j++) {
                Iterator<PlayingCard> iterator = bjplayer.getStorage().get(j).iterator();
                int slot = i * j;
                while (iterator.hasNext()){
                    PlayingCard card = iterator.next();
                    menu.setButton(slot, new SGButton(new ItemBuilder(Material.PAPER).name("&7"+card.getCard().getFormattedName()+" of "+card.getCardType().getFormattedName()).get()));
                    slot++;
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
