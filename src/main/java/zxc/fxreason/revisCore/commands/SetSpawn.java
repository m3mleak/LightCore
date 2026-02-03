package zxc.fxreason.revisCore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

public class SetSpawn implements CommandExecutor {

    private ConfigManager configManager;
    private final RevisCore plugin;

    public SetSpawn(ConfigManager configManager, RevisCore plugin) {
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

        if (sender.hasPermission("reviscore.setspawn")) {
            Location loc = player.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float yaw = loc.getYaw();
            float pitch = loc.getPitch();

            String world = loc.getWorld().getName();

            saveSpawn(x, y, z, yaw, pitch, world);

            player.sendMessage("§aТочка спавна установлена!");
        } else {
            player.sendMessage("§cНедостаточно прав!");
        }

        return true;
    }

    public void saveSpawn(double x, double y, double z, float yaw, float pitch, String world) {
        configManager.setSpawnLocation(x, y, z, yaw, pitch);
        configManager.setWorld(world);
        plugin.saveConfig();
        plugin.reloadConfig();
        configManager.reload(plugin.getConfig());
    }
}
