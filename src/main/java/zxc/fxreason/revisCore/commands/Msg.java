package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

import java.util.HashMap;
import java.util.UUID;

public class Msg implements CommandExecutor {

    private final RevisCore plugin;
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

        Player target = plugin.getServer().getPlayer(args[0]);

        if (args.length < 2) {
            player.sendMessage("Использование: ");
            return true;
        }

        if (target == null) {
            player.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage("Нельзя отправить сообщение самому себе");
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String message = messageBuilder.toString().trim();

        lastMessage.put(player.getUniqueId(), target.getUniqueId());
        lastMessage.put(target.getUniqueId(), player.getUniqueId());

        String fromFormat = "§7(ЛС) §d[&eЯ §7➩ " + player.getName() + "§d] §f" + message;
        String toFormat = "§7(ЛС) §d[" + player.getName() + " §7➩ &eЯ §d] §f" + message;

        player.sendMessage(fromFormat);
        target.sendMessage(toFormat);

        return true;
    }

    public UUID getLastMessage(UUID playerUUID) {
        return lastMessage.get(playerUUID);
    }
}
