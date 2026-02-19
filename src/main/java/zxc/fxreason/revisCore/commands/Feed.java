package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 19.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Feed implements CommandExecutor {

    private ConfigManager configManager;

    public Feed(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (sender.hasPermission("reviscore.feed")) {
            if (args.length == 1) {
                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);

                if (!sender.hasPermission("reviscore.feed-all")) {
                    if (!targetName.equals(sender.getName()) && !sender.isOp()) {
                        sender.sendMessage(configManager.getFeedOnlyYou());
                        return true;
                    }
                }

                if (target != null) {
                    target.setFoodLevel(20);
                    target.setSaturation(20.0f);
                    sender.sendMessage(configManager.getFeedSuccess());
                    return true;
                } else {
                    sender.sendMessage(configManager.getNicknameNotFound());
                }
            } else {
                sender.sendMessage(configManager.getFeedUsage());
                return true;
            }
        } else {
            sender.sendMessage(configManager.getNoPerms());
            return true;
        }
        return true;
    }
}
