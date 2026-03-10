package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Ad implements CommandExecutor {

    private final RevisCore plugin;

    public Ad(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        if (!player.hasPermission("reviscore.ad")) {
            plugin.getConfigManager().getNoPerms();
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Использование: /ad [рекламное сообщение]");
            return true;
        }

        StringBuilder adBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            adBuilder.append(args[i]).append(" ");
        }

        String ad = adBuilder.toString().trim();

        String toFormat = "§4§lРЕКЛАМА §7➩ §f" + ad;

        for (Player target : plugin.getServer().getOnlinePlayers()) {
            target.sendMessage(toFormat);
        }

        return true;
    }
}
