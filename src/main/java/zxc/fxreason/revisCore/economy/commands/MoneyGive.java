package zxc.fxreason.revisCore.economy.commands;

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
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.utils.MessageUtil;

import java.math.BigDecimal;

public class MoneyGive implements CommandExecutor {

    private final RevisCore plugin;

    public MoneyGive(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!sender.hasPermission("reviscore.givemoney")) {
            sender.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length == 2) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            try {
                double amnt = Double.parseDouble(args[1]);
                BigDecimal amount = new BigDecimal(amnt);

                if (target == null) {
                    sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                    return true;
                }

                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    sender.sendMessage(plugin.getConfigManager().getMoneyGiveNotPositive());
                    return true;
                }

                plugin.getCustomEconomy().deposit(target.getUniqueId(), amount);
                String message = plugin.getConfigManager().getGiveMoneyMsg() + targetName;

                MessageUtil.sendMessageMoney(sender, plugin.getCustomEconomy().format(amount), message);

                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getConfigManager().getErrAmount());
                return true;
            }

        } else {
            sender.sendMessage(plugin.getConfigManager().getGiveMoneyUsage());
            return true;
        }
    }
}
