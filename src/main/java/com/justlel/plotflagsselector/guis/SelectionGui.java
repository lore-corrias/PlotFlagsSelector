package com.justlel.plotflagsselector.guis;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SelectionGui {

    private Player opener;
    private Inventory gui;
    private Main main;

    /**
     * Constructor of the SelectionGui class.
     *
     * @param opener {@link Player Opener} of the menu.
     * @param main Instance of the {@link Main Main} class.
     */
    public SelectionGui(Player opener, Main main) {
        this.main = main;
        this.opener = opener;
        createGui();
    }

    /**
     * Create a GUI 3*3 sized with the title specified in the configuration.
     */
    private void createGui() {
        gui = Bukkit.createInventory(null, 27, getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.SELECTION_GUI_TITLE)[0]);
        loadGuiCorner();
        loadGuiSelectors();
    }

    /**
     * Load the GUI corner, made of silver glass pane.
     */
    @SuppressWarnings("deprecation")
    private void loadGuiCorner() {
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.SILVER.getDyeData());
        List<Integer> top = IntStream.rangeClosed(0, 7).boxed().collect(Collectors.toList());
        List<Integer> bottom = IntStream.rangeClosed(18, 26).boxed().collect(Collectors.toList());
        List<Integer> left = IntStream.iterate(0, i -> i + 9).limit(3).boxed().collect(Collectors.toList());
        List<Integer> right = IntStream.iterate(8, i -> i + 9).limit(3).boxed().collect(Collectors.toList());
        List<Integer> blocks = Stream.of(top, bottom, left, right).flatMap(Collection::stream).collect(Collectors.toList());
        for (Integer index : blocks) {
            gui.setItem(index, glass);
        }
    }

    /**
     * Set all the items used to select
     * a category of flags.
     */
    private void loadGuiSelectors() {
        for (CATEGORY category : CATEGORY.values()) {
            ItemStack categoryItem = getMain().getSelectionGuiConfiguration().getCategoryItem(category);
            Integer categoryItemSlot = getMain().getSelectionGuiConfiguration().getCategoryItemSlot(category);
            if (categoryItem == null || categoryItemSlot == null)
                return;
            gui.setItem(categoryItemSlot, categoryItem);
        }
    }

    /**
     * Get an instance of the {@link Main Main} class.
     *
     * @return Instance of the {@link Main Main} class.
     */
    public Main getMain() {
        return main;
    }

    /**
     * Shows the {@link Inventory GUI} to the specified Player.
     */
    public void openGui() {
        opener.openInventory(gui);
    }

    /**
     * All of the categories of flags.
     */
    public enum CATEGORY {
        HEALTH,
        GAMEMODE,
        PLOT_DESCRIPTION,
        WEATHER,
        TELEPORT,
        BOOLEAN;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
