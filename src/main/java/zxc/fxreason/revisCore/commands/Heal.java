package zxc.fxreason.revisCore.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Heal implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Heal(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (!player.hasPermission("reviscore.heal")) {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        long timeLeft = cdManager.getCooldown(player);
        if (timeLeft > 0) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 0) {
            player.heal(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            player.sendMessage(plugin.getConfigManager().getHealSuccess());
            cdManager.setCooldown(player, 30);
            return true;
        } else if (args.length == 1) {
            if (!player.hasPermission("reviscore.heal-players")) {
                player.sendMessage(plugin.getConfigManager().getNoPerms());
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
                return true;
            }

            target.heal(target.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            player.sendMessage(plugin.getConfigManager().getHealSuccessPlayer());
            cdManager.setCooldown(player, 30);
            return true;
        }

        return true;
    }
}
