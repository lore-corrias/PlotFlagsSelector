package com.justlel.plotflagsselector.helpers;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ItemOpener {

    /**
     * Static method used to obtain the "gui opener" item
     * and compare it to the one currently in a player's hand.
     *
     * @param main Instance of the Main class.
     * @return The item used to open the selection GUI.
     */
    public static ItemStack getItemOpener(Main main) {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        String title = main.getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.GUI_OPENER_TITLE)[0];
        String[] lore = main.getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.GUI_OPENER_LORE);
        ItemHelper.setNameAndLore(item, title, Arrays.asList(lore));
        return item;
    }
}
