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
import zxc.fxreason.revisCore.RevisCore;

public class Rain implements CommandExecutor {

    private final RevisCore plugin;

    public Rain(RevisCore plugin) {
        this.plugin = plugin;
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
                player.sendMessage(plugin.getConfigManager().getSetRain());

                return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getRainUsage());

                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());

            return true;
        }
    }
}