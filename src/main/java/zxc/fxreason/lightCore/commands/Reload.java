package zxc.fxreason.lightCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Reload implements CommandExecutor {

    private final LightCore plugin;

    public Reload(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("reviscore.reload")) {
            sender.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§cИспользование: /" + label + " reload");
            return true;
        }

        sender.sendMessage("§eПерезагрузка плагина...");

        try {
            plugin.reloadPlugin();
            sender.sendMessage("§a✓ Плагин успешно перезагружен!");

            plugin.getLogger().info("Плагин перезагружен игроком: " + sender.getName());

        } catch (Exception e) {
            sender.sendMessage("§c✗ Ошибка при перезагрузке: " + e.getMessage());
            plugin.getLogger().severe("Ошибка перезагрузки: " + e);
            e.printStackTrace();
        }

        return true;
    }
}