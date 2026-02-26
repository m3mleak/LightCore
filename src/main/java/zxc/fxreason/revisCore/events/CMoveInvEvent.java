package zxc.fxreason.revisCore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class CMoveInvEvent implements Listener {

    private final RevisCore plugin;

    public CMoveInvEvent(RevisCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onIventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.startsWith(plugin.getConfigManager().getInvseeNameInv())) {
            event.setCancelled(true);
        }
    }
}
