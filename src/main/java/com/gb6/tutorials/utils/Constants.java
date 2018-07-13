package com.gb6.tutorials.utils;

import com.gb6.tutorials.Tutorials;
import com.gb6.tutorials.objects.TutorialObject;
import com.gb6.tutorials.objects.TutorialPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Gijs on 18-3-2018.
 */
public interface Constants {
    Map<String, TutorialObject> TUTORIAL_OBJECTS = new HashMap<>();

    Map<UUID, TutorialPlayer> ACTIVE_PLAYERS = new HashMap<>();

    Tutorials INSTANCE = Tutorials.getInstance();
}