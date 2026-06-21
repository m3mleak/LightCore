package zxc.fxreason.lightCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class EnderChest implements CommandExecutor {

    private final LightCore plugin;

    public EnderChest(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("reviscore.enderchest-open")) {
            if (args.length == 0) {
                player.openInventory(player.getEnderChest());
            } else if (args.length == 1) {
                if (player.hasPermission("reviscore.enderchest-open-other")) {
                    String username = args[0];
                    Player target = Bukkit.getPlayer(username);
                    if (target != null) {
                        player.openInventory(target.getEnderChest());
                    } else {
                        player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                        return true;
                    }
                } else {
                    player.sendMessage(plugin.getConfigManager().getNoPerms());
                    return true;
                }
            } else {
                player.sendMessage(plugin.getConfigManager().getEcUsage());
                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }
        return true;
    }
}
