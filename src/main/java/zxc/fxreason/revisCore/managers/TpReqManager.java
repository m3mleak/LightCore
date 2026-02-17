package zxc.fxreason.revisCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.utils.MessageUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpReqManager {
    private final Map<UUID, TpaRequest> activeRequests = new HashMap<>();
    private final RevisCore plugin;
    private final ConfigManager configManager;
    private final MessageUtil messageUtil;

    private static final int REQUEST_TIMEOUT = 30;

    public TpReqManager(RevisCore plugin, ConfigManager configManager, MessageUtil messageUtil) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageUtil = messageUtil;
    }

    public void sendRequest(Player sender, Player target) {
        activeRequests.remove(target.getUniqueId());

        TpaRequest request = new TpaRequest(sender.getUniqueId(), target.getUniqueId());
        activeRequests.put(target.getUniqueId(), request);

        String nameTarget = target.getName();
        String namePlayer = sender.getName();

        messageUtil.sendMessage(sender, nameTarget, configManager.getTpaResponse());

        messageUtil.sendMessage(target, namePlayer, configManager.getTpaResponseToTaget());
        target.sendMessage(configManager.getAcceptDenyTpa());

        new BukkitRunnable() {
            @Override
            public void run() {
                TpaRequest currentRequest = activeRequests.get(target.getUniqueId());
                if (currentRequest != null && currentRequest.equals(request)) {
                    activeRequests.remove(target.getUniqueId());
                    Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                    Player senderPlayer = Bukkit.getPlayer(sender.getUniqueId());

                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        messageUtil.sendMessage(targetPlayer, namePlayer, configManager.getTimesUpTp());
                    }
                    if (senderPlayer != null && senderPlayer.isOnline()) {
                        messageUtil.sendMessage(senderPlayer, nameTarget, configManager.getTimesUpTplayer());
                    }
                }
            }
        }.runTaskLater(plugin, REQUEST_TIMEOUT * 20L);
    }

    public void acceptRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage(configManager.getNotActiveReq());
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender == null || !sender.isOnline()) {
            target.sendMessage(configManager.getSenderTpLeave());
            return;
        }

        sender.teleport(target.getLocation());

        messageUtil.sendMessage(sender, target.getName(), configManager.getSucessTeleportation());
        messageUtil.sendMessage(target, sender.getName(), configManager.getSuccesTpForYou());
    }

    public void denyRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage(configManager.getNotActiveReq());
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender != null && sender.isOnline()) {
            messageUtil.sendMessage(sender, target.getName(), configManager.getTpDeny());
        }

        target.sendMessage(configManager.getTpDenyYou());
    }

    public boolean hasRequest(Player target) {
        return activeRequests.containsKey(target.getUniqueId());
    }

    private static class TpaRequest {
        private final UUID senderUuid;
        private final UUID targetUuid;
        private final long timestamp;

        public TpaRequest(UUID senderUuid, UUID targetUuid) {
            this.senderUuid = senderUuid;
            this.targetUuid = targetUuid;
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