package zxc.fxreason.lightCore.commands;

/*
 * By fxreason
 * 21.02.2026
 **/

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class TpPos implements CommandExecutor {

    private final LightCore plugin;

    public TpPos(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.tppos")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length == 3) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);

            World world = player.getWorld();

            Location loc = new Location(world, x, y, z);

            player.teleport(loc);

            player.sendMessage(plugin.getConfigManager().getTpPosSuccess());

            return true;
        } else {
            player.sendMessage(plugin.getConfigManager().getTpPosUsage());

            return true;
        }
    }
}
