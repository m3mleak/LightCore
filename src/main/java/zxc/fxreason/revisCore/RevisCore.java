package zxc.fxreason.revisCore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.fxreason.revisCore.commands.*;
import zxc.fxreason.revisCore.economy.CustomEcoLogic;
import zxc.fxreason.revisCore.economy.CustomEcoProvider;
import zxc.fxreason.revisCore.economy.CustomEconomy;
import zxc.fxreason.revisCore.economy.commands.Baltop;
import zxc.fxreason.revisCore.economy.commands.Money;
import zxc.fxreason.revisCore.economy.commands.MoneyGive;
import zxc.fxreason.revisCore.economy.commands.Pay;
import zxc.fxreason.revisCore.economy.listeners.EcoListener;
import zxc.fxreason.revisCore.events.CMoveInvEvent;
import zxc.fxreason.revisCore.events.RespawnEvent;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.DataManager;
import zxc.fxreason.revisCore.managers.TeleportManager;
import zxc.fxreason.revisCore.managers.TpReqManager;
import zxc.fxreason.revisCore.utils.MessageUtil;

public final class RevisCore extends JavaPlugin {

    private ConfigManager configManager;
    private CustomEconomy customEconomy;
    private MessageUtil messageUtil;
    private TpReqManager tpReqManager;
    private TeleportManager teleportManager;
    private DataManager TogglesDataManager;
    private CustomEcoLogic customEcoLogic;
    private Salary salaryInstance;

    public ConfigManager getConfigManager() { return configManager; }
    public CustomEconomy getCustomEconomy() { return customEconomy; }
    public MessageUtil getMessageUtil() { return messageUtil; }
    public TpReqManager getTpReqManager() { return tpReqManager; }
    public TeleportManager getTeleportManager() { return teleportManager; }
    public DataManager getTogglesDataManager() { return TogglesDataManager; }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        initializeManagers();

        setupVault();

        registerCommands();
        registerTabCompletes();
        registerEvents();

        getLogger().info("RevisCore успешно включен!");
    }

    private void initializeManagers() {
        this.configManager = new ConfigManager(getConfig());
        this.messageUtil = new MessageUtil(this);
        this.customEcoLogic = new CustomEcoLogic(this);
        this.customEconomy = new CustomEconomy(customEcoLogic);
        this.tpReqManager = new TpReqManager(this);
        this.salaryInstance = new Salary(this);
        this.teleportManager = new TeleportManager(this);
        this.TogglesDataManager = new DataManager("toggles", this);
    }

    private void registerCommands() {
        registerCommand("setspawn", new SetSpawn(this));
        registerCommand("spawn", new Spawn(this));
        registerCommand("sethome", new SetHome(this));
        registerCommand("home", new Home(this));
        registerCommand("delhome", new DeleteHome(this));
        registerCommand("setwarp", new SetWarp(this));
        registerCommand("warp", new Warp(this));
        registerCommand("reviscore", new Reload(this));

        registerCommand("fly", new Fly(this));
        registerCommand("gm", new Gamemode(this));
        registerCommand("enderchest", new EnderChest(this));
        registerCommand("invsee", new Invsee(this));
        registerCommand("feed", new Feed(this));
        registerCommand("suicide", new Suicide(this));
        registerCommand("workbench", new Workbench(this));
        registerCommand("hat", new Hat(this));
        registerCommand("clear", new Clear(this));

        registerCommand("tpa", new Tpa(this));
        registerCommand("tpaccept", new TpaAccept(this));
        registerCommand("tpdeny", new TpaDeny(this));
        registerCommand("tp", new Tp(this));
        registerCommand("tphere", new Tphere(this));
        registerCommand("tpahere", new Tpahere(this));
        registerCommand("tppos", new TpPos(this));
        registerCommand("tptoggle", new TpToggle(this));
        registerCommand("rtp", new RTP(this));
        registerCommand("near", new Near(this));

        registerCommand("day", new Day(this));
        registerCommand("night", new Night(this));
        registerCommand("sun", new Sun(this));
        registerCommand("rain", new Rain(this));

        registerCommand("money", new Money(this));
        registerCommand("baltop", new Baltop(this));
        registerCommand("pay", new Pay(this));
        registerCommand("givemoney", new MoneyGive(this));
        registerCommand("salary", salaryInstance);
    }

    private void registerTabCompletes() {
        registerTabCompleter("warp", new Warp(this));
    }

    private void registerCommand(String name, CommandExecutor executor) {
        if (getCommand(name) != null) {
            getCommand(name).setExecutor(executor);
        } else {
            getLogger().warning("Команда " + name + " не найдена в plugin.yml");
        }
    }

    private void registerTabCompleter(String name, TabCompleter tabCompleter) {
        if (getCommand(name) != null) {
            getCommand(name).setTabCompleter(tabCompleter);
        } else {
            getLogger().warning("Команда " + name + " не найдена в plugin.yml");
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(salaryInstance, this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(this), this);
        getServer().getPluginManager().registerEvents(new CMoveInvEvent(this), this);
        getServer().getPluginManager().registerEvents(new EcoListener(customEcoLogic), this);
    }

    private void setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().warning("Vault не обнаружен!");
            return;
        }

        try {
            getServer().getServicesManager().register(
                    Economy.class,
                    new CustomEcoProvider(customEconomy),
                    this,
                    ServicePriority.Highest
            );
            getLogger().info("Vault успешно подключен!");
        } catch (Exception e) {
            getLogger().severe("Ошибка подключения Vault: " + e.getMessage());
        }
    }

    public void reloadPlugin() {
        try {
            reloadConfig();

            ConfigManager newConfigManager = new ConfigManager(getConfig());

            this.configManager = newConfigManager;

            getLogger().info("Плагин успешно перезагружен!");

        } catch (Exception e) {
            getLogger().severe("Ошибка при перезагрузке плагина: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (customEcoLogic != null) {
            customEcoLogic.saveAllData();
        }
        getLogger().info("RevisCore отключен!");
    }
}