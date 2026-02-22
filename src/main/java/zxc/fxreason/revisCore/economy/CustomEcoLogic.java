package zxc.fxreason.revisCore.economy;

/*
 * By fxreason
 * 23.02.2026
 **/

import org.bukkit.configuration.file.YamlConfiguration;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomEcoLogic implements EconomyAPI{

    private final Map<UUID, BigDecimal> balances = new ConcurrentHashMap<>();
    private final RevisCore plugin;
    private final File dataEconomy;
    private ConfigManager configManager;
    private final BigDecimal defaultBalance;

    public CustomEcoLogic(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.dataEconomy = new File(plugin.getDataFolder(), "economydata");
        this.configManager = configManager;
        this.defaultBalance = configManager.getDefaultBalance();
        if (!dataEconomy.exists()) dataEconomy.mkdirs();
    }

    @Override
    public void loadPlayerData(UUID uuid) {
        File playerFile = new File(dataEconomy, uuid.toString() + ".yml");
        if (playerFile.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
            double balance = config.getDouble("balance", defaultBalance.doubleValue());
            balances.put(uuid, BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_EVEN));
        } else {
            balances.put(uuid, defaultBalance.setScale(2, RoundingMode.HALF_EVEN));

        }
    }

    @Override
    public void savePlayerData(UUID uuid) {
        BigDecimal balance = balances.get(uuid);
        if (balance == null) return;

        File playerFile = new File(dataEconomy, uuid.toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("balance", balance.doubleValue());
        config.set("uuid", uuid.toString());

        try {
            config.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Не удалось сохранить данные об экономике игрока " + uuid);
        }
    }

    @Override
    public void saveAllData() {
        for (UUID uuid : balances.keySet()) {
            savePlayerData(uuid);
        }
    }

    @Override
    public boolean hasAccount(UUID uuid) {
        return balances.containsKey(uuid);
    }

    public Map<UUID, BigDecimal> getBalances() {
        return balances;
    }

    public BigDecimal getDefaultBalance() {
        return defaultBalance;
    }
}
