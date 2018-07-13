package com.gb6.tutorials.commands;

import com.gb6.tutorials.objects.TutorialObject;
import com.gb6.tutorials.objects.TutorialPlayer;
import com.gb6.tutorials.objects.objectives.BaseObjective;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.gb6.tutorials.utils.Constants.*;

public class TutorialCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid amount of arguments");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tutorial.start")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        if (ACTIVE_PLAYERS.containsKey(player.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "You cant use this command while being in a tutorial.");
            return true;
        }

        if (!TUTORIAL_OBJECTS.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Unknown tutorial: " + ChatColor.GRAY + args[0]);
            return true;
        }

        TutorialObject tutorialObject = TUTORIAL_OBJECTS.get(args[0].toLowerCase());
        TutorialPlayer tutorialPlayer = new TutorialPlayer(player, tutorialObject);
        BaseObjective objective = tutorialObject.getObjectiveMap().entrySet().iterator().next().getValue();

        tutorialPlayer.start(tutorialObject);
        tutorialPlayer.setActiveObjective(objective);

        ACTIVE_PLAYERS.put(player.getUniqueId(), tutorialPlayer);
        return false;
    }
}
