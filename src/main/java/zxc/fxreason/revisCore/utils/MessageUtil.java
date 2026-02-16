package zxc.fxreason.revisCore.utils;

/*
 * By fxreason
 * 16.02.2026
 **/

import org.bukkit.entity.Player;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class MessageUtil {
    private ConfigManager configManager;

    public MessageUtil(ConfigManager configManager) {
        this.configManager = configManager;
    }

    // for players
    public void sendMessage(Player player, String playerName, String message) {
        if (message != null && !message.isEmpty()) {
            message = message.replace("%player%", playerName);
            player.sendMessage(message);
        } else {
            player.sendMessage(configManager.getNotMessage());
        }
    }
}
