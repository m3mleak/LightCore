package zxc.fxreason.revisCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.fxreason.revisCore.commands.*;
import zxc.fxreason.revisCore.events.CMoveInvEvent;
import zxc.fxreason.revisCore.events.RespawnEvent;
import zxc.fxreason.revisCore.managers.ConfigManager;

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
        getCommand("setspawn").setExecutor(new SetSpawn(configManager, this));

        // spawn
        getCommand("spawn").setExecutor(new Spawn(configManager,this));

        // sethome
        getCommand("sethome").setExecutor(new SetHome(configManager, this));

        // home
        getCommand("home").setExecutor(new Home(configManager, this));

        // delhome
        getCommand("delhome").setExecutor(new DeleteHome(this, configManager));

        // setwarp
        getCommand("setwarp").setExecutor(new SetWarp(configManager,this));

        // warp
        getCommand("warp").setExecutor(new Warp(configManager, this));

        // reviscore reload
        getCommand("reviscore").setExecutor(new Reload(this, configManager));

        // fly
        getCommand("fly").setExecutor(new Fly(this, configManager));

        // gm
        getCommand("gm").setExecutor(new Gamemode(this, configManager));

        // ec
        getCommand("enderchest").setExecutor(new EnderChest());

        // invsee
        getCommand("invsee").setExecutor(new Invsee(this, configManager));

        // tpa
        getCommand("tpa").setExecutor(new Tpa(configManager));

        // respawn event
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(this), this);

        // canceled move inv event
        Bukkit.getPluginManager().registerEvents(new CMoveInvEvent(this, configManager), this);

        initConfig();
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();

        System.out.println();

        getLogger().info("Disabled");

    }

    public boolean updateCfgManager(ConfigManager newManager) {
        try {
            this.configManager = newManager;
            return true;
        } catch (Exception e) {
            getLogger().severe("Не удалось обновить ConfigManager: " + e.getMessage());
            return false;
        }
    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
}
