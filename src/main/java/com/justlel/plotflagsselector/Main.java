package com.justlel.plotflagsselector;

import com.justlel.plotflagsselector.commands.FlagsCommand;
import com.justlel.plotflagsselector.listeners.ChatListener;
import com.justlel.plotflagsselector.listeners.GuiListener;
import com.justlel.plotflagsselector.listeners.GuiOpenerListener;
import com.justlel.plotflagsselector.listeners.PlayerJoinListener;
import com.justlel.plotflagsselector.yaml.ChatComponentYaml;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import com.justlel.plotflagsselector.yaml.SelectionGuiYaml;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private SelectionGuiYaml selectionGuiConfiguration;
    private ChatComponentYaml chatConfiguration;
    private LanguageYaml languageConfiguration;


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new GuiOpenerListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getCommand("flags").setExecutor(new FlagsCommand(this));
        loadConfigurations();
        getServer().getConsoleSender().sendMessage(getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.PLUGIN_ENABLED));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.PLUGIN_DISABLED));
    }

    public SelectionGuiYaml getSelectionGuiConfiguration() {
        return selectionGuiConfiguration;
    }

    public ChatComponentYaml getChatConfiguration() {
        return chatConfiguration;
    }

    public LanguageYaml getLanguageConfiguration() {
        return languageConfiguration;
    }

    public void loadConfigurations() {
        try {
            this.selectionGuiConfiguration = new SelectionGuiYaml(this);
            this.chatConfiguration = new ChatComponentYaml(this);
            this.languageConfiguration = new LanguageYaml(this);
        } catch (Exception e) {
            e.printStackTrace();
            setEnabled(false);
        }
    }
}
