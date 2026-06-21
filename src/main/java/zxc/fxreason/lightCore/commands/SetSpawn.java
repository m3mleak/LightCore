package zxc.fxreason.lightCore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class SetSpawn implements CommandExecutor {

    private final LightCore plugin;

    public SetSpawn(LightCore plugin) {
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

            player.sendMessage(plugin.getConfigManager().getSuccesSetSpawn());
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
        }

        return true;
    }

    public void saveSpawn(double x, double y, double z, float yaw, float pitch, String world) {
        plugin.getConfigManager().setSpawnLocation(x, y, z, yaw, pitch);
        plugin.getConfigManager().setWorld(world);
        plugin.saveConfig();
        plugin.reloadConfig();
        plugin.getConfigManager().reload(plugin.getConfig());
    }
}
