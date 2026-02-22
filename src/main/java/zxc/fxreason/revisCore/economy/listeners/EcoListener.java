package zxc.fxreason.revisCore.economy.listeners;

/*
 * By fxreason
 * 23.02.2026
 **/

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import zxc.fxreason.revisCore.economy.CustomEcoLogic;
import zxc.fxreason.revisCore.economy.CustomEconomy;

public class EcoListener implements Listener {

    private final CustomEcoLogic customEcoLogic;

    public EcoListener(CustomEconomy customEconomy, CustomEcoLogic customEcoLogic) {
        this.customEcoLogic = customEcoLogic;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        customEcoLogic.loadPlayerData(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        customEcoLogic.savePlayerData(player.getUniqueId());
    }
}
