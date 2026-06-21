package zxc.fxreason.lightCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Invsee implements CommandExecutor {

    private final LightCore plugin;

    public Invsee(LightCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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

                    String menuName = plugin.getConfigManager().getInvseeNameInv() + target.getName();
                    Inventory targetInventory = Bukkit.createInventory(null, 54, menuName);

                    targetInventory.setItem(49, ItemStack.of(Material.ARMOR_STAND));

                    targetInventory.setItem(36, ItemStack.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                    targetInventory.setItem(44, ItemStack.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE));

                    targetInventory.setItem(37, ItemStack.of(Material.WHITE_STAINED_GLASS_PANE));
                    targetInventory.setItem(38, ItemStack.of(Material.WHITE_STAINED_GLASS_PANE));
                    targetInventory.setItem(42, ItemStack.of(Material.WHITE_STAINED_GLASS_PANE));
                    targetInventory.setItem(43, ItemStack.of(Material.WHITE_STAINED_GLASS_PANE));

                    for (int i = 0; i < 3; i++) {
                        targetInventory.setItem(39 + i, ItemStack.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                    }

                    ItemStack[] contents = target.getInventory().getContents();
                    for (int j = 0; j < 36; j++) {
                        if (contents[j] != null)
                            targetInventory.setItem(j, contents[j].clone());
                    }

                    ItemStack[] armor = target.getInventory().getArmorContents();
                    for (int k = 0; k < armor.length; k++) {
                        if (armor[k] != null)
                            targetInventory.setItem(45 + k, armor[k].clone());
                    }

                    if (target.getInventory().getItemInOffHand() != null) {
                        targetInventory.setItem(53, target.getInventory().getItemInOffHand().clone());
                    }

                    player.openInventory(targetInventory);
                } else {
                    player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                }
            } else {
                player.sendMessage(plugin.getConfigManager().getUsageInvsee());
            }
            return true;
        }
        player.sendMessage(plugin.getConfigManager().getNoPerms());
        return true;
    }
}
