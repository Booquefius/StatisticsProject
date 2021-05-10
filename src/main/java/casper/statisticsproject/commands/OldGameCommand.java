package casper.statisticsproject.commands;

import casper.statisticsproject.Main;
import casper.statisticsproject.utils.BlackjackPlayer;
import casper.statisticsproject.utils.Card;
import casper.statisticsproject.utils.CardType;
import casper.statisticsproject.utils.Utils;
import com.google.common.collect.Multimap;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OldGameCommand implements CommandExecutor {
    private final Main main;

    public OldGameCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        openBlackJack((Player) sender);
        return true;
    }

    public void openBlackJack(Player player){
        SGMenu menu = main.getGui().create("&8Blackjack", 1);
        menu.setAutomaticPaginationEnabled(false);
        if (!main.isGameRunning()){
            // CLEAR
            main.getBlackjackPlayers().clear();
            // INITIALIZE
            main.addBlackjackPlayer(new BlackjackPlayer(player));
            main.isGameRunning = true;
        }
        for (Map.Entry<Card, CardType> entry : main.getBlackjackPlayer(player.getDisplayName()).getStorage().entries()) {
            if (entry.getKey() == Card.ACE_ELEVEN || entry.getKey() == Card.ACE_ONE){
                menu.addButton(new SGButton(new ItemBuilder(Material.PAPER)
                        .name("&f"+entry.getKey().getFormattedName() + " of "+entry.getValue().getFormattedName())
                        .lore(Arrays.asList(" ", "&7Value: &n"+entry.getKey().getValue(), " "))
                        .get()
                ).withListener(event -> {
                    for (Map.Entry<Card, CardType> card : main.getBlackjackPlayer(player.getDisplayName()).getStorage().entries()) {
                        if (card.getKey() != Card.ACE_ELEVEN && card.getKey() != Card.ACE_ONE) continue;
                        Multimap<Card, CardType> s = main.getBlackjackPlayer(player.getDisplayName()).getStorage();
                        s.remove(card.getKey(), card.getValue());
                        s.put(main.getUtils().flipCard(card.getKey()), card.getValue());
                        main.getBlackjackPlayer(player.getDisplayName()).setStorage(s);

                        openBlackJack(player);
                        break;
                    }
                }));
            } else {
                menu.addButton(new SGButton(new ItemBuilder(Material.PAPER)
                        .name("&f"+entry.getKey().getFormattedName() + " of "+entry.getValue().getFormattedName())
                        .lore(Arrays.asList(" ", "&7Value: &n"+entry.getKey().getValue(), " "))
                        .get()
                ));
            }

        }

        if (main.getUtils().bustChance(main.getBlackjackPlayer(player.getDisplayName())) < 0.5 ){
            menu.setButton(7, new SGButton(new ItemBuilder(Material.LIME_DYE)
                .name("&aHit &7(&aLow Risk&7)")
                    .lore(Arrays.asList(" ", "&7When you hit you are", "&7taking a new card.", " "
                            , "&7Risk: &a&nLow", " "))
                    .build()
            ).withListener(event -> {
                main.getBlackjackPlayer(player.getDisplayName()).getStorage().putAll(Utils.getRandomCard());
                openBlackJack(player);
            }));
        } else if (main.getUtils().bustChance(main.getBlackjackPlayer(player.getDisplayName())) < 0.85){
            menu.setButton(7, new SGButton(new ItemBuilder(Material.YELLOW_DYE)
                    .name("&eHit &7(&eMedium Risk&7)")
                    .lore(Arrays.asList(" ", "&7When you hit you are", "&7taking a new card.", " "
                            , "&7Risk: &e&nMedium", " "))
                    .build()
            ).withListener(event -> {
                main.getBlackjackPlayer(player.getDisplayName()).getStorage().putAll(Utils.getRandomCard());
                openBlackJack(player);
            }));
        } else {
            menu.setButton(7, new SGButton(new ItemBuilder(Material.RED_DYE)
                    .name("&cHit &7(&cHigh Risk&7)")
                    .lore(Arrays.asList(" ", "&7When you hit you are", "&7taking a new card.", " "
                            , "&7Risk: &c&nHigh", " "))
                    .build()
            ).withListener(event -> {
                main.getBlackjackPlayer(player.getDisplayName()).getStorage().putAll(Utils.getRandomCard());
                openBlackJack(player);
            }));

        }
        DecimalFormat df = new DecimalFormat("#.##");
        int value = 0;
        for (Map.Entry<Card, CardType> entry : main.getBlackjackPlayer(player.getDisplayName()).getStorage().entries()) {
            value += entry.getKey().getValue();
        }
        menu.setButton(8, new SGButton(new ItemBuilder(Material.KNOWLEDGE_BOOK)
            .name("&fStatistics")
            .lore(Arrays.asList(" ", "&7Chance of Busting: %"+df.format(main.getUtils().bustChance(main.getBlackjackPlayer(player.getDisplayName())) * 100)
                    , "&7Current Value: "+value))
        .get()));
        menu.setButton(6, new SGButton(new ItemBuilder(Material.ANVIL)
            .name("&7Hold")
            .lore(Arrays.asList(" ", "&7You hold when you are", "&7done with drawing cards", " "))
        .get()).withListener(event -> {
            for (BlackjackPlayer blackjackPlayer : main.getBlackjackPlayers()) {
                if (blackjackPlayer.getName().equalsIgnoreCase(player.getDisplayName())) continue;

                int bjpValue = 0;
                for (Map.Entry<Card, CardType> entry : blackjackPlayer.getStorage().entries()) {
                    bjpValue += entry.getKey().getValue();
                }
                if (bjpValue <= 12){
                    blackjackPlayer.getStorage().putAll(Utils.getRandomCard());
                }
            }


            HashMap<String, Integer> hashMap = new HashMap<>();
            for (BlackjackPlayer blackjackPlayer : main.getBlackjackPlayers()) {
                int bjpValue = 0;
                for (Map.Entry<Card, CardType> entry : blackjackPlayer.getStorage().entries()) {
                    bjpValue += entry.getKey().getValue();
                }
                hashMap.put(blackjackPlayer.getName(), bjpValue);
            }
            Map.Entry<String, Integer> maxEntry = null;

            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    if (entry.getValue() > 21) continue;
                    maxEntry = entry;
                }
            }
            if (hashMap.get(player.getDisplayName()) > 21){
                player.sendMessage(ChatColor.RED+"You busted! You went over 21.");
            }
            if (maxEntry == null){
                Bukkit.broadcastMessage(ChatColor.RED+"Nobody won! Everyone busted.");
            } else {
                Bukkit.broadcastMessage(ChatColor.GREEN+""+ChatColor.BOLD+"WINNER! "+ChatColor.WHITE+ChatColor.stripColor(maxEntry.getKey())+" has won with a hand worth "+maxEntry.getValue());
            }
            main.isGameRunning = false;
            player.closeInventory();
        }));
        player.openInventory(menu.getInventory());
    }
}
