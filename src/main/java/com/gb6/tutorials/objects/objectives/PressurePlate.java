package com.gb6.tutorials.objects.objectives;

import com.gb6.tutorials.objects.TutorialPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.function.Consumer;

import static com.gb6.tutorials.utils.Constants.ACTIVE_PLAYERS;
import static com.gb6.tutorials.utils.Constants.INSTANCE;

public class PressurePlate extends BaseObjective {
    private Block block;

    public PressurePlate(Consumer<Player> action, Block block, List<String> strings) {
        super(action, strings);
        this.block = block;
        Bukkit.getPluginManager().registerEvents(this, INSTANCE);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

        if (!event.getClickedBlock().equals(block)) {
            return;
        }

        if (!getPredicate().test(event.getPlayer(), this)) {
            return;
        }

        TutorialPlayer tutorialPlayer = ACTIVE_PLAYERS.get(event.getPlayer().getUniqueId());

        if (event.getClickedBlock().equals(block)) {
            getActionConsumer().accept(event.getPlayer());
            next(tutorialPlayer, this);
        }
    }

}
