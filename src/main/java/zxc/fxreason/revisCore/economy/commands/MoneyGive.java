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
import zxc.fxreason.revisCore.economy.CustomEconomy;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.utils.MessageUtil;

import java.math.BigDecimal;

public class MoneyGive implements CommandExecutor {

    private final ConfigManager configManager;
    private final CustomEconomy economy;
    private final MessageUtil messageUtil;

    public MoneyGive(ConfigManager configManager, CustomEconomy economy, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.economy = economy;
        this.messageUtil = messageUtil;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!sender.hasPermission("reviscore.givemoney")) {
            sender.sendMessage(configManager.getNoPerms());
            return true;
        }

        if (args.length == 2) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            try {
                double amnt = Double.parseDouble(args[1]);
                BigDecimal amount = new BigDecimal(amnt);

                if (target == null) {
                    sender.sendMessage(configManager.getNicknameNotFound());
                    return true;
                }

                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    sender.sendMessage(configManager.getMoneyGiveNotPositive());
                    return true;
                }

                economy.deposit(target.getUniqueId(), amount);
                String message = configManager.getGiveMoneyMsg() + targetName;

                messageUtil.sendMessageMoney(sender, economy.format(amount), message);

                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage(configManager.getErrAmount());
                return true;
            }

        } else {
            sender.sendMessage(configManager.getGiveMoneyUsage());
            return true;
        }
    }
}
