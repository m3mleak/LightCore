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

public class Tpahere implements CommandExecutor {

    private final RevisCore plugin;

    public Tpahere(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);
            String type = "tpahere";

            if (targetName.equals(player.getName())) {
                player.sendMessage(plugin.getConfigManager().getNonTpToMe());
                return true;
            }

            if (target != null) {
                plugin.getTpReqManager().sendRequest(player, target, type);
            } else {
                player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            }
            return true;
        } else {
            player.sendMessage(plugin.getConfigManager().getTpahereUsage());
        }

        return true;
    }
}
