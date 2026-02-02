package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

public class Reload implements CommandExecutor {

    private final RevisCore plugin;
    private final ConfigManager configManager;

    public Reload(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (sender.hasPermission("reviscore.reload")) {
            if (args.length == 1 && args[0].equals("reload")) {
                try {
                    plugin.reloadConfig();

                    ConfigManager newCfgManager = new ConfigManager(plugin.getConfig());

                    if (plugin.updateCfgManager(newCfgManager)) {
                        sender.sendMessage("§aКонфигурация успешо перезагружена!");
                    } else {
                        sender.sendMessage("§cОшибка при обновлении конфигурации!");
                    }
                } catch (Exception e) {
                    sender.sendMessage("§cОшибка перезагрузки: " + e.getMessage());
                    plugin.getLogger().severe("Ошибка при перезагрузке конфигурации: " + e);
                }
            } else {
                sender.sendMessage("§cИспользование: /reviscore reload");
            }
        } else {
            sender.sendMessage("§cНедостаточно прав!");
        }
        return true;
    }
}
