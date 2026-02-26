package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 21.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Sun implements CommandExecutor {

    private final RevisCore plugin;

    public Sun(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("reviscore.sun")) {
            if (args.length == 0) {
                World world = player.getWorld();
                world.setStorm(false);
                world.setThundering(false);
                player.sendMessage(plugin.getConfigManager().getSetSun());

                return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getSunUsage());

                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());

            return true;
        }
    }
}