package zxc.fxreason.revisCore.clansystem;

import org.bukkit.Material;
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
import zxc.fxreason.revisCore.utils.ColorUtils;

public class ClanCMD implements CommandExecutor, Listener {

    private final RevisCore plugin;

    public ClanCMD(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        String menuTitle = plugin.getConfigManager().getClanMenuInvName();
        Inventory mainClanMenu = plugin.getServer().createInventory(null, 27, menuTitle);

        ItemStack bluePanel = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack whitePanel = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack menuClanButton = new ItemStack(Material.OMINOUS_TRIAL_KEY);
        ItemStack menuBalanceInd = new ItemStack(Material.NETHER_STAR);
        ItemStack menuClanTopInd = new ItemStack(Material.ENDER_EYE);

        ItemMeta menuClanMeta = menuClanButton.getItemMeta();
        ItemMeta menuBalanceMeta = menuBalanceInd.getItemMeta();
        ItemMeta menuClanTopMeta = menuClanTopInd.getItemMeta();

        String menuClanButtonName = "<gradient:#4498DB:#0E31FC>★ мᴇʜю ᴋлᴀʜᴀ</gradient>";
        String menuBalanceIndName = "<gradient:#00FF1C:#06510C>⛀ ᴋᴀзʜᴀ ᴋлᴀʜᴀ</gradient>";
        String menuClanTopName = "<gradient:#FF0000:#FF0015>☠ тᴏп ᴋлᴀʜᴏʙ</gradient>";

        menuClanMeta.displayName(ColorUtils.parseItem(menuClanButtonName));
        menuBalanceMeta.displayName(ColorUtils.parseItem(menuBalanceIndName));
        menuClanTopMeta.displayName(ColorUtils.parseItem(menuClanTopName));

        menuClanButton.setItemMeta(menuClanMeta);
        menuBalanceInd.setItemMeta(menuBalanceMeta);
        menuClanTopInd.setItemMeta(menuClanTopMeta);

        int[] lightBluePanels = {0, 1, 7, 8, 10, 16, 18, 19, 25, 26};

        for (int slot : lightBluePanels) {
            mainClanMenu.setItem(slot, bluePanel);
        }

        mainClanMenu.setItem(9, whitePanel);
        mainClanMenu.setItem(17, whitePanel);
        mainClanMenu.setItem(13, menuClanButton);
        mainClanMenu.setItem(12, menuBalanceInd);
        mainClanMenu.setItem(14, menuClanTopInd);

        player.openInventory(mainClanMenu);

        return true;
    }

    private Inventory clanMenu() {

        /*
         * Main clan menu
         * Functions work for clansystem (Moderation clan, Experience clan and Complete tasks)
         * */

        // TODO: полная система управления кланом
        String clanMenuName = plugin.getConfigManager().getMenuClanInvName();
        Inventory clanMenu = plugin.getServer().createInventory(null, 36, clanMenuName);

        return clanMenu;
    }

    private Inventory clanBankMenu() {

        /*
        * Clan bank menu
        * Functions work for deposit, withdraw and other manipulation of money in the clan
        * */

        // TODO: система управления казной клана (положить деньги, снять деньги, выдать разрешения на снятие денег)
        String clanBankMenuName = plugin.getConfigManager().getMenuBankClanName();
        Inventory clanBankMenu = plugin.getServer().createInventory(null, 27, clanBankMenuName);

        return clanBankMenu;
    }

    private Inventory topClansMenu() {

        /*
        * Top clan's menu
        * This menu for view list top clan's in the server
        * */

        // TODO: реализация системы топа кланов в графическом меню
        String topClanMenuName = plugin.getConfigManager().getMenuTopClansName();
        Inventory topClanMenu = plugin.getServer().createInventory(null, 45, topClanMenuName);

        return topClanMenu;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        String title = e.getView().getTitle();

        if (title.equals(plugin.getConfigManager().getClanMenuInvName())) {
            if (e.getCurrentItem() == null) {
                return;
            }

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()) {
                case 12 -> player.openInventory(clanBankMenu());
                case 13 -> player.openInventory(clanMenu());
                case 14 -> player.openInventory(topClansMenu());
            }

            e.setCancelled(true);
        }


    }

}
