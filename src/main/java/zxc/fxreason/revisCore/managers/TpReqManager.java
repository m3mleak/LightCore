package zxc.fxreason.revisCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import zxc.fxreason.revisCore.RevisCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpReqManager {

    private final Map<UUID, TpaRequest> activeRequests = new HashMap<>();
    private final RevisCore plugin;

    private static final int REQUEST_TIMEOUT = 30;

    public TpReqManager(RevisCore plugin) {
        this.plugin = plugin;
    }

    public void sendRequest(Player sender, Player target, String type) {
        activeRequests.remove(target.getUniqueId());

        TpaRequest request = new TpaRequest(sender.getUniqueId(), target.getUniqueId(), type);
        activeRequests.put(target.getUniqueId(), request);

        String nameTarget = target.getName();
        String namePlayer = sender.getName();

        // запрос отправлен
        plugin.getMessageUtil().sendMessage(sender, nameTarget, plugin.getConfigManager().getTpaResponse());

        // игрок хочет телепортироваться к вам
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

        Location senderStartLoc = sender.getLocation();
        Location targetStartLoc = target.getLocation();

        BossBar bossBar = Bukkit.createBossBar(
                "§bТелепортация через §a7 §bсекунд...",
                BarColor.BLUE,
                BarStyle.SOLID
        );

        if (request.getType().equals("tpa")) {
            bossBar.addPlayer(sender);
            sender.sendMessage(plugin.getConfigManager().getTpStartDntMove());
        } else {
            bossBar.addPlayer(target);
            target.sendMessage(plugin.getConfigManager().getTpStartDntMove());
        }

        new BukkitRunnable() {
            int secondsLeft = 7;

            @Override
            public void run() {
                if (!sender.isOnline() || !target.isOnline()) {
                    bossBar.removeAll();
                    cancel();
                    return;
                }

                if (request.getType().equals("tpa")) {
                    if (hasMoved(sender, senderStartLoc)) {
                        bossBar.removeAll();
                        sender.sendMessage(plugin.getConfigManager().getTpCancelMove());
                        cancel();
                        return;
                    }
                } else {
                    if (hasMoved(target, targetStartLoc)) {
                        bossBar.removeAll();
                        sender.sendMessage(plugin.getConfigManager().getTpCancelMove());
                        cancel();
                        return;
                    }
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
                bossBar.setProgress(secondsLeft / 7.0);

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

    public static boolean hasMoved(Player player, Location startLoc) {
        Location currentLoc = player.getLocation();
        return currentLoc.getBlockX() != startLoc.getBlockX() ||
                currentLoc.getBlockY() != startLoc.getBlockY() ||
                currentLoc.getBlockZ() != startLoc.getBlockZ();
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

        public UUID getSenderUuid() {
            return senderUuid;
        }

        public UUID getTargetUuid() {
            return targetUuid;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getType() {
            return type;
        }

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