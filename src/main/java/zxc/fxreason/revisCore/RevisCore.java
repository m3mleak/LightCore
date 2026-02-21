package zxc.fxreason.revisCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.fxreason.revisCore.commands.*;
import zxc.fxreason.revisCore.events.CMoveInvEvent;
import zxc.fxreason.revisCore.events.RespawnEvent;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.TpReqManager;
import zxc.fxreason.revisCore.utils.MessageUtil;

public final class RevisCore extends JavaPlugin {

    private ConfigManager configManager;

    public ConfigManager getConfigManager() {
        return configManager;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.configManager = new ConfigManager(getConfig());
        MessageUtil messageUtil = new MessageUtil(configManager);
        TpReqManager tpReqManager = new TpReqManager(this, configManager, messageUtil);

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
        getCommand("tpa").setExecutor(new Tpa(configManager, tpReqManager));

        // tpaccept
        getCommand("tpaccept").setExecutor(new TpaAccept(tpReqManager));

        // tpdeny
        getCommand("tpdeny").setExecutor(new TpaDeny(tpReqManager));

        // tp
        getCommand("tp").setExecutor(new Tp(configManager, messageUtil));

        // feed
        getCommand("feed").setExecutor(new Feed(configManager));

        // suicide
        getCommand("suicide").setExecutor(new Suicide(configManager));

        // tphere
        getCommand("tphere").setExecutor(new Tphere(configManager));

        // tpahere
        getCommand("tpahere").setExecutor(new Tpahere(tpReqManager, configManager));

        // workbench
        getCommand("workbench").setExecutor(new Workbench(configManager));

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
