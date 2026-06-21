package zxc.fxreason.lightCore.commands;

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
import zxc.fxreason.lightCore.LightCore;

public class Night implements CommandExecutor {

    private final LightCore plugin;

    public Night(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("reviscore.night")) {
            if (args.length == 0) {
                World world = player.getWorld();
                world.setTime(13000);
                player.sendMessage(plugin.getConfigManager().getSetNight());

                return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getNightUsage());

                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());

            return true;
        }
    }
}