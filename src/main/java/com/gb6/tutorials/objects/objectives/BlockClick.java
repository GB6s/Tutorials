package com.gb6.tutorials.objects.objectives;

import com.gb6.tutorials.enums.MouseClick;
import com.gb6.tutorials.objects.TutorialPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.function.Consumer;

import static com.gb6.tutorials.utils.Constants.*;

public class BlockClick extends BaseObjective {
    private Block block;
    private MouseClick click;

    public BlockClick(Consumer<Player> action, MouseClick click, Block block, List<String> strings) {
        super(action, strings);
        this.click = click;
        this.block = block;
        Bukkit.getPluginManager().registerEvents(this, INSTANCE);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!click.getActions().contains(event.getAction())) {
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
