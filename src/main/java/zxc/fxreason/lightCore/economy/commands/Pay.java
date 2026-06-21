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
import zxc.fxreason.lightCore.utils.MessageUtil;

import java.math.BigDecimal;

public class Pay implements CommandExecutor {

    private final LightCore plugin;

    public Pay(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(plugin.getConfigManager().getPayUsage());
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(plugin.getConfigManager().getNotPayYou());
            return true;
        }

        try {
            double amnt = Double.parseDouble(args[1]);
            BigDecimal amount = new BigDecimal(amnt);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                player.sendMessage(plugin.getConfigManager().getPayNotPostive());
                return true;
            }

            if (!plugin.getCustomEconomy().hasEnough(player.getUniqueId(), amount)) {
                player.sendMessage(plugin.getConfigManager().getPayNotMoney());
                return true;
            }

            if (plugin.getCustomEconomy().transfer(player.getUniqueId(), target.getUniqueId(), amount)) {
                String am = plugin.getCustomEconomy().format(amount);

                String message1 = plugin.getConfigManager().getPayMeSend() + target.getName();
                MessageUtil.sendMessageMoney(player, am, message1);

                String message2 = plugin.getConfigManager().getPayToSend() + player.getName();
                MessageUtil.sendMessageMoney(target, am, message2);
            } else {
                player.sendMessage(plugin.getConfigManager().getPayNotSend());
            }

        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getConfigManager().getErrAmount());
            e.printStackTrace();
        }

        return true;
    }
}