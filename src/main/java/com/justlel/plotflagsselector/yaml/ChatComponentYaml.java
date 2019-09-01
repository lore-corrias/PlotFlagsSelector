package com.justlel.plotflagsselector.yaml;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.guis.SelectionGui;
import com.justlel.plotflagsselector.pattern.ChatComponentPatternManager;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ChatComponentYaml extends YamlLoader {

    private ChatComponentPatternManager chatComponentPatternManager;

    /**
     * Constructor of the ChatComponentYaml class.
     *
     * @param main Instance of the {@link Main Main} class.
     * @throws IOException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws IllegalArgumentException {@link YamlLoader#YamlLoader(Main) inherited.}
     * @throws InvalidConfigurationException {@link YamlLoader#YamlLoader(Main) inherited.}
     */
    public ChatComponentYaml(Main main) throws IOException, IllegalArgumentException, InvalidConfigurationException {
        super(main);
        chatComponentPatternManager = new ChatComponentPatternManager();
        loadYaml("chat.yml");
    }

    /**
     * Obtain a list of string with the messages to be sent to any player which wants
     * to change its flags.
     *
     * @param category The {@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY category} from which the text is from.
     * @return The chat text from the configuration.
     */
    public List<String> getChatTextRaw(SelectionGui.CATEGORY category) {
        if (isList(category + ".text")) {
            return getStringList(category + ".text");
        } else if (isString(category + ".text")) {
            return Collections.singletonList(getString(category + ".text"));
        } else {
            return null;
        }
    }

    /**
     * Returns an instance of the {@link ChatComponentPatternManager text parser}
     *
     * @return The chat text parser.
     */
    public ChatComponentPatternManager getChatTextParser() {
        return chatComponentPatternManager;
    }
}
