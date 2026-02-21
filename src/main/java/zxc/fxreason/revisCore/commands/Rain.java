package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 21.02.2026
 **/

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Rain implements CommandExecutor {

    private ConfigManager configManager;

    public Rain(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("reviscore.rain")) {
            if (args.length == 0) {
                World world = player.getWorld();
                world.setStorm(true);
                world.setThundering(false);
                player.sendMessage(configManager.getSetRain());

                return true;
            } else {
                player.sendMessage(configManager.getRainUsage());

                return true;
            }
        } else {
            player.sendMessage(configManager.getNoPerms());

            return true;
        }
    }
}