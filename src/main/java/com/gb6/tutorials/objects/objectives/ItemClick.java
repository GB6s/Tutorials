package com.gb6.tutorials.objects.objectives;

import com.gb6.tutorials.enums.MouseClick;
import com.gb6.tutorials.objects.TutorialPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gb6.tutorials.utils.Constants.ACTIVE_PLAYERS;
import static com.gb6.tutorials.utils.Constants.INSTANCE;

public class ItemClick extends BaseObjective {
    private ItemStack itemStack;
    private MouseClick click;
    private Predicate<ItemStack> predicate = stack -> {
        if (stack.getType() != itemStack.getType()) {
            return false;
        }

        if (stack.hasItemMeta() && itemStack.hasItemMeta()) {
//            printToConsole(stack);
//            printToConsole(itemStack);
            if (!stack.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {
                return false;
            }

            List<String> lore = stack.getItemMeta().getLore().stream().map(String::trim).collect(Collectors.toList());

            if (!lore.equals(itemStack.getItemMeta().getLore())) {
                return false;
            }

            return true;
        } else if (!stack.hasItemMeta() && !itemStack.hasItemMeta()) {
            return true;
        }
        return false;
    };

    public ItemClick(Consumer<Player> action, MouseClick click, ItemStack itemStack, List<String> strings) {
        super(action, strings);
        this.click = click;
        this.itemStack = itemStack;
        Bukkit.getPluginManager().registerEvents(this, INSTANCE);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!getPredicate().test(event.getPlayer(), this)) {
            return;
        }

        if (!click.getActions().contains(event.getAction())) {
            return;
        }


        if (!predicate.test(event.getPlayer().getInventory().getItemInMainHand())) {
            return;
        }

        TutorialPlayer tutorialPlayer = ACTIVE_PLAYERS.get(event.getPlayer().getUniqueId());

        getActionConsumer().accept(event.getPlayer());
        next(tutorialPlayer, this);

    }

    private void printToConsole(ItemStack itemStack) {
        System.out.println("--------------------1---------------------");
        System.out.println(itemStack.getType());
        System.out.println(itemStack.getItemMeta().getLore());
        System.out.println(itemStack.getItemMeta().getDisplayName());
        System.out.println("--------------------1---------------------");
    }
}
