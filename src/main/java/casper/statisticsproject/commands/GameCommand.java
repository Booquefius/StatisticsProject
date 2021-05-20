package casper.statisticsproject.commands;

import casper.statisticsproject.Main;
import casper.statisticsproject.objects.BlackjackPlayer;
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
        *       -> addbal <player> <amount>
        * */


        // COMMAND ITSELF
        switch (args.length){
            case 1: {
                if (args[0].equalsIgnoreCase("join")){
                    main.getMenus().openSelection((player));
                    return true;
                } else {
                    sendHelp(player);
                    return true;
                }
            }
            case 2:{
                if (args[0].equalsIgnoreCase("admin") && player.isOp()){
                    switch (args[1].toLowerCase(Locale.ROOT)){
                        case "start": {
                            //TODO
                            // START THE GAME COMMAND
                            return true;
                        }
                        case "kick": {
                            main.getUtils().sendUsage(player, "/game admin kick <player>");
                            return true;
                        }
                        case "addbal": {
                            main.getUtils().sendUsage(player, "/game admin addbal <player> <amount>");
                            return true;
                        }
                        case "addplayer": {
                            main.getUtils().sendUsage(player, "/game admin addplayer <player>");
                            return true;
                        }
                    }
                    return true;
                } else {
                    sendHelp(player);
                    return true;
                }
            }
            case 3: {
                switch (args[1].toLowerCase(Locale.ROOT)){
                    case "start": {
                        sendHelp(player);
                    }
                    case "kick": {
                        if (main.getBlackjackPlayer(args[2]) == null){
                            player.sendMessage(ChatColor.RED+"The user '"+args[2]+"' does not exist.");
                            player.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SUCCESS! "+ChatColor.WHITE+"You have removed "+args[2]+ " from the game.");
                            return true;
                        }
                        main.removeBlackjackPlayer(main.getBlackjackPlayer(args[2]));
                    }
                    case "addbal": {
                        main.getUtils().sendUsage(player, "/game admin addbal <player> <amount>");
                        return true;
                    }
                    case "addplayer": {
                        if (main.getBlackjackPlayer(args[2]) != null){
                            player.sendMessage(ChatColor.RED+"The user '"+args[2]+"' already exists.");
                            return true;
                        }
                        main.addBlackjackPlayer(new BlackjackPlayer(args[2]));
                        player.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SUCCESS! "+ChatColor.WHITE+"You have added "+args[2]+ " to the game.");
                    }
                }
                return true;
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
            player.sendMessage(ChatColor.GRAY+"/game admin addbal <player> <amount>");
        }
        player.sendMessage(ChatColor.GRAY+""+ChatColor.STRIKETHROUGH+"                                      ");
    }

}