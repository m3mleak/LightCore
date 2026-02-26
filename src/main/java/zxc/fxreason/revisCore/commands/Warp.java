package zxc.fxreason.revisCore.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Warp implements CommandExecutor {
    private final RevisCore plugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File warpsFile;

    public Warp(RevisCore plugin) {
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
                player.sendMessage(plugin.getConfigManager().getSuccessTeleportToWarp() + targetWarp.get("warpname"));
                TeleportToWarp(player, targetWarp);
            }
        } else {
            player.sendMessage(plugin.getConfigManager().getWarpUsage());
        }
        return true;
    }

    private void TeleportToWarp (Player player, Map<String, Object> warp) {
        try {
            float x = ((Number) warp.get("x")).floatValue();
            float y = ((Number) warp.get("y")).floatValue();
            float z = ((Number) warp.get("z")).floatValue();

            String worldName = (String) warp.get("world");
            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                player.teleport(new Location(world, x, y, z));
            } else {
                player.sendMessage(plugin.getConfigManager().getWorldNotFoundWarp());
            }
        } catch (Exception e) {
            player.sendMessage(plugin.getConfigManager().getErrorLoadsCoordsWarp());
        }
    }

    private Map<String, Object> loadData() {
        try (Reader reader = new FileReader(warpsFile)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);
            return data;
        } catch (IOException e) {
            System.out.println("[RevisCore] Ошибка чтения базы данных");
            return null;
        }
    }
}
