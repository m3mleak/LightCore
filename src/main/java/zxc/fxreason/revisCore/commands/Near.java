package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 24.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class Near implements CommandExecutor {

    private final RevisCore plugin;
    private final ConfigManager configManager;
    private final int defaultRadius = 100;

    public Near(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.near")) {
            player.sendMessage(configManager.getNoPerms());
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(configManager.getNearUsage());
            return true;
        }

        int radius = defaultRadius;

        List<String> nearPlayers = getNearPlayers(player, radius);

        if (nearPlayers.isEmpty()) {
            player.sendMessage(configManager.getNotFoundNearPLayers());
        } else {
            player.sendMessage("§b✽ §7➛ §fИгроки рядом с вами в радиусе (" + radius + " м.):");
            for (int i = 0; i < nearPlayers.size(); i++) {
                player.sendMessage("§f" + (i+1) + ". §b" + nearPlayers.get(i));
            }
        }
        return true;
    }

    private List<String> getNearPlayers(Player player, int radius) {
        List<String> names = new ArrayList<>();
        Location playerLoc = player.getLocation();

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.equals(player)) continue;

            if (playerLoc.getWorld().equals(target.getWorld())) {
                double distance = playerLoc.distance(target.getLocation());
                if (distance <= radius) {
                    names.add(target.getName() + " (" + (int) distance + " м.)");
                }
            }
        }
        return names;
    }
}
