package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.CooldownManager;
import zxc.fxreason.revisCore.managers.TpReqManager;

public class Tpa implements CommandExecutor {
    private ConfigManager configManager;

    private final CooldownManager cdManager = new CooldownManager();
    private final TpReqManager tpReqManager;

    public Tpa(ConfigManager configManager, TpReqManager tpReqManager) {
        this.configManager = configManager;
        this.tpReqManager = tpReqManager;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int seconds;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }
        Player player = (Player)sender;
        if (player.hasPermission("reviscore.cooldown-tpa.15")) {
            seconds = 15;
        } else if (player.hasPermission("reviscore.cooldown-tpa.20")) {
            seconds = 20;
        } else if (player.hasPermission("reviscore.cooldown-tpa.30")) {
            seconds = 30;
        } else if (player.hasPermission("reviscore.cooldown-tpa.45")) {
            seconds = 45;
        } else {
            seconds = 60;
        }
        long timeLeft = this.cdManager.getCooldown(player);
        if (timeLeft > 0L && !player.hasPermission("reviscore.noncooldown-tpa")) {
            player.sendMessage(configManager.getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }
        if (args.length == 1) {

            String target = args[0];
            String type = "tpa";

            if (target.equals(player.getName())) {
                player.sendMessage(configManager.getNonTpToMe());
                return false;
            }

            Player targetPlayer = Bukkit.getPlayer(target);

            if (targetPlayer != null) {
                tpReqManager.sendRequest(player, targetPlayer, type);
            } else {
                player.sendMessage(configManager.getNicknameNotFound());
            }


        } else {
            player.sendMessage(this.configManager.getUsageTpa());
        }
        this.cdManager.setCooldown(player, seconds);
        return true;
    }
}
