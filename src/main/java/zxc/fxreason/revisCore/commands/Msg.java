package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

import java.util.HashMap;
import java.util.UUID;

public class Msg implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();
    private HashMap<UUID, UUID> lastMessage = new HashMap<>();

    public Msg(RevisCore plugin) {
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

        if (args.length < 2) {
            player.sendMessage(plugin.getConfigManager().getMsgUsage());
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(plugin.getConfigManager().getMsgDontSendMe());
            return true;
        }

        if (plugin.getDirectMessageManager().isMsgToggleEnabled(target.getUniqueId())) {
            player.sendMessage(plugin.getConfigManager().getMsgToggleTarget());
            return true;
        }

        if (plugin.getDirectMessageManager().isMsgToggleEnabled(player.getUniqueId())) {
            player.sendMessage(plugin.getConfigManager().getMsgTogglePlayer());
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String message = messageBuilder.toString().trim();

        lastMessage.put(player.getUniqueId(), target.getUniqueId());
        lastMessage.put(target.getUniqueId(), player.getUniqueId());

        String fromFormat = "§7(ЛС) §d[§eЯ §7➩ §f" + player.getName() + "§d] §f" + message;
        String toFormat = "§7(ЛС) §d[§e" + player.getName() + " §7➩ §fЯ§d] §f" + message;

        player.sendMessage(fromFormat);
        target.sendMessage(toFormat);

        cdManager.setCooldown(player, 3);

        return true;
    }

    public UUID getLastMessage(UUID playerUUID) {
        return lastMessage.get(playerUUID);
    }
}
