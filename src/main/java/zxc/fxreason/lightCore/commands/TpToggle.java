package zxc.fxreason.lightCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

import java.util.UUID;

public class TpToggle implements CommandExecutor {

    private final LightCore plugin;

    public TpToggle(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (!player.hasPermission("reviscore.tptoggle")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(plugin.getConfigManager().getTpToggleUsage());
            return true;
        }

        UUID uuid = player.getUniqueId();
        boolean currentState = plugin.getTpReqManager().isTpToggleEnabled(uuid);
        boolean newState = !currentState;

        plugin.getTpReqManager().setTpToggle(uuid, newState);

        player.sendMessage(newState ? plugin.getConfigManager().getTpToggleOff() : plugin.getConfigManager().getTpToggleOn());

        plugin.getTogglesDataManager().getConfig().set("players.tptoggle." + uuid, newState);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getTogglesDataManager().save();
        });

        return true;
    }
}
