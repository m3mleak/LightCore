package zxc.fxreason.lightCore.economy.commands;

/*
 * By fxreason
 * 23.02.2026
 **/

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

import java.math.BigDecimal;
import java.util.*;

public class Baltop implements CommandExecutor {

    private final LightCore plugin;

    public Baltop(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        LinkedHashMap<UUID, BigDecimal> allTop = plugin.getCustomEconomy().getTopBalances(10);

        if (allTop.isEmpty()) {
            sender.sendMessage("Игроков в топе не найдено");
            return true;
        }

        List<Map.Entry<UUID, BigDecimal>> entries = new ArrayList<>(allTop.entrySet());

        int startIndex = 0;
        int endIndex = Math.min(startIndex + 10, entries.size());

        sender.sendMessage("§b§l╔═══════════════════╗");
        sender.sendMessage("§b§l   ★ §fТОП САМЫХ БОГАТЫХ ИГРОКОВ§b§l ★   ");
        sender.sendMessage("§b§l╠═══════════════════╣");

        for (int i = startIndex; i < endIndex; i++) {
            Map.Entry<UUID, BigDecimal> entry = entries.get(i);
            int position = i + 1;

            String playerName = getPlayerName(entry.getKey());
            String formattedBal = plugin.getCustomEconomy().format(entry.getValue());

            String positionColor;
            String nameColor = "§f";
            String balanceColor = "§a";

            if (position == 1) {
                positionColor = "§6§l";
                nameColor = "§6";
            } else if (position == 2) {
                positionColor = "§7§l";
                nameColor = "§7";
            } else if (position == 3) {
                positionColor = "§c§l";
                nameColor = "§c";
            } else {
                positionColor = "§f";
            }

            String medal = "";
            if (position == 1) medal = "§6👑 ";
            else if (position == 2) medal = "§7🥈 ";
            else if (position == 3) medal = "§c🥉 ";

            String line = String.format("§e%3d. %s%s%s §7- %s%s",
                    position,
                    medal,
                    nameColor,
                    playerName,
                    balanceColor,
                    formattedBal);

            sender.sendMessage(line);
        }

        sender.sendMessage("§b§l╚═══════════════════╝");

        return true;
    }

    private String getPlayerName (UUID uuid) {
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (onlinePlayer != null) {
            return onlinePlayer.getName();
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore() && offlinePlayer.getName() != null) {
            return offlinePlayer.getName();
        }

        String uuidStr = uuid.toString();
        return "Неизвестно (" + uuidStr.substring(0, 6) + "...)";
    }
}
