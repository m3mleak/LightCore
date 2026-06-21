package zxc.fxreason.lightCore.commands;

/*
 * By fxreason
 * 21.02.2026
 **/

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Workbench implements CommandExecutor {

    private final LightCore plugin;

    public Workbench(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (!player.hasPermission("reviscore.workbench")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length == 0) {
            player.openWorkbench(null, true);
        } else {
            player.sendMessage(plugin.getConfigManager().getWbUsage());
        }

        return true;
    }

}
