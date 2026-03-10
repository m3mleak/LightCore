package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

import java.util.UUID;

public class MsgToggle implements CommandExecutor {

    private final RevisCore plugin;;

    public MsgToggle(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (!player.hasPermission("reviscore.msgtoggle")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(plugin.getConfigManager().getMsgToggleUsage());
            return true;
        }

        UUID uuid = player.getUniqueId();
        boolean currentState = plugin.getDirectMessageManager().isMsgToggleEnabled(uuid);
        boolean newState = !currentState;

        plugin.getDirectMessageManager().setMsgToggle(uuid, newState);

        player.sendMessage(newState ? plugin.getConfigManager().getMsgToggleOff() : plugin.getConfigManager().getMsgToggleOn());

        plugin.getTogglesDataManager().getConfig().set("players.msgtoggle." + uuid, newState);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getTogglesDataManager().save();
        });

        return true;
    }
}
