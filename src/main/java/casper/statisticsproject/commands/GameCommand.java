package casper.statisticsproject.commands;

import casper.statisticsproject.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class GameCommand implements CommandExecutor {
    private final Main main;

    public GameCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //VALIDATION
        if (!(sender instanceof Player)) return true;
        if (args.length == 0) return true;

        Player player = (Player) sender;

        /* Command Map
        *
        * /game
        *   -> join
        *   -> help
        *   -> admin
        *       -> addplayer <player>
        *       -> start
        *       -> kick <player>
        *       -> setbal <player> <amount>
        * */


        // COMMAND ITSELF
        switch (args.length){
            case 1: {
                if (args[0].equalsIgnoreCase("join")){
                    main.getMenus().openSelection((player));
                    return true;
                } else {
                    sendHelp(player);
                }
            }
            case 2:{
                if (args[0].equalsIgnoreCase("admin") && player.isOp()){
                    switch (args[1].toUpperCase(Locale.ROOT)){
                        case "start": {
                            //TODO
                            // START THE GAME COMMAND
                        }
                        case "kick": {
                            main.getUtils().sendUsage(player, "/game admin kick <player>");
                            break;
                        }
                        case "setbal": {
                            main.getUtils().sendUsage(player, "/game admin setbal <player> <amount>");
                            break;
                        }
                        case "addplayer": {
                            main.getUtils().sendUsage(player, "/game admin addplayer <player>");
                            break;
                        }
                    }
                } else {
                    sendHelp(player);
                }
            }
        }
        return true;
    }

    private void sendHelp(Player player){
        player.sendMessage(ChatColor.GRAY+""+ChatColor.STRIKETHROUGH+"                                      ");
        player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"BLACKJACK COMMANDS");
        player.sendMessage(ChatColor.GRAY+"/game help");
        player.sendMessage(ChatColor.GRAY+"/game join");

        //ADMIN COMMANDS
        if (player.isOp()){
            player.sendMessage(" ");
            player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"BLACKJACK ADMIN COMMANDS");
            player.sendMessage(ChatColor.GRAY+"/game admin start");
            player.sendMessage(ChatColor.GRAY+"/game admin kick <player>");
            player.sendMessage(ChatColor.GRAY+"/game admin addplayer <player>");
            player.sendMessage(ChatColor.GRAY+"/game admin setbal <player> <amount>");
        }
        player.sendMessage(ChatColor.GRAY+""+ChatColor.STRIKETHROUGH+"                                      ");
    }

}