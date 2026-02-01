package zxc.fxreason.revisCore.manager;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private FileConfiguration config;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public void setSpawnLocation(double x, double y, double z, float yaw, float pitch) {
        config.set("spawn.x", x);
        config.set("spawn.y", y);
        config.set("spawn.z", z);
        config.set("spawn.yaw", yaw);
        config.set("spawn.pitch", pitch);
    }

    public void setWorld(String worldName) {
        config.set("spawn.world", worldName);
    }

    public boolean isTeleportOnFirstJoin() {
        return config.getBoolean("settings.teleport-on-first-join", true);
    }

    public Double getx() {
        return config.getDouble("spawn.x");
    }

    public Double gety() {
        return config.getDouble("spawn.y");
    }

    public Double getz() {
        return config.getDouble("spawn.z");
    }

    public float getYaw() {
        return (float) config.getDouble("spawn.yaw");
    }

    public float getPitch() {
        return (float) config.getDouble("spawn.pitch");
    }

    public String getWorld() {
        return config.getString("spawn.world");
    }
}
