package zxc.fxreason.lightCore.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Gamemode implements CommandExecutor {

    private final LightCore plugin;

    public Gamemode(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("reviscore.gamemode")) {
            if (args.length == 0) {
                player.sendMessage(plugin.getConfigManager().getGamemodeCorrectUse());
                return true;
            } else if (args.length == 1) {
                int gamemode = Integer.parseInt(args[0]);

                switch (gamemode) {
                    case 1 -> {
                        player.sendMessage(plugin.getConfigManager().getGamemodeInstalled() + "креатив");
                        player.setGameMode(GameMode.CREATIVE);
                    }
                    case 0 -> {
                        player.sendMessage(plugin.getConfigManager().getGamemodeInstalled() + "выживание");
                        player.setGameMode(GameMode.SURVIVAL);
                    }
                    case 2 -> {
                        player.sendMessage(plugin.getConfigManager().getGamemodeInstalled() + "приключение");
                        player.setGameMode(GameMode.ADVENTURE);
                    }
                    case 3 -> {
                        player.sendMessage(plugin.getConfigManager().getGamemodeInstalled() + "наблюдатель");
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                    default -> {
                        player.sendMessage(plugin.getConfigManager().getIncorrectGamemode());
                    }
            }

            return true;
            } else {
                player.sendMessage(plugin.getConfigManager().getGamemodeCorrectUse());
                return true;
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }
    }
}
