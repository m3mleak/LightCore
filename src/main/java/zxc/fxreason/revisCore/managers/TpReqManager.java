package zxc.fxreason.revisCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import zxc.fxreason.revisCore.RevisCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpReqManager {
    private final Map<UUID, TpaRequest> activeRequests = new HashMap<>();
    private final RevisCore plugin;
    private final ConfigManager configManager;

    private static final int REQUEST_TIMEOUT = 30;

    public TpReqManager(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void sendRequest(Player sender, Player target) {
        activeRequests.remove(target.getUniqueId());

        TpaRequest request = new TpaRequest(sender.getUniqueId(), target.getUniqueId());
        activeRequests.put(target.getUniqueId(), request);

        sender.sendMessage("§aЗапрос на телепортацию отправлен игроку §e" + target.getName());

        target.sendMessage("§e" + sender.getName() + " §aхочет телепортироваться к вам.");
        target.sendMessage("§aИспользуйте §e/tpaccept §aили §e/tpdeny");

        new BukkitRunnable() {
            @Override
            public void run() {
                TpaRequest currentRequest = activeRequests.get(target.getUniqueId());
                if (currentRequest != null && currentRequest.equals(request)) {
                    activeRequests.remove(target.getUniqueId());
                    Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                    Player senderPlayer = Bukkit.getPlayer(sender.getUniqueId());

                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        targetPlayer.sendMessage("§cЗапрос на телепортацию от §e" + sender.getName() + " §cистек.");
                    }
                    if (senderPlayer != null && senderPlayer.isOnline()) {
                        senderPlayer.sendMessage("§cВаш запрос на телепортацию к §e" + target.getName() + " §cистек.");
                    }
                }
            }
        }.runTaskLater(plugin, REQUEST_TIMEOUT * 20L);
    }

    public void acceptRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage("§cУ вас нет активных запросов на телепортацию!");
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender == null || !sender.isOnline()) {
            target.sendMessage("§cИгрок, отправивший запрос, вышел из игры!");
            return;
        }

        sender.teleport(target.getLocation());

        sender.sendMessage("§aТелепортация к §e" + target.getName() + " §aвыполнена!");
        target.sendMessage("§e" + sender.getName() + " §aтелепортировался к вам!");
    }

    public void denyRequest(Player target) {
        TpaRequest request = activeRequests.remove(target.getUniqueId());

        if (request == null) {
            target.sendMessage("§cУ вас нет активных запросов на телепортацию!");
            return;
        }

        Player sender = Bukkit.getPlayer(request.getSenderUuid());

        if (sender != null && sender.isOnline()) {
            sender.sendMessage("§e" + target.getName() + " §cотклонил ваш запрос на телепортацию!");
        }

        target.sendMessage("§cВы отклонили запрос на телепортацию!");
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