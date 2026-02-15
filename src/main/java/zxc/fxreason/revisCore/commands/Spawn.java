package zxc.fxreason.revisCore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

public class Spawn implements CommandExecutor {
    private ConfigManager configManager;
    private final RevisCore plugin;

    public Spawn(ConfigManager configManager, RevisCore plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;


        if (args.length == 0) {
            player.sendMessage(configManager.getSuccesTeleportToSpawn());
            player.teleport(loadSpawnLoc());
        } else {
            sender.sendMessage(configManager.getSpawnCorrect());
        }

        return true;
    }

    public Location loadSpawnLoc() {
        FileConfiguration config = plugin.getConfig();
        ConfigManager configManager = plugin.getConfigManager();

        if (config.contains("spawn.world")) {
            Location spawnLocation = new Location(
                    plugin.getServer().getWorld(configManager.getWorld()),
                    configManager.getx(),
                    configManager.gety(),
                    configManager.getz(),
                    configManager.getYaw(),
                    configManager.getPitch()
            );
            return spawnLocation;
        }
        return null;
    }
}
