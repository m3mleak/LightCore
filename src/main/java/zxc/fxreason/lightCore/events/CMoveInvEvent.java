package zxc.fxreason.lightCore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import zxc.fxreason.lightCore.LightCore;

public class CMoveInvEvent implements Listener {

    private final LightCore plugin;

    public CMoveInvEvent(LightCore plugin) {
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
