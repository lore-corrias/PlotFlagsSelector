package com.justlel.plotflagsselector.yaml;

import com.justlel.plotflagsselector.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageYaml extends YamlLoader {

    /**
     * List of all the messages which do not require a prefix.
     */
    private final static List<LANGUAGES_INDEX> withoutPrefix = Arrays.asList(LANGUAGES_INDEX.PREFIX,
            LANGUAGES_INDEX.GUI_OPENER_LORE, LANGUAGES_INDEX.GUI_OPENER_TITLE, LANGUAGES_INDEX.SELECTION_GUI_TITLE,
            LANGUAGES_INDEX.INSUFFICIENT_PERMISSIONS);

    /**
     * Constructor of the LanguageYaml class.
     *
     * @param main Instance of the {@link Main Main} class.
     * @throws IllegalArgumentException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws IOException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws InvalidConfigurationException {@link YamlLoader#YamlLoader(Main) inherited.}
     */
    public LanguageYaml(Main main) throws IllegalArgumentException, IOException, InvalidConfigurationException {
        super(main);
        loadYaml("language.yml");
    }

    /**
     * Get any message from the language.yml file
     * in the configurations folder.
     *
     * @param index {@link LANGUAGES_INDEX Index} of the message.
     * @return The message wanted as an array of Strings.
     */
    public String[] getMessage(LANGUAGES_INDEX index) {
        if (!isString(index.toString()) && !isList(index.toString()))
            return null;
        ArrayList<String> finalStrings = new ArrayList<>();
        if (isString(index.toString())) {
            String standard = getString(index.toString());
            if (!withoutPrefix.contains(index)) {
                finalStrings.add(ChatColor.translateAlternateColorCodes('&', addPrefix(standard)));
            } else {
                finalStrings.add(ChatColor.translateAlternateColorCodes('&', standard));
            }
        } else if (isList(index.toString())) {
            List<String> stringList = getStringList(index.toString());
            for (String row : stringList) {
                if (stringList.indexOf(row) == 0 && !withoutPrefix.contains(index)) {
                    finalStrings.add(ChatColor.translateAlternateColorCodes('&', addPrefix(row)));
                } else {
                    finalStrings.add(ChatColor.translateAlternateColorCodes('&', row));
                }
            }
        } else {
            return null;
        }
        return finalStrings.toArray(new String[0]);
    }

    /**
     * Adds the prefix defined in the
     * config to the given string
     *
     * @param string String to add the prefix.
     * @return The string with the prefix.
     */
    private String addPrefix(final String string) {
        return getString("prefix", "").concat(" ").concat(string);
    }

    /**
     * All the indexes of the messages.
     *
     * Each one corresponds to an index of the
     * language.yml file, but it's uppercase and each
     * "-" is replaced with a "_"
     */
    public enum LANGUAGES_INDEX {
        PREFIX,
        IN_GAME_COMMAND,
        FLAG_CATEGORY_404,
        INSUFFICIENT_PERMISSIONS,
        CONFIG_RELOADED,
        WRITE_FLAG_PARAMETER,
        SELECTION_GUI_TITLE,
        GUI_OPENER_TITLE,
        GUI_OPENER_LORE,
        INVALID_FLAG_PARAMETER,
        INVALID_SYNTAX,
        PLUGIN_ENABLED,
        PLUGIN_DISABLED;

        @Override
        public String toString() {
            return super.toString().replace("_", "-").toLowerCase();
        }
    }
}
