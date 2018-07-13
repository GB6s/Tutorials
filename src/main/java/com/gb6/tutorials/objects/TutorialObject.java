package com.gb6.tutorials.objects;

import com.gb6.tutorials.objects.objectives.BaseObjective;
import com.google.common.collect.BiMap;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TutorialObject {
    @Getter private String identifier;
    @Getter private Location spawnPoint;
    @Getter private Location returnPoint;
    @Getter private BiMap<Integer, BaseObjective> objectiveMap;

    public TutorialObject(String identifier, Location spawnPoint, Location returnPoint, BiMap<Integer, BaseObjective> objectiveMap) {
        this.identifier = identifier;
        this.spawnPoint = spawnPoint;
        this.returnPoint = returnPoint;
        this.objectiveMap = objectiveMap;
    }
}
