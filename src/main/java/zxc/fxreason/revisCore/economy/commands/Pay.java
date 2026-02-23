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

public class Pay implements CommandExecutor {

    private final CustomEconomy economy;
    private final ConfigManager configManager;
    private final MessageUtil messageUtil;

    public Pay(CustomEconomy economy, ConfigManager configManager, MessageUtil messageUtil) {
        this.economy = economy;
        this.configManager = configManager;
        this.messageUtil = messageUtil;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(configManager.getPayUsage());
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            player.sendMessage(configManager.getNicknameNotFound());
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(configManager.getNotPayYou());
            return true;
        }

        try {
            double amnt = Double.parseDouble(args[1]);
            BigDecimal amount = new BigDecimal(amnt);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                player.sendMessage(configManager.getPayNotPostive());
                return true;
            }

            if (!economy.hasEnough(player.getUniqueId(), amount)) {
                player.sendMessage(configManager.getPayNotMoney());
                return true;
            }

            if (economy.transfer(player.getUniqueId(), target.getUniqueId(), amount)) {
                String am = economy.format(amount);

                String message1 = configManager.getPayMeSend() + target.getName();
                messageUtil.sendMessageMoney(player, am, message1);

                String message2 = configManager.getPayToSend() + player.getName();
                messageUtil.sendMessageMoney(target, am, message2);
            } else {
                player.sendMessage(configManager.getPayNotSend());
            }

        } catch (NumberFormatException e) {
            player.sendMessage(configManager.getErrAmount());
            e.printStackTrace();
        }

        return true;
    }
}