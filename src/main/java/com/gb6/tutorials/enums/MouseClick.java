package com.gb6.tutorials.enums;

import lombok.Getter;
import org.bukkit.event.block.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.event.block.Action.*;

/**
 * Created by Gijs on 21-3-2018.
 */
public enum MouseClick {
    LEFT_CLICK(LEFT_CLICK_AIR, LEFT_CLICK_BLOCK), RIGHT_CLICK(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK), BOTH(LEFT_CLICK_AIR, LEFT_CLICK_BLOCK, RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK);

    @Getter List<Action> actions = new ArrayList<>();

    MouseClick(Action... actions) {
        this.actions = Arrays.asList(actions);
    }
}
