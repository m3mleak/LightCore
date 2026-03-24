package zxc.fxreason.revisCore.clansystem;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.utils.ColorUtils;

import java.util.*;

public class ClanCMD extends BukkitRunnable implements CommandExecutor, Listener {

    private final RevisCore plugin;
    private Map<UUID, Long> players = new HashMap<>();

    public ClanCMD(RevisCore plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        for (UUID uuid : players.keySet()) {
            if (System.currentTimeMillis() >= players.get(uuid)) {
                players.remove(uuid);
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        String menuTitle = plugin.getConfigManager().getClanMenuInvName();
        String menuCreateClanTitle = plugin.getConfigManager().getMenuClanCreate();
        Inventory mainClanMenu = plugin.getServer().createInventory(null, 27, menuTitle);
        Inventory clanCreateMenu = plugin.getServer().createInventory(null, 27, menuCreateClanTitle);
        Inventory clanAcceptCreateMenu = plugin.getServer().createInventory(null, 27, menuTitle);

        ItemStack bluePanel = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack whitePanel = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack menuClanButton = new ItemStack(Material.OMINOUS_TRIAL_KEY);
        ItemStack menuBalanceInd = new ItemStack(Material.NETHER_STAR);
        ItemStack menuClanTopInd = new ItemStack(Material.ENDER_EYE);
        ItemStack clanCreateAccept = new ItemStack(Material.LIME_CANDLE);
        ItemStack clanCreateDeny = new ItemStack(Material.RED_CANDLE);
        ItemStack createClanButton = new ItemStack(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);

        ItemMeta menuClanMeta = menuClanButton.getItemMeta();
        ItemMeta menuBalanceMeta = menuBalanceInd.getItemMeta();
        ItemMeta menuClanTopMeta = menuClanTopInd.getItemMeta();

        ItemMeta createClanButtonMeta = createClanButton.getItemMeta();

        String menuClanButtonName = "<gradient:#4498DB:#0E31FC>★ мᴇʜю ᴋлᴀʜᴀ</gradient>";
        String menuBalanceIndName = "<gradient:#00FF1C:#06510C>⛀ ᴋᴀзʜᴀ ᴋлᴀʜᴀ</gradient>";
        String menuClanTopName = "<gradient:#FF0000:#FF0015>☠ тᴏп ᴋлᴀʜᴏʙ</gradient>";
        String clanCreateButtonName = "<gradient:#E700FF:#3B34BE>⚔ ᴄᴏздᴀть ᴋлᴀʜ</gradient>";

        menuClanMeta.displayName(ColorUtils.parseItem(menuClanButtonName));
        menuBalanceMeta.displayName(ColorUtils.parseItem(menuBalanceIndName));
        menuClanTopMeta.displayName(ColorUtils.parseItem(menuClanTopName));

        createClanButtonMeta.displayName(ColorUtils.parseItem(clanCreateButtonName));
        createClanButtonMeta.lore(null);

        List<Component> loreClanCreateButton = new ArrayList<>();
        loreClanCreateButton.add(0, Component.text("").color(NamedTextColor.WHITE));
        loreClanCreateButton.add(1, Component.text("Стоимость создания клана: 25.000$").color(NamedTextColor.WHITE));
        loreClanCreateButton.add(2, Component.text("После нажатия у вас будет 10 секунд чтобы ввести тег клана!").color(NamedTextColor.WHITE));

        createClanButtonMeta.lore(loreClanCreateButton);

        menuClanButton.setItemMeta(menuClanMeta);
        menuBalanceInd.setItemMeta(menuBalanceMeta);
        menuClanTopInd.setItemMeta(menuClanTopMeta);

        createClanButton.setItemMeta(createClanButtonMeta);

        int[] lightBluePanels = {0, 1, 7, 8, 10, 16, 18, 19, 25, 26};

        for (int slot : lightBluePanels) {
            mainClanMenu.setItem(slot, bluePanel);
            clanCreateMenu.setItem(slot, bluePanel);
        }

        mainClanMenu.setItem(9, whitePanel);
        mainClanMenu.setItem(17, whitePanel);
        mainClanMenu.setItem(13, menuClanButton);
        mainClanMenu.setItem(12, menuBalanceInd);
        mainClanMenu.setItem(14, menuClanTopInd);

        clanCreateMenu.setItem(9, whitePanel);
        clanCreateMenu.setItem(17, whitePanel);
        clanCreateMenu.setItem(13, createClanButton);

        if (ClanManager.getPlayerClan(player.getUniqueId()) != null) {
            player.openInventory(mainClanMenu);
        } else {
            player.openInventory(clanCreateMenu);
        }

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
        Player player = (Player) e.getWhoClicked();

        if (title.equals(plugin.getConfigManager().getClanMenuInvName())) {
            if (e.getCurrentItem() == null) {
                return;
            }

            switch (e.getSlot()) {
                case 12 -> player.openInventory(clanBankMenu());
                case 13 -> player.openInventory(clanMenu());
                case 14 -> player.openInventory(topClansMenu());
            }

            e.setCancelled(true);
        }

        if (title.equals(plugin.getConfigManager().getMenuClanCreate())) {

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getSlot() != 13) {
                return;
            }

            if (players.containsKey(player.getUniqueId())) {
                player.closeInventory();
                player.sendMessage("Вы еще не написали название клана");
                return;
            }

            players.put(player.getUniqueId(), System.currentTimeMillis() + 10000);
            player.closeInventory();
            player.sendMessage("Напишите в чат название клана в течении 10 секунд!");

            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onChat(AsyncChatEvent e) {

        Player player =  e.getPlayer();
        Component message = e.message();

        Component clanName = Component.text("Клан ")
                .append(message.color(NamedTextColor.GREEN))
                .append(Component.text(" успешно создан!")).color(NamedTextColor.WHITE);

        e.setCancelled(true);

        if (!players.containsKey(player.getUniqueId())) {
            return;
        }

        player.sendMessage(clanName);
    }

}
