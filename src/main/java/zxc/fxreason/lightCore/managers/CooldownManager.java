package zxc.fxreason.lightCore.managers;

/*
 * By fxreason
 * 15.02.2026
 **/

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public void setCooldown(Player player, int seconds) {
        long delay = System.currentTimeMillis() + (long)(seconds * 1000);
        cooldowns.put(player.getUniqueId(), delay);
    }

    public long getCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return 0;
        }
        long expiredTime = cooldowns.get(player.getUniqueId()); // last point time
        long timeLeft = (expiredTime - System.currentTimeMillis()) / 1000;
        if (timeLeft < 0) {
            cooldowns.remove(player.getUniqueId());
            return 0;
        }
        return timeLeft;
    }

}
