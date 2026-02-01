package zxc.fxreason.revisCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.fxreason.revisCore.commands.*;
import zxc.fxreason.revisCore.events.RespawnEvent;
import zxc.fxreason.revisCore.manager.ConfigManager;

public final class RevisCore extends JavaPlugin {

    private ConfigManager configManager;
    private static RevisCore instance;

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static RevisCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        this.configManager = new ConfigManager(getConfig());

        // setspawn
        getCommand("setspawn").setExecutor(new SetSpawn(configManager));

        // spawn
        getCommand("spawn").setExecutor(new Spawn(this));

        // sethome
        getCommand("sethome").setExecutor(new SetHome(this));

        // home
        getCommand("home").setExecutor(new Home(this));

        // setwarp

        // warp

        // reviscore reload
        getCommand("reviscore").setExecutor(new Reload(this, configManager));

        // respawn event
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(this), this);

        initConfig();
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();

        getLogger().info("Disabled");

    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
}
