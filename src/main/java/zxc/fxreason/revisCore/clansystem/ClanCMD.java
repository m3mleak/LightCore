package zxc.fxreason.revisCore.clansystem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class ClanCMD implements CommandExecutor {

    private final RevisCore plugin;

    public ClanCMD(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        String menuTitle = plugin.getConfigManager().getClanMenuInvName();
        Inventory mainClanMenu = plugin.getServer().createInventory(null, 27, menuTitle);

        ItemStack bluePanel = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack whitePanel = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

        int[] lightBluePanels = {0, 1, 7, 8, 10, 16, 18, 19, 25, 26};

        for (int slot : lightBluePanels) {
            mainClanMenu.setItem(slot, bluePanel);
        }

        mainClanMenu.setItem(8, whitePanel);
        mainClanMenu.setItem(17, whitePanel);



        return true;
    }
}
