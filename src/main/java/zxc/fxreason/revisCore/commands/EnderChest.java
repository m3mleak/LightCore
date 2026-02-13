package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class EnderChest implements CommandExecutor {
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
                        player.sendMessage("игрок не в сети");
                    }
                }
            } else {
                player.sendMessage("fuck");
            }
        }

        return true;
    }
}
