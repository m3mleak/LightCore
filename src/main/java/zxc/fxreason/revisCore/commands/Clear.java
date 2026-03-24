package zxc.fxreason.revisCore.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Clear implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Clear(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        Player player = (Player) sender;
        long timeLeft = this.cdManager.getCooldown(player);

        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 0) {

            if (!sender.hasPermission("reviscore.clear")) {
                sender.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            player.getInventory().clear();
            player.sendMessage(plugin.getConfigManager().getClearSuccess());
            cdManager.setCooldown(player, 15);

            return true;
        }
        if (args.length == 1) {
            if (!sender.hasPermission("reviscore.clear-all")) {
                sender.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                target.getInventory().clear();
            } else {
                sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            }
            sender.sendMessage(plugin.getConfigManager().getClearSuccess());

            return true;
        }

        if (args.length == 3) {
            if (!sender.hasPermission("reviscore.clear-items")) {
                sender.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                return true;
            }

            Material material = Material.matchMaterial(args[1].toUpperCase());
            if (material == null) {
                sender.sendMessage(plugin.getConfigManager().getIncorrectItem());
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getConfigManager().getCountInt());
                return true;
            }

            int removed = removeItems(target, material, amount);
            sender.sendMessage("§b✽ §7➛ §fУдалено §a" + removed + "§f предметов §a" + material.name() + "§f у игрока §a" + player.getName() + "§f.");
        }

        return true;
    }

    private int removeItems(Player player, Material material, int amount) {
        int toRemove  = amount;
        int removed = 0;

        ItemStack contents[] = player.getInventory().getContents();

        for (int i = 0; i < contents.length && toRemove > 0; i++) {
            ItemStack item = contents[i];


            if (item != null && item.getType() == material) {
                int itemAmount = item.getAmount();

                if (itemAmount <= toRemove) {
                    removed += itemAmount;
                    toRemove -= itemAmount;
                    player.getInventory().setItem(i, null);
                } else {
                    item.setAmount(itemAmount - toRemove);
                    removed += toRemove;
                    toRemove = 0;
                    player.getInventory().setItem(i, item);
                }
            }
        }

        player.updateInventory();
        return removed;
    }
}
