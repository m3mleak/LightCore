package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 20.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Tphere implements CommandExecutor {

    private final RevisCore plugin;

    public Tphere(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("reviscore.tphere")) {
            if (args.length == 1) {
                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);
                String playerName = player.getName();

                if (targetName.equals(playerName)) {
                    player.sendMessage(plugin.getConfigManager().getNotTphereYou());
                    return true;
                }

                if (target != null) {
                    target.teleport(player.getLocation());
                    player.sendMessage(plugin.getConfigManager().getSuccessTphere());
                } else {
                    player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                }
                return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getUsageTphere());
                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }
    }
}
