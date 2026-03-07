package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

import java.util.UUID;

public class Reply implements CommandExecutor {

    private final RevisCore plugin;

    public Reply(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Использование: /r [сообщение]");
            return true;
        }

        UUID lastTargetUUID = plugin.getMsg().getLastMessage(player.getUniqueId());
        if (lastTargetUUID == null) {
            player.sendMessage("Вам никто не писал!");
            return true;
        }

        Player target = plugin.getServer().getPlayer(lastTargetUUID);
        if (target == null || !target.isOnline()) {
            player.sendMessage("Игрок больше не в сети");
            lastTargetUUID = null;
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }

        String message = messageBuilder.toString().trim();

        String fromFormat = "§7(ЛС) §d[&eЯ §7➩ " + player.getName() + "§d] §f" + message;
        String toFormat = "§7(ЛС) §d[" + player.getName() + " §7➩ &eЯ §d] §f" + message;

        player.sendMessage(fromFormat);
        target.sendMessage(toFormat);

        return true;
    }
}
