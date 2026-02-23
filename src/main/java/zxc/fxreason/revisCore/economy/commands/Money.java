package zxc.fxreason.revisCore.economy.commands;

/*
 * By fxreason
 * 23.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.economy.CustomEconomy;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Money implements CommandExecutor {

    private final CustomEconomy economy;
    private final ConfigManager configManager;

    public Money(CustomEconomy economy, ConfigManager configManager) {
        this.economy = economy;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cЭта команда только для игроков!");
                return true;
            }

            double balance = economy.getBalance(player.getUniqueId()).doubleValue();
            player.sendMessage(String.format(configManager.getMoneyMsg() + economy.format(economy.getBalance(player.getUniqueId()))));

            return true;
        }
        if (args.length == 1) {
            if (player.hasPermission("reviscore.money-see-all")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null) {
                    double balance = economy.getBalance(target.getUniqueId()).doubleValue();
                    player.sendMessage(configManager.getMoneyPlayerMsg() + economy.format(economy.getBalance(target.getUniqueId())));
                } else {
                    player.sendMessage(configManager.getNicknameNotFound());
                }
            } else {
                player.sendMessage(configManager.getNoPerms());

            }
            return true;
        } else {
            player.sendMessage(configManager.getMoneyUsage());
        }
        return true;
    }
}
