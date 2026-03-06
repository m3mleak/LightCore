package zxc.fxreason.revisCore.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.CooldownManager;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class RTP implements CommandExecutor {

    private final RevisCore plugin;
    private final CooldownManager cdManager = new CooldownManager();
    private final EnumSet<Material> UNSAFE_BLOCKS = EnumSet.of(
            Material.LAVA, Material.WATER, Material.MAGMA_BLOCK,
            Material.CACTUS, Material.FIRE, Material.CAMPFIRE
    );

    public RTP(RevisCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        long timeLeft = cdManager.getCooldown(player);
        if (timeLeft > 0) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        switch (args[0]) {
            case "default" -> handleRtp(player, 1000, null);
            case "long" -> {
                if (!player.hasPermission("reviscore.rtp-long")) {
                    player.sendMessage(plugin.getConfigManager().getNoPerms());
                    return true;
                }
                handleRtp(player, 2500, "reviscore.rtp-long");
            }
            default -> player.sendMessage(plugin.getConfigManager().getRtpUsage());
        }

        return true;
    }

    private void handleRtp(Player player, int radius, String perm) {
        World world = plugin.getServer().getWorld(plugin.getConfigManager().getWorldRTP());
        if (world == null) world = player.getWorld();

        findSafeLocationAsync(world, radius, (loc) -> {
            if (loc == null) {
                player.sendMessage(plugin.getConfigManager().getRtpNotSearchSafe());
                return;
            }

            plugin.getTeleportManager().startTeleport(player, plugin.getConfigManager().getRtpSuccess(), loc, 7);
            cdManager.setCooldown(player, 30);
        });
    }

    private void findSafeLocationAsync(World world, int radius, java.util.function.Consumer<Location> callback) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int x = random.nextInt(-radius, radius);
        int z = random.nextInt(-radius, radius);

        world.getChunkAtAsync(x >> 4, z >> 4).thenAccept(chunk -> {
            for (int i = 0; i < 15; i++) {
                int nx = x + random.nextInt(-20, 20);
                int nz = z + random.nextInt(-20, 20);
                int y = world.getHighestBlockYAt(nx, nz);

                Location loc = new Location(world, nx + 0.5, y + 1, nz + 0.5);
                if (isSafe(loc)) {
                    callback.accept(loc);
                    return;
                }
            }
            callback.accept(null);
        });
    }

    private boolean isSafe(Location loc) {
        Block foot = loc.getBlock();
        Block head = foot.getRelative(0, 1, 0);
        Block ground = foot.getRelative(0, -1, 0);

        return ground.getType().isSolid()
                && !UNSAFE_BLOCKS.contains(ground.getType())
                && foot.getType().isAir()
                && head.getType().isAir();
    }
}
