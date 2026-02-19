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
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.utils.MessageUtil;

public class Tp implements CommandExecutor {

    private ConfigManager configManager;
    private MessageUtil messageUtil;

    public Tp(ConfigManager configManager, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.messageUtil = messageUtil;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.tp")) {
            player.sendMessage(configManager.getNoPerms());
            return true;
        }

        if (args.length == 1) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            if (target.equals(player.getName())) {
                player.sendMessage(configManager.getNotTpToYou());
            }

            if (target != null) {
                player.teleport(target.getLocation());
                messageUtil.sendMessage(player, targetName, configManager.getSucessTeleportation());
                return true;
            } else {
                player.sendMessage(configManager.getNicknameNotFound());
                return true;
            }
        } else {
            player.sendMessage(configManager.getUsageTp());
            return true;
        }
    }
}
