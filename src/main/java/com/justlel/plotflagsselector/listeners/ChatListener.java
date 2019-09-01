package com.justlel.plotflagsselector.listeners;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.commands.FlagsCommand;
import com.justlel.plotflagsselector.iomanager.IOManager;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener {

    private Main main;

    /**
     * Constructor of the ChatListener class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    public ChatListener(Main main) {
        this.main = main;
    }

    /**
     * Method executed every time a player writes in chat.
     *
     * @param e The {@link AsyncPlayerChatEvent event} fired.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (IOManager.isPlayerWaiting(e.getPlayer())) {
            FlagsCommand.SUB_CATEGORIES category = IOManager.getPlayerWaitingCategory(e.getPlayer());
            if (category == null)
                return;
            if (!validateMessage(category, e.getMessage())) {
                e.getPlayer().sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.INVALID_FLAG_PARAMETER));
            } else {
                e.getPlayer().performCommand("plot flag set " + category + " " + e.getMessage());
                IOManager.removePlayerWaiting(e.getPlayer());
            }
            e.setCancelled(true);
        }
    }

    /**
     *
     * @param category The {@link com.justlel.plotflagsselector.commands.FlagsCommand.SUB_CATEGORIES Sub-Category} of the flag selected.
     * @param message The message sent by the player
     * @return True if the message is valid, false otherwise.
     */
    private boolean validateMessage(FlagsCommand.SUB_CATEGORIES category, String message) {
        if (category == FlagsCommand.SUB_CATEGORIES.FEED || category == FlagsCommand.SUB_CATEGORIES.HEAL) {
            try {
                Integer.parseInt(message);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
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
