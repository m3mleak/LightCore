package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;

/*
 * By fxreason
 * 15.02.2026
 **/

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.TpReqManager;

public class TpaDeny implements CommandExecutor {

    private final RevisCore plugin;

    public TpaDeny(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        plugin.getTpReqManager().denyRequest(player);
        return true;
    }
}
