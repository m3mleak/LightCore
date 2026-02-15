package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Tpa implements CommandExecutor {
    private ConfigManager configManager;

    private final CooldownManager cdManager = new CooldownManager();

    public Tpa(ConfigManager configManager) {
        this.configManager = configManager;
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
            player.sendMessage(this.configManager.getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }
        if (args.length == 1) {

            String target = args[0];

            if (target.equals(player.getName())) {
                player.sendMessage(this.configManager.getNonTpToMe());
                return false;
            }

            Player targetPlayer = Bukkit.getPlayer(target);
            World world = targetPlayer.getWorld();

            double x = targetPlayer.getX();
            double y = targetPlayer.getY();
            double z = targetPlayer.getZ();
            
            player.teleport(new Location(world, x, y, z));

        } else {
            player.sendMessage(this.configManager.getUsageTpa());
        }
        this.cdManager.setCooldown(player, seconds);
        return true;
    }
}
