package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

public class Fly implements CommandExecutor {
    private final RevisCore plugin;
    private ConfigManager configManager;

    public Fly(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("reviscore.fly")) {
            boolean newState = !player.getAllowFlight();
            player.setAllowFlight(newState);

            if (!newState) {
                player.setFlying(false);
            }

            String stateMessage = newState ? configManager.getFlyEnable() : configManager.getFlyDisable();

            player.sendMessage(stateMessage);

        } else {
            player.sendMessage(configManager.getNoPerms());
        }

        return true;
    }
}
