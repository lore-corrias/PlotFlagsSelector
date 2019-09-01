package com.justlel.plotflagsselector.listeners;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.guis.SelectionGui;
import com.justlel.plotflagsselector.helpers.ItemOpener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class GuiOpenerListener implements Listener {

    private Main main;

    /**
     * Constructor of the GuiOpenerListener class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    public GuiOpenerListener(Main main) {
        this.main = main;
    }

    /**
     * Method executed every time a player interacts with an object.
     *
     * @param e The {@link PlayerInteractEvent event} fired.
     */
    @EventHandler
    public void onItemInteract(PlayerInteractEvent e) {
        if (e.getItem() == null || !e.getItem().equals(ItemOpener.getItemOpener(getMain())))
            return;
        e.getPlayer().performCommand("flags");
        e.setCancelled(true);
    }

    /**
     * Get an instance of the {@link Main Main} class.
     *
     * @return Instance of the {@link Main Main} class.
     */
    public Main getMain() {
        return main;
    }
}
