package com.gb6.tutorials.objects.objectives;

import com.gb6.tutorials.objects.TutorialPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static com.gb6.tutorials.utils.Constants.ACTIVE_PLAYERS;

public class BaseObjective implements Listener {
    @Getter private Consumer<Player> actionConsumer;
    @Getter private BiPredicate<Player, BaseObjective> predicate = (player, objective) -> {
        if (!ACTIVE_PLAYERS.containsKey(player.getUniqueId())) {
            return false;
        }

        TutorialPlayer tutorialPlayer = ACTIVE_PLAYERS.get(player.getUniqueId());

        if (!tutorialPlayer.getActiveObjective().equals(objective)) {
            return false;
        }

        return true;
    };
    @Getter @Setter private List<String> strings;

    public BaseObjective(Consumer<Player> action, List<String> strings) {
        this.actionConsumer = action;
        this.strings = strings;
    }

    public void next(TutorialPlayer tutorialPlayer, BaseObjective current) {
        int id = tutorialPlayer.getActiveTutorial().getObjectiveMap().inverse().get(current);

        if (!tutorialPlayer.getActiveTutorial().getObjectiveMap().containsKey(id + 1)) {
            tutorialPlayer.end(tutorialPlayer.getActiveTutorial());
            ACTIVE_PLAYERS.remove(tutorialPlayer.getPlayer().getUniqueId());
        } else {
            //System.out.println("Active tutorial set to: " + tutorialPlayer.getActiveTutorial().getObjectiveMap().get(id + 1).toString());
            tutorialPlayer.setActiveObjective(tutorialPlayer.getActiveTutorial().getObjectiveMap().get(++id));
        }
    }

    public void sendStrings(Player player) {
        strings.forEach(player::sendMessage);
    }
}
