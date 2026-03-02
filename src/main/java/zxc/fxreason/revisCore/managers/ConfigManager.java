package zxc.fxreason.revisCore.managers;

import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigDecimal;

public class ConfigManager {
    private FileConfiguration config;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public void reload(FileConfiguration newCfg) {
        this.config = newCfg;
        this.config.options().copyDefaults(true);
    }

    public void setSpawnLocation(double x, double y, double z, float yaw, float pitch) {
        this.config.set("spawn.x", Double.valueOf(x));
        this.config.set("spawn.y", Double.valueOf(y));
        this.config.set("spawn.z", Double.valueOf(z));
        this.config.set("spawn.yaw", Float.valueOf(yaw));
        this.config.set("spawn.pitch", Float.valueOf(pitch));
    }

    public void setWorld(String worldName) {
        this.config.set("spawn.world", worldName);
    }

    public boolean isTeleportOnFirstJoin() {
        return this.config.getBoolean("settings.teleport-on-first-join", true);
    }

    public String getNoHomesPoint() {
        return this.config.getString("messages.no-homes-points");
    }

    public String getOneHomesPoint() {
        return this.config.getString("messages.one-point-home");
    }

    public String getHomeDataError() {
        return this.config.getString("messages.home-error");
    }

    public String getMorePointsHome() {
        return this.config.getString("messages.more-points-home");
    }

    public String getTeleportedPlayerToHome() {
        return this.config.getString("messages.teleported-player-home");
    }

    public String getNotFoundPointHome() {
        return this.config.getString("messages.not-found-point-home");
    }

    public String getUsageHome() {
        return this.config.getString("messages.usage-home");
    }

    public String getWorldHomeNotFound() {
        return this.config.getString("messages.world-not-found");
    }

    public String getErrorLoadsCoordHome() {
        return this.config.getString("messages.error-loads-coords");
    }

    public String getEnterHomePoint() {
        return this.config.getString("messages.enter-home-point");
    }

    public String getDuplicateNameHome() {
        return this.config.getString("messages.duplicate-name-home");
    }

    public String getMaxPointsHome() {
        return this.config.getString("messages.max-points-sethome");
    }

    public String getSuccesEnterSetHome() {
        return this.config.getString("messages.succes-enter-sethome");
    }

    public String getSuccesSetSpawn() {
        return this.config.getString("messages.succes-enter-setspawn");
    }

    public String getNoPerms() {
        return this.config.getString("messages.no-permission");
    }

    public String getDuplicateWarp() {
        return this.config.getString("messages.duplicate-warp");
    }

    public String getSetWarpName() {
        return this.config.getString("messages.setwarp-name");
    }

    public String getSetWarpSucces() {
        return this.config.getString("messages.setwarp-succes");
    }

    public String getSpawnCorrect() {
        return this.config.getString("messages.spawn-correct");
    }

    public String getWarpUsage() {
        return this.config.getString("messages.warp-usage");
    }

    public String getWorldNotFoundWarp() {
        return this.config.getString("messages.world-not-found-warp");
    }

    public String getErrorLoadsCoordsWarp() {
        return this.config.getString("messages.error-loads-coords-warp");
    }

    public String getSuccesTeleportToSpawn() {
        return this.config.getString("messages.success-teleport-to-spawn");
    }

    public String getSuccessTeleportToWarp() {
        return this.config.getString("messages.success-teleport-to-warp");
    }

    public String getEnterHomeForDelete() {
        return this.config.getString("messages.enter-home-point-for-del");
    }

    public String getHomePointNotFound() {
        return this.config.getString("messages.home-point-not-found");
    }

    public String getSuccessDeleteHomePoint() {
        return this.config.getString("messages.success-delete-home-point");
    }

    public String getFlyEnable() {
        return this.config.getString("messages.fly-enable");
    }

    public String getFlyDisable() {
        return this.config.getString("messages.fly-disable");
    }

