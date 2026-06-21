package zxc.fxreason.lightCore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;
import zxc.fxreason.lightCore.managers.ConfigManager;
import zxc.fxreason.lightCore.managers.CooldownManager;

public class Spawn implements CommandExecutor {

    private final LightCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Spawn(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        long timeLeft = this.cdManager.getCooldown(player);
        int cooldown = 15;

        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 0) {
            teleportToSpawn(player);
            this.cdManager.setCooldown(player, cooldown);
            player.sendMessage(plugin.getConfigManager().getTpStartSeven());
        } else {
            sender.sendMessage(plugin.getConfigManager().getSpawnCorrect());
        }

        return true;
    }

    private void teleportToSpawn(Player player) {
        Location loc = loadSpawnLoc();
        if (loc == null) {
            player.sendMessage(plugin.getConfigManager().getSpawnNotPoint());
            return;
        }
        plugin.getTeleportManager().startTeleport(player, plugin.getConfigManager().getSuccesTeleportToSpawn(), loc, 7);

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
