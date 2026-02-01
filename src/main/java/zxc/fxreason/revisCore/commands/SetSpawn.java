package zxc.fxreason.revisCore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.manager.ConfigManager;

public class SetSpawn implements CommandExecutor {

    private ConfigManager configManager;

    public SetSpawn(ConfigManager configManager) {
        this.configManager = configManager;
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

            configManager.setSpawnLocation(x, y, z, yaw, pitch);
            configManager.setWorld(world);

            player.sendMessage("§aТочка спавна установлена!");
        } else {
            player.sendMessage("§cНедостаточно прав!");
        }

        return true;
    }
}
