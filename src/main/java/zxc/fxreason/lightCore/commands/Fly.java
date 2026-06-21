package zxc.fxreason.lightCore.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.lightCore.LightCore;

public class Fly implements CommandExecutor {

    private final LightCore plugin;

    public Fly(LightCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if ((!player.hasPermission("reviscore.fly-nonelytra")) || (!player.getInventory().getChestplate().equals(Material.ELYTRA))) {
            player.sendMessage("Наденьте элитры для полета!");
            return true;
        }

        if (sender.hasPermission("reviscore.fly")) {
            boolean newState = !player.getAllowFlight();
            player.setAllowFlight(newState);

            if (!newState) {
                player.setFlying(false);
            }

            String stateMessage = newState ? plugin.getConfigManager().getFlyEnable() : plugin.getConfigManager().getFlyDisable();

            player.sendMessage(stateMessage);

        } else {
            player.sendMessage(plugin.getConfigManager().getNoPerms());
        }

        return true;
    }
}
