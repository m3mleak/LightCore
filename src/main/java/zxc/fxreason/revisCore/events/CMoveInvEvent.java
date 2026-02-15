package zxc.fxreason.revisCore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class CMoveInvEvent implements Listener {
    private final RevisCore plugin;

    private ConfigManager configManager;

    public CMoveInvEvent(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onIventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith(this.configManager.getInvseeNameInv()))
            event.setCancelled(true);
    }
}
