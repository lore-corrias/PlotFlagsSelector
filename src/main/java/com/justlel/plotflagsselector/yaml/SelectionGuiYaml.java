package com.justlel.plotflagsselector.yaml;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.guis.SelectionGui;
import com.justlel.plotflagsselector.helpers.ItemHelper;
import com.justlel.plotflagsselector.helpers.SkullHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class SelectionGuiYaml extends YamlLoader {

    /**
     * Constructor of the SelectionGuiYaml class.
     *
     * @param main Instance of the {@link Main Main} class.
     * @throws IllegalArgumentException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws IOException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws InvalidConfigurationException {@link YamlLoader#YamlLoader(Main) inherited.}
     */
    public SelectionGuiYaml(Main main) throws IllegalArgumentException, IOException, InvalidConfigurationException {
        super(main);
        loadYaml("selection_gui.yml");
    }

    /**
     * Obtain the {@link ConfigurationSection ConfigurationSection}
     * containing all the info about a category of flags.
     *
     * @param category {@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY Category} of the flag.
     * @return The category's info.
     */
    private ConfigurationSection getCategory(SelectionGui.CATEGORY category) {
        if (!isConfigurationSection(category.toString()))
            return null;
        return getConfigurationSection(category.toString());
    }

    /**
     * Obtain the item (defined inside the "selection_gui.yml")
     * representing the given category of flags.
     *
     * @param category {@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY Category} of the item.
     * @return The item of the GUI which represents that category.
     */
    final public ItemStack getCategoryItem(SelectionGui.CATEGORY category) {
        ConfigurationSection categorySection = getCategory(category);
        if (categorySection == null)
            return null;
        if (!categorySection.isString("texture") || !categorySection.isInt("slot"))
            return null;
        ItemStack skullItem = SkullHelper.getCustomTextureHead(categorySection.getString("texture"));
        if (skullItem == null)
            return null;
        if (categorySection.isString("name") && categorySection.isList("lore")) {
            ItemHelper.setNameAndLore(skullItem, categorySection.getString("name"), categorySection.getStringList("lore"));
        } else if (categorySection.isString("name")) {
            ItemHelper.setName(skullItem, categorySection.getString("name"));
        } else if (categorySection.isList("lore")) {
            ItemHelper.setLore(skullItem, categorySection.getStringList("lore"));
        }
        return skullItem;
    }

    /**
     * Similar to getCategoryItem(), but gives the slot of the item instead of the item itself.
     *
     * @param category {@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY Category} of the item.
     * @return The slot of the item inside the GUI which represents that category.
     */
    final public Integer getCategoryItemSlot(SelectionGui.CATEGORY category) {
        if (!isInt(category + ".slot"))
            return null;
        return getInt(category + ".slot");
    }
}
