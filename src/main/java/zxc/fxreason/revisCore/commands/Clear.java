package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Clear implements CommandExecutor {

    private final RevisCore plugin;

    public Clear(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            player.getInventory().clear();
            player.sendMessage(plugin.getConfigManager().getClearSuccess());
            return true;
        } else if (args.length == 1) {
            if (sender.hasPermission("reviscore.clear-all")) {
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target != null) {
                    target.getInventory().clear();
                } else {
                    sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                }
                sender.sendMessage(plugin.getConfigManager().getClearSuccess());
            }
            return true;
        } else {
            sender.sendMessage(plugin.getConfigManager().getClearUsage());
        }
        return true;
    }
}
