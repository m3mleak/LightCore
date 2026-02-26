package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Tpa implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Tpa(RevisCore plugin) {
        this.plugin = plugin;
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
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }
        if (args.length == 1) {

            String target = args[0];
            String type = "tpa";

            if (target.equals(player.getName())) {
                player.sendMessage(plugin.getConfigManager().getNonTpToMe());
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(target);

            if (targetPlayer != null) {
                plugin.getTpReqManager().sendRequest(player, targetPlayer, type);
            } else {
                player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            }


        } else {
            player.sendMessage(this.plugin.getConfigManager().getUsageTpa());
        }
        this.cdManager.setCooldown(player, seconds);
        return true;
    }
}
