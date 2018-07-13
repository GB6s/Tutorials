package com.gb6.tutorials.objects.objectives;

import com.gb6.tutorials.objects.TutorialPlayer;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.List;
import java.util.function.Consumer;

import static com.gb6.tutorials.utils.Constants.ACTIVE_PLAYERS;
import static com.gb6.tutorials.utils.Constants.INSTANCE;

public class NPCClick extends BaseObjective {
    private int id;

    public NPCClick(Consumer<Player> action, int id, List<String> strings) {
        super(action, strings);
        this.id = id;
        Bukkit.getPluginManager().registerEvents(this, INSTANCE);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onNPCInteract(NPCRightClickEvent event) {
        if (event.getNPC().getId() != id) {
            return;
        }

        if (!getPredicate().test(event.getClicker(), this)) {
            return;
        }

        TutorialPlayer tutorialPlayer = ACTIVE_PLAYERS.get(event.getClicker().getUniqueId());

        getActionConsumer().accept(event.getClicker());
        next(tutorialPlayer, this);
    }
}
