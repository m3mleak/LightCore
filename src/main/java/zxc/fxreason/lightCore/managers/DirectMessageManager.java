package zxc.fxreason.lightCore.managers;

import zxc.fxreason.lightCore.LightCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DirectMessageManager {

    private final LightCore plugin;

    private final Map<UUID, Boolean> MsgToggles = new HashMap<>();

    public DirectMessageManager(LightCore plugin) {
        this.plugin = plugin;
        loadToggles();
    }

    public void setMsgToggle(UUID uuid, boolean state) {
        MsgToggles.put(uuid, state);
    }

    public boolean isMsgToggleEnabled(UUID uuid) {
        return MsgToggles.getOrDefault(uuid, false);
    }

    private void loadToggles() {
        var config = plugin.getTogglesDataManager().getConfig();
        var section = config.getConfigurationSection("players.msgtoggle");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                MsgToggles.put(UUID.fromString(key), config.getBoolean("players.msgtoggle." + key));
            }
        }
    }
}
