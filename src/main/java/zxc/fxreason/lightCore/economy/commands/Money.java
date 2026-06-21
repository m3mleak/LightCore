package zxc.fxreason.lightCore.economy.commands;

/*
 * By fxreason
 * 23.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Money implements CommandExecutor {

    private final LightCore plugin;

    public Money(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cЭта команда только для игроков!");
                return true;
            }

            double balance = plugin.getCustomEconomy().getBalance(player.getUniqueId()).doubleValue();
            player.sendMessage(String.format(plugin.getConfigManager().getMoneyMsg() + plugin.getCustomEconomy().format(plugin.getCustomEconomy().getBalance(player.getUniqueId()))));

            return true;
        }
        if (args.length == 1) {
            if (player.hasPermission("reviscore.money-see-all")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null) {
                    double balance = plugin.getCustomEconomy().getBalance(target.getUniqueId()).doubleValue();
                    player.sendMessage(plugin.getConfigManager().getMoneyPlayerMsg() + plugin.getCustomEconomy().format(plugin.getCustomEconomy().getBalance(target.getUniqueId())));
                } else {
                    player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                }
            } else {
                player.sendMessage(plugin.getConfigManager().getNoPerms());

            }
            return true;
        } else {
            player.sendMessage(plugin.getConfigManager().getMoneyUsage());
        }
        return true;
    }
}
