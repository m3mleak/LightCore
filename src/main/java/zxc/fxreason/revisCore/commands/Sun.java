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
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Sun implements CommandExecutor {

    private ConfigManager configManager;

    public Sun(ConfigManager configManager) {
        this.configManager = configManager;
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
                player.sendMessage(configManager.getSetSun());

                return true;
            } else {
                player.sendMessage(configManager.getSunUsage());

                return true;
            }
        } else {
            player.sendMessage(configManager.getNoPerms());

            return true;
        }
    }
}