    public String getGamemodeCorrectUse() {
        return this.config.getString("messages.gamemode-correct-use");
    }

    public String getGamemodeInstalled() {
        return this.config.getString("messages.gamemode-installed");
    }

    public String getIncorrectGamemode() {
        return this.config.getString("messages.incorrect-gamemode");
    }

    public String getNicknameNotFound() {
        return this.config.getString("messages.not-found-player");
    }

    public String getTimesUpTp() {
        return this.config.getString("messages.times-up-tp");
    }

    public String getTimesUpTplayer() {
        return this.config.getString("messages.times-up-tplayer");
    }

    public String getNotActiveReq() {
        return this.config.getString("messages.not-active-reqtp");
    }

    public String getSenderTpLeave() {
        return this.config.getString("messages.sender-tp-leave");
    }

    public String getSucessTeleportation() {
        return this.config.getString("messages.succes-teleportation");
    }

    public String getUsageInvsee() {
        return this.config.getString("messages.usage-invsee");
    }

    public String getUsageTpa() {
        return this.config.getString("messages.usage-tpa");
    }

    public String getCooldownCMD() {
        return this.config.getString("messages.cooldown-command");
    }

    public String getNonTpToMe() {
        return this.config.getString("messages.nontp-to-me");
    }

    public String getTpaResponse() {
        return this.config.getString("messages.tpa-response");
    }

    public String getTpaResponseToTaget() {
        return this.config.getString("messages.tpa-response-to-target");
    }

    public String getAcceptDenyTpa() {
        return this.config.getString("messages.accept-deny-tpa");
    }

    public String getNotMessage() {
        return this.config.getString("messages.not-message");
    }

    public String getSuccesTpForYou() {
        return this.config.getString("messages.succes-tp-for-you");
    }

    public String getTpDeny() {
        return this.config.getString("messages.tp-deny");
    }

    public String getTpDenyYou() {
        return this.config.getString("messages.tp-deny-you");
    }

    public String getUsageTp() {
        return this.config.getString("messages.usage-tp");
    }

    public String getNotTpToYou() {
        return this.config.getString("messages.not-tp-to-you");
    }

    public String getFeedUsage() {
        return this.config.getString("messages.feed-usage");
    }

    public String getFeedSuccess() {
        return this.config.getString("messages.feed-success");
    }

    public String getFeedOnlyYou() {
        return this.config.getString("messages.feed-only-you");
    }

    public String getSuicideMsg() {
        return this.config.getString("messages.suicide");
    }

    public String getSuicideUsage() {
        return this.config.getString("messages.suicide-usage");
    }

    public String getSuccessTphere() {
        return this.config.getString("messages.success-tphere");
    }

    public String getUsageTphere() {
        return this.config.getString("messages.tphere-usage");
    }

    public String getNotTphereYou() {
        return this.config.getString("messages.tphere-not-you-to-you");
    }

    public String getTpahereResponse() {
        return this.config.getString("messages.tpahere-response");
    }

    public String getTpahereAccept() {
        return this.config.getString("messages.tpahere-accept");
    }

    public String getTpahereTp() {
        return this.config.getString("messages.tpahere-tp");
    }

    public String getTpahereUsage() {
        return this.config.getString("messages.tpahere-usage");
    }

    public String getSetDay() {
        return this.config.getString("messages.setday");
    }

    public String getDayUsage() {
        return this.config.getString("messages.day-usage");
    }

    public String getSetNight() {
        return this.config.getString("messages.setnight");
    }

    public String getNightUsage() {
        return this.config.getString("messages.night-usage");
    }

    public String getSetSun() {
        return this.config.getString("messages.setsun");
    }

    public String getSunUsage() {
        return this.config.getString("messages.sun-usage");
    }

    public String getSetRain() {
        return this.config.getString("messages.setrain");
    }

    public String getRainUsage() {
        return this.config.getString("messages.rain-usage");
    }

