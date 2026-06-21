package zxc.fxreason.lightCore.commands;

/*
 * By fxreason
 * 15.02.2026
 **/

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class TpaAccept implements CommandExecutor {

    private final LightCore plugin;

    public TpaAccept(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        plugin.getTpReqManager().acceptRequest(player);
        return true;
    }
}
