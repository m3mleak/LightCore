package zxc.fxreason.revisCore.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.managers.ConfigManager;

import java.io.*;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetWarp implements CommandExecutor {
    private ConfigManager configManager;
    private final RevisCore plugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File warpsFile;

    public SetWarp(ConfigManager configManager, RevisCore plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
        this.setupFile();
    }

    private void setupFile() {
        File pluginFolder = plugin.getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        warpsFile = new File(pluginFolder, "warps.json");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда доступна только игрокам");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(configManager.getSetWarpName());
        } else if (args.length == 1) {
            if (!player.hasPermission("reviscore.setwarp:")) {
                player.sendMessage(configManager.getNoPerms());
                return true;
            }

            String warp = args[0];

            Map<String, Object> data = loadData();
            List<Map<String, Object>> warps = (List<Map<String, Object>>) data.get("warps");
            if (warps != null) {
                for (Map<String, Object> f : warps) {
                    if (f.get("warpname").equals(warp)) {
                        player.sendMessage(configManager.getDuplicateWarp());
                        return true;
                    }
                }
            }

            World world = player.getWorld();
            float x = (float) player.getX();
            float y = (float) player.getY();
            float z = (float) player.getZ();

            jsonSave(player, warp, world, x, y, z);
            return true;
        } else {
            player.sendMessage(configManager.getSetWarpName());
        }

        return true;
    }

    private Map<String, Object> loadData() {
        if (!warpsFile.exists()) {
            Map<String, Object> data = new HashMap<>();
            data.put("warps", new ArrayList<>());
            return data;
        }

        try (Reader reader = new FileReader(warpsFile)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);

            if (data == null) {
                data = new HashMap<>();
                data.put("warps", new ArrayList<>());
            }

            if (!data.containsKey("warps")) {
                data.put("warps", new ArrayList<>());
            }

            return data;
        } catch (IOException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("warps", new ArrayList<>());
            return data;
        }
    }

    private void jsonSave(Player player, String warpName, World world, float x, float y, float z) {
        Map<String, Object> newWarp = new HashMap<>();
        newWarp.put("warpname", warpName);
        newWarp.put("world", world.getName());
        newWarp.put("x", x);
        newWarp.put("y", y);
        newWarp.put("z", z);

        Map<String, Object> data = loadData();
        List<Map<String, Object>> warps = (List<Map<String, Object>>) data.get("warps");

        if (warps == null) {
            warps = new ArrayList<>();
            data.put("warps", warps);
        }

        boolean warpExists = false;
        for (Map<String, Object> f : warps) {
            if (f.get("warpname").equals(warpName)) {
                player.sendMessage(configManager.getDuplicateWarp());
                warpExists = true;
                break;
            }
        }

        if (!warpExists) {
            warps.add(newWarp);
            player.sendMessage(configManager.getSetWarpSucces());
        }

        saveData(data);
    }

    private void saveData(Map<String, Object> data) {
        try (Writer writer = new FileWriter(warpsFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            plugin.getLogger().warning("Ошибка при сохранении warps.json: " + e.getMessage());
        }
    }
}
