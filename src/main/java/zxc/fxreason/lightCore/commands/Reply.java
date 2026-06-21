package zxc.fxreason.lightCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;
import zxc.fxreason.lightCore.managers.CooldownManager;

import java.util.UUID;

public class Reply implements CommandExecutor {

    private final LightCore plugin;
    private final CooldownManager cdManager = new CooldownManager();

    public Reply(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        long timeLeft = cdManager.getCooldown(player);
        if (timeLeft > 0) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
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

        if (plugin.getDirectMessageManager().isMsgToggleEnabled(target.getUniqueId())) {
            player.sendMessage("Игрок отлкючил личные сообщения!");
            return true;
        }

        if (plugin.getDirectMessageManager().isMsgToggleEnabled(player.getUniqueId())) {
            player.sendMessage("У вас отключены личные сообщения!");
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }

        String message = messageBuilder.toString().trim();

        String fromFormat = "§7(ЛС) §d[§eЯ §7➩ §f" + player.getName() + "§d] §f" + message;
        String toFormat = "§7(ЛС) §d[§e" + player.getName() + " §7➩ §fЯ §d] §f" + message;

        player.sendMessage(fromFormat);
        target.sendMessage(toFormat);

        cdManager.setCooldown(player, 3);

        return true;
    }
}
