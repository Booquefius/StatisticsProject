package casper.statisticsproject.commands;

import casper.statisticsproject.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class GameTabCompleter implements TabCompleter {
    private final Main main;

    public GameTabCompleter(Main main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.isOp()) {
            switch (args.length){
                case 1:
                    return List.of("join", "help", "admin", "debug");
                case 2: {
                    if (args[0].equalsIgnoreCase("admin")){
                        return List.of("start", "kick", "addbal", "addplayer");
                    }
                    if (args[0].equalsIgnoreCase("debug"))
                        return List.of("openbj");
                }
            }
        } else {
            switch (args.length){
                case 1:
                    return List.of("join", "help");
            }
        }

        return null;
    }
}
