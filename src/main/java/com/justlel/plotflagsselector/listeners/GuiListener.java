package com.justlel.plotflagsselector.listeners;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.guis.SelectionGui;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class GuiListener implements Listener {

    private Main main;

    /**
     * Constructor of the GuiListener class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    public GuiListener(Main main) {
        this.main = main;
    }

    /**
     * Method executed every time a player clicks an inventory.
     *
     * @param e The {@link InventoryClickEvent event} fired.
     */
    @EventHandler
    private void onInventory(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equals(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.SELECTION_GUI_TITLE)[0]) || e.getRawSlot() == -999 || e.getCurrentItem() == null)
            return;
        try {
            for (SelectionGui.CATEGORY selection : SelectionGui.CATEGORY.values()) {
                Integer itemSelected = getMain().getSelectionGuiConfiguration().getCategoryItemSlot(selection);
                if (itemSelected == null || !itemSelected.equals(e.getRawSlot()))
                    continue;
                ((Player) e.getWhoClicked()).performCommand("flags " + selection.toString().toUpperCase());
                e.getWhoClicked().closeInventory();
                break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