    public String getTpPosSuccess() {
        return this.config.getString("messages.tppos-succcess");
    }

    public String getTpPosUsage() {
        return this.config.getString("messages.tppos-usage");
    }

    public String getMoneyMsg() {
        return this.config.getString("messages.money");
    }

    public String getMoneyPlayerMsg() {
        return this.config.getString("messages.money-player");
    }

    public String getMoneyUsage() {
        return this.config.getString("messages.money-usage");
    }

    public String getPayUsage() {
        return this.config.getString("messages.pay-usage");
    }

    public String getNotPayYou() {
        return this.config.getString("messages.not-pay-you");
    }

    public String getPayNotPostive() {
        return this.config.getString("messages.pay-not-postive");
    }

    public String getPayNotMoney() {
        return this.config.getString("messages.pay-not-money");
    }

    public String getErrAmount() {
        return this.config.getString("messages.err-amount");
    }

    public String getPayNotSend() {
        return this.config.getString("messages.pay-not-send");
    }

    public String getPayMeSend() {
        return this.config.getString("messages.pay-me-send");
    }

    public String getPayToSend() {
        return this.config.getString("messages.pay-to-send");
    }

    public String getMoneyGiveNotPositive() {
        return this.config.getString("messages.givemoney-not-positive");
    }

    public String getGiveMoneyUsage() {
        return this.config.getString("messages.givemoney-usage");
    }

    public String getGiveMoneyMsg() {
        return this.config.getString("messages.givemoney");
    }

    public String getNotFoundNearPLayers() {
        return this.config.getString("messages.not-near-players");
    }

    public String getNearUsage() {
        return this.config.getString("messages.near-usage");
    }

    public String getSalarySuccess() {
        return this.config.getString("messages.salary-success");
    }

    public String getSalaryErr() {
        return this.config.getString("messages.salary-err");
    }

    public String getHatUsage() {
        return this.config.getString("messages.hat-usage");
    }

    public String getHatHelmErr() {
        return this.config.getString("messages.hat-helmet-err");
    }

    public String getHatNotItemMH() {
        return this.config.getString("messages.hat-not-item-mh");
    }

    public String getHatUsageNotBlocks() {
        return this.config.getString("messages.hat-usage-not-blocks");
    }

    public String getHatSuccess() {
        return this.config.getString("messages.hat-success");
    }

    public String getEcUsage() {
        return this.config.getString("messages.ec-usage");
    }

    public String getWbUsage() {
        return this.config.getString("messages.wb-usage");
    }

    public String getPlayerTpToggle() {
        return this.config.getString("messages.player-tptoggle");
    }

    public String getTpStartDntMove() {
        return this.config.getString("messages.tp-start-dntmove");
    }

    public String getTpStartSeven() {
        return this.config.getString("messages.tp-start-seven");
    }

    public String getTpCancelledDmg() {
        return this.config.getString("messages.tp-canceled-dmg");
    }

    public String getSpawnNotPoint() {
        return this.config.getString("messages.spawn-not-point");
    }

    public String getInvseeNameInv() {
        return this.config.getString("inventory_names.invsee");
    }

    public String getSalaryNameInv() {
        return this.config.getString("inventory_names.salary");
    }

    public BigDecimal getDefaultBalance() {
        return BigDecimal.valueOf(this.config.getDouble("settings.default-balance", 100.0));
    }

    public Double getx() {
        return Double.valueOf(this.config.getDouble("spawn.x"));
    }

    public Double gety() {
        return Double.valueOf(this.config.getDouble("spawn.y"));
    }

    public Double getz() {
        return Double.valueOf(this.config.getDouble("spawn.z"));
    }

    public float getYaw() {
        return (float)this.config.getDouble("spawn.yaw");
    }

    public float getPitch() {
        return (float)this.config.getDouble("spawn.pitch");
    }

    public String getWorld() {
        return this.config.getString("spawn.world");
    }
}
