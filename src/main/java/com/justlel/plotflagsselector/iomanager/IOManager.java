package com.justlel.plotflagsselector.iomanager;

import com.justlel.plotflagsselector.commands.FlagsCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class IOManager {

    private static HashMap<Player, FlagsCommand.SUB_CATEGORIES> currentWaiting = new HashMap<>();

    /**
     * See whether the player is going to write the value of a flag or not
     *
     * @param player {@link Player Player} to be checked.
     * @return Whether the player is going to write the value of a flag or not.
     */
    public static boolean isPlayerWaiting(Player player) {
        return currentWaiting.containsKey(player);
    }

    /**
     *
     * @param player {@link Player Player} to be added.
     * @param category The {@link com.justlel.plotflagsselector.commands.FlagsCommand.SUB_CATEGORIES Sub-Category} to which the player will be linked.
     */
    public static void addWaitingPlayer(Player player, FlagsCommand.SUB_CATEGORIES category) {
        currentWaiting.putIfAbsent(player, category);
    }

    /**
     *
     * @param player {@link Player Player} to be removed.
     */
    public static void removePlayerWaiting(Player player) {
        currentWaiting.remove(player);
    }

    /**
     *
     * @param player {@link Player Player} to be checked.
     * @return The {@link com.justlel.plotflagsselector.commands.FlagsCommand.SUB_CATEGORIES Sub-Category} to which the player will be linked.
     * @return null If the Player is not found.
     */
    public static FlagsCommand.SUB_CATEGORIES getPlayerWaitingCategory(Player player) {
        return currentWaiting.getOrDefault(player, null);
    }
}
