package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 18.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Tp implements CommandExecutor {

    private final RevisCore plugin;

    public Tp(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.tp")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length == 1) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            if (target.equals(player.getName())) {
                player.sendMessage(plugin.getConfigManager().getNotTpToYou());
            }

            if (target != null) {
                player.teleport(target.getLocation());
                plugin.getMessageUtil().sendMessage(player, targetName, plugin.getConfigManager().getSucessTeleportation());
                return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getUsageTp());
            return true;
        }
    }
}
