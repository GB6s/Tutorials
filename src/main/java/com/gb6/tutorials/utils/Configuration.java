package com.gb6.tutorials.utils;

import com.gb6.tutorials.enums.MouseClick;
import com.gb6.tutorials.objects.TutorialObject;
import com.gb6.tutorials.objects.objectives.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.gb6.tutorials.enums.MouseClick.BOTH;
import static com.gb6.tutorials.utils.Constants.INSTANCE;
import static com.gb6.tutorials.utils.Constants.TUTORIAL_OBJECTS;

public class Configuration {
    private static FileConfiguration config;

    public Configuration() {
        config = INSTANCE.getConfig();
    }

    public void loadTutorials() {
        TUTORIAL_OBJECTS.clear();
        ConfigurationSection section = config.getConfigurationSection("tutorials");

        for (String key : section.getKeys(false)) {
            ConfigurationSection tutorial = section.getConfigurationSection(key);
            ConfigurationSection objectives = tutorial.getConfigurationSection("objectives");
            Location spawn = deserializeLocation(tutorial.getString("spawn-point"));
            Location returnPoint = deserializeLocation(tutorial.getString("return-point"));
            BiMap<Integer, BaseObjective> objectiveMap = HashBiMap.create();


            outer:
            for (String key2 : objectives.getKeys(false)) {
                for (String key3 : objectives.getConfigurationSection(key2).getKeys(false)) {
                    System.out.println(key3);

                    int id = Integer.parseInt(key2);
                    ConfigurationSection objective = objectives.getConfigurationSection(key2 + "." + key3);

                    Consumer<Player> consumer = (player) -> {
                        objective.getStringList("action.commands").forEach(s -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", player.getName())));
                        if (!objective.getString("action.sound").isEmpty()) {
                            player.playSound(player.getLocation(), Sound.valueOf(objective.getString("action.sound").toUpperCase()), 10F, 1F);
                        }
                    };

                    Location location = null;
                    MouseClick click = BOTH;
                    List<String> strings = new ArrayList<>();

                    if (objective.contains("location")) {
                        location = deserializeLocation(objective.getString("location"));
                    }

                    if (objective.contains("click-type")) {
                        click = MouseClick.valueOf(objective.getString("click-type").toUpperCase());
                    }

                    if (objective.contains("text")) {
                        strings = objective.getStringList("text").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
                    }

                    switch (key3.toLowerCase()) {
                        case "block-click":
                            if (location == null) {
                                continue outer;
                            }
                            objectiveMap.put(id, new BlockClick(consumer, click, location.getBlock(), strings));
                            break;
                        case "item-click":
                            ItemStack itemStack = getItemFromConfig(objective.getConfigurationSection("item-stack"));

                            objectiveMap.put(id, new ItemClick(consumer, click, itemStack, strings));
                            break;
                        case "npc-click":
                            if (!objective.contains("id")) {
                                continue outer;
                            }

                            int identifier = objective.getInt("id");

                            objectiveMap.put(id, new NPCClick(consumer, identifier, strings));
                            break;
                        case "pressure-plate":
                            if (location == null) {
                                continue outer;
                            }
                            objectiveMap.put(id, new PressurePlate(consumer, location.getBlock(), strings));
                            break;
                        case "text-click":

                            break;
                    }

                    TUTORIAL_OBJECTS.put(key, new TutorialObject(key, spawn, returnPoint, objectiveMap));
                }
            }
        }
    }

    private Location deserializeLocation(String string) {
        String[] split = string.split("/");
        return new Location(Bukkit.getWorld(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }

    private ItemStack getItemFromConfig(ConfigurationSection section) {
        String displayName = ChatColor.translateAlternateColorCodes('&', section.getString("display-name"));
        Material material = Material.valueOf(section.getString("material").toUpperCase());
        List<String> lore = section.getStringList("lore").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());

        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
