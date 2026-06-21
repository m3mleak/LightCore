package zxc.fxreason.lightCore.commands;

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
import zxc.fxreason.lightCore.LightCore;

public class Feed implements CommandExecutor {

    private final LightCore plugin;

    public Feed(LightCore plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (sender.hasPermission("reviscore.feed")) {
            if (args.length == 0) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("§cКоманда доступна только для игроков");
                    return true;
                }

                player.setFoodLevel(20);
                player.setSaturation(20.0f);
                player.sendMessage(plugin.getConfigManager().getFeedSuccessYou());

                return true;
            }

            if (args.length == 1) {
                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);

                if (!sender.hasPermission("reviscore.feed-all")) {
                    if (!targetName.equals(sender.getName()) && !sender.isOp()) {
                        sender.sendMessage(plugin.getConfigManager().getFeedOnlyYou());
                        return true;
                    }
                }

                if (target != null) {
                    target.setFoodLevel(20);
                    target.setSaturation(20.0f);
                    sender.sendMessage(plugin.getConfigManager().getFeedSuccess());
                    return true;
                } else {
                    sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                }
            } else {
                sender.sendMessage(plugin.getConfigManager().getFeedUsage());
                return true;
            }
        } else {
            sender.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }
        return true;
    }
}
