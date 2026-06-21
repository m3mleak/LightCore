package zxc.fxreason.lightCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import zxc.fxreason.lightCore.LightCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpReqManager implements Listener {

    private final Map<UUID, TpaRequest> activeRequests = new HashMap<>();
    private final Map<UUID, Long> lastDamageTime = new HashMap<>();
    private final Map<UUID, Boolean> TpToggles = new HashMap<>();
    private final LightCore plugin;

    private static final int REQUEST_TIMEOUT = 30;
    private static final int TELEPORT_DELAY = 7;

    public TpReqManager(LightCore plugin) {
        this.plugin = plugin;
        loadToggles();
    }

    private void loadToggles() {
        var config = plugin.getTogglesDataManager().getConfig();
        var section = config.getConfigurationSection("players.tptoggle");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                TpToggles.put(UUID.fromString(key), config.getBoolean("players.tptoggle." + key));
            }
        }
    }

    public void setTpToggle(UUID uuid, boolean state) {
        TpToggles.put(uuid, state);
    }

    public boolean isTpToggleEnabled(UUID uuid) {
        return TpToggles.getOrDefault(uuid, false);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            lastDamageTime.put(event.getEntity().getUniqueId(), System.currentTimeMillis());
        }
    }

    public void sendRequest(Player sender, Player target, String type) {

        if (isTpToggleEnabled(target.getUniqueId())) {
            sender.sendMessage(plugin.getConfigManager().getPlayerTpToggle());
            return;
        }

        activeRequests.remove(target.getUniqueId());

        TpaRequest request = new TpaRequest(sender.getUniqueId(), target.getUniqueId(), type);
        activeRequests.put(target.getUniqueId(), request);

        String nameTarget = target.getName();
        String namePlayer = sender.getName();

        plugin.getMessageUtil().sendMessage(sender, nameTarget, plugin.getConfigManager().getTpaResponse());

        if (type.equals("tpa")) {
            plugin.getMessageUtil().sendMessage(target, namePlayer, plugin.getConfigManager().getTpaResponseToTaget());
            target.sendMessage(plugin.getConfigManager().getAcceptDenyTpa());
        } else {
            plugin.getMessageUtil().sendMessage(target, namePlayer, plugin.getConfigManager().getTpahereResponse());
            target.sendMessage(plugin.getConfigManager().getAcceptDenyTpa());
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                TpaRequest currentRequest = activeRequests.get(target.getUniqueId());
                if (currentRequest != null && currentRequest.equals(request)) {
                    activeRequests.remove(target.getUniqueId());
                    Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                    Player senderPlayer = Bukkit.getPlayer(sender.getUniqueId());

                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        plugin.getMessageUtil().sendMessage(targetPlayer, namePlayer, plugin.getConfigManager().getTimesUpTp());
                    }
                    if (senderPlayer != null && senderPlayer.isOnline()) {
                        plugin.getMessageUtil().sendMessage(senderPlayer, nameTarget, plugin.getConfigManager().getTimesUpTplayer());
                    }
                }
            }
        }.runTaskLater(plugin, REQUEST_TIMEOUT * 20L);
    }

    public void acceptRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage(plugin.getConfigManager().getNotActiveReq());
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender == null || !sender.isOnline()) {
            target.sendMessage(plugin.getConfigManager().getSenderTpLeave());
            return;
        }

        Player teleporter = request.getType().equals("tpa") ? sender : target;

        long acceptTime = System.currentTimeMillis();

        BossBar bossBar = Bukkit.createBossBar(
                "§bТелепортация через §a" + TELEPORT_DELAY + " §bсекунд...",
                BarColor.BLUE,
                BarStyle.SOLID
        );

        teleporter.sendMessage(plugin.getConfigManager().getTpStartSeven());

        bossBar.addPlayer(teleporter);

        new BukkitRunnable() {
            int secondsLeft = TELEPORT_DELAY;

            @Override
            public void run() {
                if (!sender.isOnline() || !target.isOnline()) {
                    bossBar.removeAll();
                    cancel();
                    return;
                }

                Long lastDamage = lastDamageTime.get(teleporter.getUniqueId());
                if (lastDamage != null && lastDamage > acceptTime) {
                    bossBar.removeAll();
                    teleporter.sendMessage(plugin.getConfigManager().getTpCancelledDmg());
                    cancel();
                    return;
                }

                if (secondsLeft <= 0) {
                    if (request.getType().equals("tpa")) {
                        sender.teleport(target.getLocation());
                        plugin.getMessageUtil().sendMessage(sender, target.getName(), plugin.getConfigManager().getSucessTeleportation());
                        plugin.getMessageUtil().sendMessage(target, sender.getName(), plugin.getConfigManager().getSuccesTpForYou());
                    } else {
                        target.teleport(sender.getLocation());
                        sender.sendMessage(plugin.getConfigManager().getTpahereAccept());
                        plugin.getMessageUtil().sendMessage(target, sender.getName(), plugin.getConfigManager().getTpahereTp());
                    }

                    bossBar.removeAll();
                    cancel();
                    return;
                }

                bossBar.setTitle("§bТелепортация через §a" + secondsLeft + " §bсекунд...");
                bossBar.setProgress((double) secondsLeft / TELEPORT_DELAY);

                secondsLeft--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void denyRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage(plugin.getConfigManager().getNotActiveReq());
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender != null && sender.isOnline()) {
            plugin.getMessageUtil().sendMessage(sender, target.getName(), plugin.getConfigManager().getTpDeny());
        }

        target.sendMessage(plugin.getConfigManager().getTpDenyYou());
    }

    public boolean hasRequest(Player target) {
        return activeRequests.containsKey(target.getUniqueId());
    }

    private static class TpaRequest {
        private final UUID senderUuid;
        private final UUID targetUuid;
        private final long timestamp;
        private final String type;

        public TpaRequest(UUID senderUuid, UUID targetUuid, String type) {
            this.senderUuid = senderUuid;
            this.targetUuid = targetUuid;
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }

        public UUID getSenderUuid() { return senderUuid; }
        public String getType() { return type; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TpaRequest that = (TpaRequest) obj;
            return senderUuid.equals(that.senderUuid) &&
                    targetUuid.equals(that.targetUuid) &&
                    timestamp == that.timestamp;
        }
    }
}
