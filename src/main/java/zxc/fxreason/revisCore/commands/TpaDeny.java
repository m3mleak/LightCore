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
import zxc.fxreason.revisCore.managers.TpReqManager;

public class TpaDeny implements CommandExecutor {
    private final TpReqManager tpReqManager;

    public TpaDeny(TpReqManager tpReqManager) {
        this.tpReqManager = tpReqManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        tpReqManager.denyRequest(player);
        return true;
    }
}
