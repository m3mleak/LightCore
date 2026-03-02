package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 25.02.2026
 **/

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Hat implements CommandExecutor {

    private final RevisCore plugin;

    public Hat(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        PlayerInventory playerInv = player.getInventory();

        if (!player.hasPermission("reviscore.hat")) {
           player.sendMessage(plugin.getConfigManager().getNoPerms());
           return true;
        }

        if (args.length > 0) {
            player.sendMessage(plugin.getConfigManager().getHatUsage());
            return true;
        }

        ItemStack item = playerInv.getItemInMainHand();

        if (item.getType() == Material.AIR) {
            player.sendMessage(plugin.getConfigManager().getHatNotItemMH());
            return true;
        }

        if (playerInv.getHelmet() != null) {
            player.sendMessage(plugin.getConfigManager().getHatHelmErr());
            return true;
        }

        if (!item.getType().isBlock()) {
            player.sendMessage(plugin.getConfigManager().getHatUsageNotBlocks());
            return true;
        }

        ItemStack helmet = item.clone();
        helmet.setAmount(1);

        playerInv.setHelmet(helmet);
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            playerInv.setItemInMainHand(null);
        }
        player.sendMessage(plugin.getConfigManager().getHatSuccess());

        return true;
    }
}
