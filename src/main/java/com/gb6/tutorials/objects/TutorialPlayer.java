package com.gb6.tutorials.objects;

import com.gb6.tutorials.objects.objectives.BaseObjective;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TutorialPlayer {

    @Getter private BaseObjective activeObjective;
    @Getter @Setter private Player player;
    @Getter @Setter private TutorialObject activeTutorial;

    public TutorialPlayer(Player player, TutorialObject activeTutorial) {
        this.player = player;
        this.activeTutorial = activeTutorial;
    }

    public void end(TutorialObject tutorialObject) {
        player.teleport(tutorialObject.getReturnPoint());
        player.sendMessage(ChatColor.RED + "Tutorial ended");
    }

    public void start(TutorialObject tutorialObject) {
        player.teleport(tutorialObject.getSpawnPoint());
        player.sendMessage(ChatColor.GREEN + "Tutorial started");
    }

    public void setActiveObjective(BaseObjective activeObjective) {
        this.activeObjective = activeObjective;
        activeObjective.sendStrings(player);
    }
}
