package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 20.02.2026
 **/

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Suicide implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Suicide(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        int cooldown = getCooldowns(player);
        long timeLeft = this.cdManager.getCooldown(player);
        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (!player.hasPermission("reviscore.suicide")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            this.cdManager.setCooldown(player, cooldown);
            return true;
        }

        if (args.length == 0) {
            player.setHealth(0.0);
            player.sendMessage(plugin.getConfigManager().getSuicideMsg());
        } else {
            player.sendMessage(plugin.getConfigManager().getSuicideUsage());
        }

        return false;
    }

    private int getCooldowns(Player player) {
        if (player.hasPermission("reviscore.phantom")) {
            return 120;
        } else {
            return 150;
        }
    }
}
