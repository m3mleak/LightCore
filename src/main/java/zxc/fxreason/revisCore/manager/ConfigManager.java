package zxc.fxreason.revisCore.manager;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private FileConfiguration config;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public void reload(FileConfiguration newCfg) {
        this.config = newCfg;
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

    public String getNoHomesPoint() {
        return config.getString("messages.no-homes-points");
    }

    public String getOneHomesPoint() {
        return config.getString("messages.one-point-home");
    }

    public String getHomeDataError() {
        return config.getString("messages.home-error");
    }

    public String getMorePointsHome() {
        return config.getString("messages.more-points-home");
    }

    public String getTeleportedPlayerToHome() {
        return config.getString("messages.teleported-player-home");
    }

    public String getNotFoundPointHome() {
        return config.getString("messages.not-found-point-home");
    }

    public String getUsageHome() {
        return config.getString("messages.usage-home");
    }

    public String getWorldHomeNotFound() {
        return config.getString("messages.world-not-found");
    }

    public String getErrorLoadsCoordHome() {
        return config.getString("messages.error-loads-coords");
    }

    public String getEnterHomePoint() {
        return config.getString("messages.enter-home-point");
    }

    public String getDuplicateNameHome() {
        return config.getString("messages.duplicate-name-home");
    }

    public String getMaxPointsHome() {
        return config.getString("messages.max-points-sethome");
    }

    public String getSuccesEnterSetHome() {
        return config.getString("messages.succes-enter-sethome");
    }

    public String getSuccesSetSpawn() {
        return config.getString("messages.succes-enter-setspawn");
    }

    public String getNoPerms() {
        return config.getString("messages.no-permission");
    }

    public String getDuplicateWarp() {
        return config.getString("messages.duplicate-warp");
    }

    public String getSetWarpName() {
        return config.getString("messages.setwarp-name");
    }

    public String getSetWarpSucces() {
        return config.getString("messages.setwarp-succes");
    }

    public String getSpawnCorrect() {
        return config.getString("messages.spawn-correct");
    }

    public String getWarpUsage() {
        return config.getString("messages.warp-usage");
    }

    public String getWorldNotFoundWarp() {
        return config.getString("messages.world-not-found-warp");
    }

    public String getErrorLoadsCoordsWarp() {
        return config.getString("messages.error-loads-coords-warp");
    }

    public String getSuccesTeleportToSpawn() {
        return config.getString("messages.success-teleport-to-spawn");
    }

    public String getSuccessTeleportToWarp() {
        return config.getString("messages.success-teleport-to-warp");
    }

    public String getEnterHomeForDelete() {
        return config.getString("messages.enter-home-point-for-del");
    }

    public String getHomePointNotFound() {
        return config.getString("messages.home-point-not-found");
    }

    public String getSuccessDeleteHomePoint() {
        return config.getString("messages.success-delete-home-point");
    }

    public String getFlyEnable() {
        return config.getString("messages.fly-enable");
    }

    public String getFlyDisable() {
        return config.getString("messages.fly-disable");
    }

    public String getGamemodeCorrectUse() {
        return config.getString("messages.gamemode-correct-use");
    }

    public String getGamemodeInstalled() {
        return config.getString("messages.gamemode-installed");
    }

    public String getIncorrectGamemode() {
        return config.getString("messages.incorrect-gamemode");
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
