package com.justlel.plotflagsselector.pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatComponentPatternManager {

    private static final Pattern COMPLETE_PATTERN = Pattern.compile("\\(([^\\(\\)]*?)\\)\\[([^\\[\\]]*?)]", Pattern.CASE_INSENSITIVE);

    /**
     * This method is supposed to be called recursively
     * in order to obtain each sub-version of a String
     * which has to be parsed.
     *
     * @param string The string to be parsed.
     * @return A parsed string.
     */
    public TextComponent parseString(String string) {
        if(string == null || string.isEmpty()) {
            return new TextComponent();
        }
        string = ChatColor.translateAlternateColorCodes('&', string);
        TextComponent finalComponent = new TextComponent();

        Matcher stringMatcher = COMPLETE_PATTERN.matcher(string);
        if(stringMatcher.find()) { // the string contains some syntax which has to be parsed
            String groupMatched = stringMatcher.group(); // the string to be parsed
            if(groupMatched.equalsIgnoreCase(string)) {
                finalComponent.addExtra(parseCommandAndText(groupMatched)); // adding just the string parsed
            } else {
                finalComponent.addExtra(parseString(string.substring(0, string.indexOf(groupMatched)))); // parsing and adding the first part of the string
                finalComponent.addExtra(parseCommandAndText(groupMatched)); // adding the parsed group caught
                finalComponent.addExtra(parseString(StringUtils.substringAfterLast(string, groupMatched))); // parsing and adding the remaining text
            }
        } else {
            finalComponent.addExtra(string);
        }
        return finalComponent;
    }

    /**
     * Convert a raw group, generally caught by the method
     * {@link ChatComponentPatternManager#parseString(String)}
     * to its TextComponent with the clickable command.
     *
     * @param group Group to be parsed caught by a regex.
     * @return The TextComponent parsed from the string.
     */
    private TextComponent parseCommandAndText(String group) {
        if(group == null || group.isEmpty())
            return null;
        Matcher matcher = COMPLETE_PATTERN.matcher(group);
        TextComponent finalComponent = new TextComponent();
        if(matcher.matches()) { // just in case the group is a fake positive :<
            String text = matcher.group(1); // text inside the "()", text to be displayed
            String command = matcher.group(2); // text inside the "[]", command to be executed on click
            finalComponent.setText(ChatColor.translateAlternateColorCodes('&', text));
            finalComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command.startsWith("/") ? command : "/" + command));
        }
        return finalComponent;
    }
}
