package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

public class Ad implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

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
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(plugin.getConfigManager().getAdUsage());
            return true;
        }

        long timeLeft = cdManager.getCooldown(player);
        if (timeLeft > 0) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        StringBuilder adBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            adBuilder.append(args[i]).append(" ");
        }

        String ad = adBuilder.toString().trim();

        String toFormat = "§4§lРЕКЛАМА §7➩ §f" + ad + " §7(" + player.getName() + ")";

        for (Player target : plugin.getServer().getOnlinePlayers()) {
            target.sendMessage(toFormat);
        }

        cdManager.setCooldown(player, 30);

        return true;
    }
}
