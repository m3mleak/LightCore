package zxc.fxreason.revisCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

public class Exp implements CommandExecutor {

    private final RevisCore plugin;

    public Exp(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!sender.hasPermission("reviscore.expgive")) {
            sender.sendMessage(plugin.getConfigManager().getNoPerms());
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(plugin.getConfigManager().getExpGiveUsage());
            return true;
        }

        Player target =  plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getConfigManager().getNicknameNotFound());
            return true;
        }

        int amount = Integer.parseInt(args[1]);

        target.giveExp(amount);

        sender.sendMessage("§b✽ §7➛ §fВы успешно выдали §a" + amount + " EXP §fигроку §a" + target.getName());

        return true;
    }
}
