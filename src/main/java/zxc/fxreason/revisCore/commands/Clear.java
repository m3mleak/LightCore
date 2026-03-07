package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Clear implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Clear(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        Player player = (Player) sender;
        long timeLeft = this.cdManager.getCooldown(player);

        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 0) {
            if (!sender.hasPermission("reviscore.clear")) {
                player.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            player.getInventory().clear();
            player.sendMessage(plugin.getConfigManager().getClearSuccess());
            this.cdManager.setCooldown(player, 15);

            return true;
        } else if (args.length == 1) {
            if (!sender.hasPermission("reviscore.clear-all")) {
                player.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                target.getInventory().clear();
            } else {
                sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            }
            sender.sendMessage(plugin.getConfigManager().getClearSuccess());

            return true;
        } else {
            sender.sendMessage(plugin.getConfigManager().getClearUsage());
        }
        return true;
    }
}
