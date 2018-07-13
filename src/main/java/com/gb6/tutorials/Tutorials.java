package com.gb6.tutorials;

import com.gb6.tutorials.commands.TutorialCommand;
import com.gb6.tutorials.utils.Configuration;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tutorials extends JavaPlugin {

    @Getter private static Tutorials instance;
    @Getter private static Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        configuration = new Configuration();

        configuration.loadTutorials();
        getCommand("tutorial").setExecutor(new TutorialCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
