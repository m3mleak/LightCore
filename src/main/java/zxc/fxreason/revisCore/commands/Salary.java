package zxc.fxreason.revisCore.commands;

/*
 * By fxreason
 * 25.02.2026
 **/

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;
import zxc.fxreason.revisCore.utils.ColorUtils;
import zxc.fxreason.revisCore.utils.MessageUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Salary implements CommandExecutor, Listener {

    private final CooldownManager cdManager = new CooldownManager();
    private final RevisCore plugin;
    private long timeLeft;

    public Salary(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        timeLeft = cdManager.getCooldown(player);

        BigDecimal amount = calculateAmount(player);

        String menuTitle = plugin.getConfigManager().getSalaryNameInv();
        Inventory salaryInv = plugin.getServer().createInventory(null, 27, menuTitle);

        ItemStack lightBlue = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack white = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

        int[] lightBlueSlots = {0, 1, 9, 18, 19, 7, 8, 17, 25, 26};
        for (int slot : lightBlueSlots) salaryInv.setItem(slot, lightBlue);

        salaryInv.setItem(4, white);
        salaryInv.setItem(22, white);

        salaryInv.setItem(13, createSalaryItem(amount));

        player.openInventory(salaryInv);
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_STEP, 10, 1);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (!title.equals(plugin.getConfigManager().getSalaryNameInv())) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null || e.getSlot() != 13) return;

        Player player = (Player) e.getWhoClicked();
        BigDecimal amount = calculateAmount(player);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            player.sendMessage(plugin.getConfigManager().getSalaryErr());
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            player.closeInventory();
        } else {

            if (timeLeft > 0L) {
                player.sendMessage(plugin.getConfigManager().getCooldownCMD()  + timeLeft + " §fсек.");
                e.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                player.closeInventory();
            } else {
                plugin.getCustomEconomy().deposit(player.getUniqueId(), amount);

                this.cdManager.setCooldown(player, 86400);
                MessageUtil.sendMessageMoney(player, String.valueOf(amount), plugin.getConfigManager().getSalarySuccess());
                player.playSound(player.getLocation(), Sound.BLOCK_SCULK_CATALYST_FALL, 10, 1);
                player.closeInventory();
            }
        }
    }

    private BigDecimal calculateAmount(Player player) {
        if (player.hasPermission("reviscore.custom")) return BigDecimal.valueOf(500000);
        if (player.hasPermission("reviscore.ancient")) return BigDecimal.valueOf(180000);
        if (player.hasPermission("reviscore.warden")) return BigDecimal.valueOf(90000);
        if (player.hasPermission("reviscore.elder")) return BigDecimal.valueOf(70000);
        if (player.hasPermission("reviscore.rubin")) return BigDecimal.valueOf(50000);
        if (player.hasPermission("reviscore.void")) return BigDecimal.valueOf(30000);
        if (player.hasPermission("reviscore.keeper")) return BigDecimal.valueOf(15000);
        if (player.hasPermission("reviscore.phantom")) return BigDecimal.valueOf(10000);
        return BigDecimal.ZERO;
    }

    private ItemStack createSalaryItem(BigDecimal amount) {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            String name = "<color:#19D0F3>⛀</color> <gradient:#19D0F3:#2537D2>пᴏлʏчить зᴀᴘплᴀтʏ</gradient>";
            List<Component> lore = new ArrayList<>();

            lore.add(Component.empty());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                lore.add(ColorUtils.parseItem("<white>- Вы не можете получать зарплату</white>"));
            } else {
                lore.add(ColorUtils.parseItem("<white>- Получать зарплату можно каждые 24 часа!</white>"));
                lore.add(ColorUtils.parseItem("<white>- Вы получите <green>" + amount + " $</green></white>"));
            }
            lore.add(Component.empty());

            meta.displayName(ColorUtils.parseItem(name));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}