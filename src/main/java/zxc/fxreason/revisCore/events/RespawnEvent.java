package zxc.fxreason.revisCore.events;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class RespawnEvent implements Listener {

    private final RevisCore plugin;

    public RespawnEvent(RevisCore plugin) {
        this.plugin = plugin;
    }

    public Location loadSpawnLoc() {
        FileConfiguration config = plugin.getConfig();
        ConfigManager configManager = plugin.getConfigManager();

        if (config.contains("spawn.world")) {
            Location spawnLocation = new Location(
                    plugin.getServer().getWorld(configManager.getWorld()),
                    configManager.getx(),
                    configManager.gety(),
                    configManager.getz(),
                    configManager.getYaw(),
                    configManager.getPitch()
            );
            return spawnLocation;
        }
        return null;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!event.isBedSpawn() && !event.isAnchorSpawn()) {
            Location sLoc = loadSpawnLoc();

            if (sLoc != null) {
                event.setRespawnLocation(sLoc);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfigManager().isTeleportOnFirstJoin() && !event.getPlayer().hasPlayedBefore()) {
            teleportToSpawnDelay(event.getPlayer(), 20L);
        }
    }

    private void teleportToSpawnDelay(Player player, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()){
                    player.teleport(loadSpawnLoc());
                }
            }
        }.runTaskLater(plugin, delay);
    }
}
