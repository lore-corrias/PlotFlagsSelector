package com.justlel.plotflagsselector.chat;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.guis.SelectionGui;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ChatComponentManager {


    private SelectionGui.CATEGORY category;
    private Player player;
    private Main main;

    /**
     * Constructor of the ChatComponentManage class.
     *
     * @param category The {@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY category} from which obtain the messages to be sent.
     * @param player The {@link Player Player} to send the messages.
     * @param main Instance of the {@link Main Main} class.
     */
    public ChatComponentManager(SelectionGui.CATEGORY category, Player player, Main main) {
        this.main = main;
        this.category = category;
        this.player = player;
    }

    /**
     * Send the text defined by the category to the player
     * set from the constructor.
     */
    public void sendParsedText() {
        for (String raw : getMain().getChatConfiguration().getChatTextRaw(category)) {
            TextComponent textComponent = getMain().getChatConfiguration().getChatTextParser().parseString(raw);
            player.spigot().sendMessage(textComponent);
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
}
