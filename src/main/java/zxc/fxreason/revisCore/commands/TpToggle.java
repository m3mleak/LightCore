package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpToggle implements CommandExecutor {

    private final RevisCore plugin;
    private final Map<UUID, Boolean> TpToggles = new HashMap<>();

    public TpToggle(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("reviscore.tptoggle")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (plugin.getTogglesDataManager().getConfig().getConfigurationSection("players") != null) {
            for (String key : plugin.getTogglesDataManager().getConfig().getConfigurationSection("players").getKeys(false)) {
                TpToggles.put(UUID.fromString(key), plugin.getTogglesDataManager().getConfig().getBoolean("players." + key));
            }
        }

        if (args.length > 0) {
            player.sendMessage(plugin.getConfigManager().getTpToggleUsage());
            return true;
        }

        boolean current = TpToggles.getOrDefault(player.getUniqueId(), false);
        TpToggles.put(player.getUniqueId(), !current);

        if (!current) {
            player.sendMessage(plugin.getConfigManager().getTpToggleOn());
        } else {
            player.sendMessage(plugin.getConfigManager().getTpToggleOff());
        }

        for (Map.Entry<UUID, Boolean> entry : TpToggles.entrySet()) {
            plugin.getTogglesDataManager().getConfig().set("players." + entry.getKey().toString(), entry.getValue());
        }
        plugin.getTogglesDataManager().save();

        return true;
    }
}
