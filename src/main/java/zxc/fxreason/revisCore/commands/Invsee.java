package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Invsee implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("reviscore.invsee")) {
            if (args.length == 1) {
                String username = args[0];
                Player target = Bukkit.getPlayer(username);

                if (target != null) {
                    String menuName = "Invsee" + username;
                    Inventory targetInventory = Bukkit.createInventory(null, 54, menuName);

                    ItemStack[] contents = target.getInventory().getContents();
                    for (int i = 0; i < 36; i++) {
                        if (contents[i] != null) {
                            targetInventory.setItem(i, contents[i].clone());
                        }
                    }

                    ItemStack[] armor = target.getInventory().getArmorContents();
                    for (int i = 0; i < armor.length; i++) {
                        if (armor[i] != null) {
                            targetInventory.setItem(36 + i, armor[i].clone());
                        }
                    }

                    if (target.getInventory().getItemInOffHand() != null) {
                        targetInventory.setItem(40, target.getInventory().getItemInOffHand().clone());
                    }

                    player.openInventory(targetInventory);
                } else {
                    player.sendMessage("Игрок не найден");
                }
            } else {
                player.sendMessage("неправильное использование");
            }
            return true;
        } else {
            player.sendMessage("нет прав");
        }

        return true;
    }
}
