package zxc.fxreason.lightCore.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zxc.fxreason.lightCore.LightCore;
import zxc.fxreason.lightCore.managers.CooldownManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Warp implements CommandExecutor, TabCompleter {

    private final LightCore plugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File warpsFile;
    private final CooldownManager cdManager = new CooldownManager();

    public Warp(LightCore plugin) {
        this.plugin = plugin;
        setupFile();
    }

    private void setupFile() {
        File pluginFolder = plugin.getDataFolder();
        warpsFile = new File(pluginFolder, "warps.json");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        int cooldown = 15;
        long timeLeft = this.cdManager.getCooldown(player);
        if (timeLeft > 0L) {
            player.sendMessage(plugin.getConfigManager().getCooldownCMD() + timeLeft + " §fсекунд.");
            return true;
        }

        if (args.length == 1) {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> warps = (List<Map<String, Object>>) data.get("warps");

            Map<String, Object> targetWarp = null;

            boolean teleportation = false;
            for (Map<String, Object> f : warps) {
                if (args[0].equals(f.get("warpname"))) {
                    teleportation = true;
                    targetWarp = f;
                }
            }

            if (teleportation) {
                TeleportToWarp(player, targetWarp);
                this.cdManager.setCooldown(player, cooldown);
                player.sendMessage(plugin.getConfigManager().getTpStartSeven());
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getWarpUsage());
        }
        return true;
    }

    private void TeleportToWarp(Player player, Map<String, Object> warp) {
        double x = ((Number) warp.get("x")).doubleValue();
        double y = ((Number) warp.get("y")).doubleValue();
        double z = ((Number) warp.get("z")).doubleValue();
        String worldName = (String) warp.get("world");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            player.sendMessage(plugin.getConfigManager().getWorldNotFoundWarp());
            return;
        }

        Location loc = new Location(world, x, y, z);

        String message = plugin.getConfigManager().getSuccessTeleportToWarp() + warp.get("warpname");

        plugin.getTeleportManager().startTeleport(player, message, loc, 7);
    }

    private Map<String, Object> loadData() {
        if (!warpsFile.exists()) return Map.of();
        try (Reader reader = new FileReader(warpsFile)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);
            return data;
        } catch (IOException e) {
            System.out.println("[RevisCore] Ошибка чтения базы данных");
            return null;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            Map<String, Object> data = loadData();
            if (data == null || !data.containsKey("warps")) return List.of();

            List<Map<String, Object>> warps = (List<Map<String, Object>>) data.get("warps");

            return warps.stream().map(f -> (String) f.get("warpname")).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }
        return List.of();
    }
}
