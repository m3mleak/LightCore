package zxc.fxreason.revisCore.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

public class Gamemode implements CommandExecutor {
    private final RevisCore plugin;
    private ConfigManager configManager;

    public Gamemode(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
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
                player.sendMessage(configManager.getGamemodeCorrectUse());
                return true;
            } else if (args.length == 1) {
                int gamemode = Integer.parseInt(args[0]);

                switch (gamemode) {
                    case 1:
                        player.sendMessage(configManager.getGamemodeInstalled() + "креатив");
                        player.setGameMode(GameMode.CREATIVE);
                        break;
                    case 0:
                        player.sendMessage(configManager.getGamemodeInstalled() + "выживание");
                        player.setGameMode(GameMode.SURVIVAL);
                        break;
                    case 2:
                        player.sendMessage(configManager.getGamemodeInstalled() + "приключение");
                        player.setGameMode(GameMode.ADVENTURE);
                        break;
                    case 3:
                        player.sendMessage(configManager.getGamemodeInstalled() + "наблюдатель");
                        player.setGameMode(GameMode.SPECTATOR);
                        break;
                    default:
                        player.sendMessage(configManager.getIncorrectGamemode());
                        break;
            }

            return true;
            } else {
                player.sendMessage(configManager.getGamemodeCorrectUse());
                return true;
            }
        } else {
            player.sendMessage(configManager.getNoPerms());
            return true;
        }
    }
}
