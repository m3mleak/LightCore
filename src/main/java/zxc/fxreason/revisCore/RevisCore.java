package zxc.fxreason.revisCore;

import net.thenextlvl.service.api.economy.EconomyController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.fxreason.revisCore.commands.*;
import zxc.fxreason.revisCore.economy.CustomEcoLogic;
import zxc.fxreason.revisCore.economy.CustomEconomy;
import zxc.fxreason.revisCore.economy.commands.Money;
import zxc.fxreason.revisCore.economy.listeners.EcoListener;
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
        initConfig();
        // Plugin startup logic
        this.configManager = new ConfigManager(getConfig());
        MessageUtil messageUtil = new MessageUtil(configManager);
        TpReqManager tpReqManager = new TpReqManager(this, configManager, messageUtil);
        CustomEcoLogic customEcoLogic = new CustomEcoLogic(this, configManager);
        CustomEconomy customEconomy = new CustomEconomy(customEcoLogic);

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

        // day
        getCommand("day").setExecutor(new Day(configManager));

        // night
        getCommand("night").setExecutor(new Night(configManager));

        // sun
        getCommand("sun").setExecutor(new Sun(configManager));

        // rain
        getCommand("rain").setExecutor(new Rain(configManager));

        // tppos
        getCommand("tppos").setExecutor(new TpPos(configManager));

        // money
        getCommand("money").setExecutor(new Money(customEconomy, configManager));

        // respawn event
        getServer().getPluginManager().registerEvents(new RespawnEvent(this), this);

        // canceled move inv event
        getServer().getPluginManager().registerEvents(new CMoveInvEvent(this, configManager), this);

        // join/quit events for economy
        getServer().getPluginManager().registerEvents(new EcoListener(customEcoLogic), this);

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
        reloadConfig();
    }

}
