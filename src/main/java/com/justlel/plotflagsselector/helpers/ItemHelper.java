package com.justlel.plotflagsselector.helpers;

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemHelper {

    /**
     * Set a custom name and a custom lore on an item.
     *
     * @param item Item on which set the name and the lore.
     * @param name The name to be set.
     * @param lore The lore to be set.
     */
    public static void setNameAndLore(@NotNull ItemStack item, @NotNull String name, @NotNull List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> convertedLore = new ArrayList<>();
        for (String row : lore) {
            convertedLore.add(ChatColor.translateAlternateColorCodes('&', row));
        }
        meta.setLore(convertedLore);
        item.setItemMeta(meta);
    }

    /**
     * Set a custom name on an item.
     *
     * @param item Item on which set the name and the lore.
     * @param name The name to be set.
     */
    public static void setName(@NotNull ItemStack item, @NotNull String name) {
        setNameAndLore(item, name, Collections.emptyList());
    }

    /**
     * Set a custom lore on an item.
     *
     * @param item Item on which set the name and the lore.
     * @param lore The lore to be set.
     */
    public static void setLore(@NotNull ItemStack item, @NotNull List<String> lore) {
        setNameAndLore(item, "", lore);
    }
}
