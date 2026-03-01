package zxc.fxreason.revisCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Spawn implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Spawn(RevisCore plugin) {
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
        int cooldown = 10;

        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(plugin.getConfigManager().getSuccesTeleportToSpawn());
            teleportToSpawn(player);
            this.cdManager.setCooldown(player, cooldown);
        } else {
            sender.sendMessage(plugin.getConfigManager().getSpawnCorrect());
        }

        return true;
    }

    private void teleportToSpawn(Player player) {
        Location loc = loadSpawnLoc();

        BossBar bossBar = Bukkit.createBossBar("§bТелепортация через §a7 §bсекунд...", BarColor.BLUE, BarStyle.SOLID);
        bossBar.addPlayer(player);
        player.sendMessage(plugin.getConfigManager().getTpStartSeven());

        new BukkitRunnable() {
            int secondsLeft = 7;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    bossBar.removeAll();
                    cancel();
                    return;
                }

                if (secondsLeft <= 0) {
                    player.teleport(loc);
                    bossBar.removeAll();
                    cancel();
                    return;
                }

                bossBar.setTitle("§bТелепортация через §a" + secondsLeft + " §bсекунд...");
                bossBar.setProgress(secondsLeft / 7.0);

                secondsLeft--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
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
