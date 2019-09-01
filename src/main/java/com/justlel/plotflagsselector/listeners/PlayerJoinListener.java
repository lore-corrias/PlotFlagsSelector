package com.justlel.plotflagsselector.listeners;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.helpers.ItemOpener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main main;

    /**
     * Constructor of the PlayerJoinListener class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    /**
     * Method executed every time a player joins.
     *
     * @param e The {@link PlayerJoinEvent event} fired.
     */
    @EventHandler
    public void onConnection(PlayerJoinEvent e) {
        if (e.getPlayer().getInventory().getItem(8) == null)
            e.getPlayer().getInventory().setItem(8, ItemOpener.getItemOpener(getMain()));
    }

    public Main getMain() {
        return main;
    }
}
