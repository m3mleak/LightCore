package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 21.02.2026
 **/

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Workbench implements CommandExecutor {

    private ConfigManager configManager;

    public Workbench(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.workbench")) {
            player.sendMessage(configManager.getNoPerms());
        }

        if (args.length == 0) {
            player.openWorkbench(null, true);
        } else {
            player.sendMessage("Использование: ");
        }

        return true;
    }

}
