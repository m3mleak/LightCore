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
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.TpReqManager;

public class Tpahere implements CommandExecutor {
    private TpReqManager tpReqManager;
    private ConfigManager configManager;

    public Tpahere(TpReqManager tpReqManager, ConfigManager configManager) {
        this.tpReqManager = tpReqManager;
        this.configManager = configManager;
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
                player.sendMessage(configManager.getNonTpToMe());
                return true;
            }

            if (target != null) {
                tpReqManager.sendRequest(player, target, type);
            } else {
                player.sendMessage(configManager.getNicknameNotFound());
            }
            return true;
        } else {
            player.sendMessage(configManager.getTpahereUsage());
        }

        return true;
    }
}
