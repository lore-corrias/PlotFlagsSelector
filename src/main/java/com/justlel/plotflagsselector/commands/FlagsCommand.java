package com.justlel.plotflagsselector.commands;

import com.justlel.plotflagsselector.Main;
import com.justlel.plotflagsselector.chat.ChatComponentManager;
import com.justlel.plotflagsselector.guis.SelectionGui;
import com.justlel.plotflagsselector.iomanager.IOManager;
import com.justlel.plotflagsselector.yaml.LanguageYaml;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class FlagsCommand implements CommandExecutor {

    /**
     * Sub-Categories of the "health" and "plot_description"{@link com.justlel.plotflagsselector.guis.SelectionGui.CATEGORY category}
     */
    public enum SUB_CATEGORIES {
        FEED,
        HEAL,
        GREETING,
        DESCRIPTION;

        @Override
        public String toString() {
            return super.toString().toUpperCase();
        }
    }

    private Main main;

    /**
     * Constructor of the FlagsCommand class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    public FlagsCommand(Main main) {
        this.main = main;
    }

    /**
     * Handles all the different commands of the plugin.
     * Depending on the arguments, this method fires any of
     * the other defined below.
     *
     * @param sender The {@link CommandSender sender} of the command.
     * @param command The command itself.
     * @param label The first world of the command.
     * @param args The arguments of the command
     * @return true
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.IN_GAME_COMMAND));
            return true;
        }
        Player player = (Player) sender;
        try {
            switch (args.length) {
                case 0:
                    guiOpenCommand(player);
                    return true;
                case 1:
                    if (args[0].equalsIgnoreCase("reload")) {
                        reloadCommand(player);
                        return true;
                    }
                    textCommand(args[0], player);
                    return true;
                case 2:
                    if (!args[0].equalsIgnoreCase("set"))
                        break;
                    SUB_CATEGORIES category = SUB_CATEGORIES.valueOf(args[1].toUpperCase());
                    flagSetCommand(category, player);
                    return true;
            }
            player.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.INVALID_SYNTAX));
        } catch (IllegalArgumentException e) {
            player.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.FLAG_CATEGORY_404));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Shows the selection gui to the player specified.
     * That's it.
     *
     * @param player Player to show the gui.
     */
    private void guiOpenCommand(Player player) {
        SelectionGui selectionGui = new SelectionGui(player, main);
        selectionGui.openGui();
    }

    /**
     * Reload the configurations, if and only if the executor
     * has enough permissions.
     *
     * @param player {@link Player Executor} of the command
     */
    private void reloadCommand(Player player) {
        Permission reloadPermission = new Permission("flags.reload");
        if (!player.hasPermission(reloadPermission) && !player.isOp()) {
            player.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.INSUFFICIENT_PERMISSIONS));
        } else {
            main.loadConfigurations();
            player.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.CONFIG_RELOADED));
        }
    }

    /**
     * Sends the text obtained from the configuration to the
     * {@link Player Player}, depending on the category specified.
     *
     * @param category Category of the text to be shown.
     * @param player {@link Player Player} to send the text.
     */
    private void textCommand(String category, Player player) {
        ChatComponentManager chatMessage = new ChatComponentManager(SelectionGui.CATEGORY.valueOf(category), player, main);
        chatMessage.sendParsedText();
    }

    /**
     * Set-up the IOManager and waits for new user input to set the flag
     * specified by the {@link SUB_CATEGORIES Sub-Category}.
     *
     * @param flagCategory {@link SUB_CATEGORIES Sub-Category} of the flag.
     * @param player {@link Player Player} to send the text.
     */
    private void flagSetCommand(SUB_CATEGORIES flagCategory, Player player) {
        IOManager.addWaitingPlayer(player, flagCategory);
        player.sendMessage(getMain().getLanguageConfiguration().getMessage(LanguageYaml.LANGUAGES_INDEX.WRITE_FLAG_PARAMETER));
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
