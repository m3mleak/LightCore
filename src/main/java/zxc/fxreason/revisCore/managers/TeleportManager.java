package zxc.fxreason.revisCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import zxc.fxreason.revisCore.RevisCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager implements Listener {

    private final RevisCore plugin;
    private final Map<UUID, BukkitTask> teleports = new HashMap<>();
    private final Map<UUID, BossBar> bars = new HashMap<>();

    public TeleportManager(RevisCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void startTeleport(Player player, String successMsg, Location target, int seconds) {
        cancelTeleport(player, false);

        BossBar bossBar = Bukkit.createBossBar("§bТелепортация через §a7 §bсекунд...", BarColor.BLUE, BarStyle.SOLID);
        bossBar.addPlayer(player);
        bars.put(player.getUniqueId(), bossBar);

        BukkitTask task = new BukkitRunnable() {
            int ticksLeft = seconds;

            @Override
            public void run() {
                if (ticksLeft <= 0) {
                    player.teleport(target);
                    player.sendMessage(successMsg);
                    cleanup(player);
                    cancel();
                    return;
                }

                bossBar.setTitle("§bТелепортация через §a" + ticksLeft + " §bсек.");
                bossBar.setProgress((double) ticksLeft / seconds);
                ticksLeft--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        teleports.put(player.getUniqueId(), task);
    }

    public void cancelTeleport(Player player, boolean showMessage) {
        BukkitTask task = teleports.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
            cleanup(player);
            if (showMessage) {
                player.sendMessage(plugin.getConfigManager().getTpCancelledDmg());
            }
        }
    }

    private void cleanup(Player player) {
        teleports.remove(player.getUniqueId());
        BossBar bar = bars.remove(player.getUniqueId());
        if (bar != null) bar.removeAll();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (teleports.containsKey(player.getUniqueId())) {
                cancelTeleport(player, true);
            }
        }
    }
}
