package zxc.fxreason.revisCore.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class SetHome implements CommandExecutor, TabCompleter {
    private ConfigManager configManager;
    private final RevisCore plugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File homesFile;

    public SetHome(ConfigManager configManager, RevisCore plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
        this.setupFile();
    }

    private void setupFile() {
        File pluginFolder = plugin.getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        homesFile = new File(pluginFolder, "homes.json");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(configManager.getEnterHomePoint());
            return true;
        } else if (args.length == 1) {
            if (!player.hasPermission("sethome.set")) {
                player.sendMessage("§cНедостаточно прав!");
                return true;
            }

            String home = args[0];
            String nickname = player.getName();

            Map<String, Object> data = loadData();
            List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");
            if (homes != null) {
                for (Map<String, Object> f : homes) {
                    if (f.get("namehome").equals(home) && f.get("username").equals(nickname)) {
                        player.sendMessage(configManager.getDuplicateNameHome());
                        return true;
                    }
                }
            }

            if (!player.hasPermission("sethome.unlimited")) {
                int currentHomes = getPlayerHomesCount(nickname);
                if (currentHomes >= 2) {
                    player.sendMessage(configManager.getMaxPointsHome());
                    return true;
                }
            }

            World world = player.getWorld();
            float x = (float) player.getX();
            float y = (float) player.getY();
            float z = (float) player.getZ();

            jsonSave(player, nickname, home, world, x, y, z);
            return true;
        } else {
            player.sendMessage(configManager.getEnterHomePoint());
        }

        return true;
    }

    private int getPlayerHomesCount(String username) {
        Map<String, Object> data = loadData();
        List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");

        if (homes == null) {
            return 0;
        }

        int count = 0;
        for (Map<String, Object> f : homes) {
            if (f.get("username").equals(username)){
                count++;
            }
        }
        return count;
    }

    private void jsonSave(Player player, String username, String home, World world, float x, float y, float z) {
        Map<String, Object> newHome = new HashMap<>();
        newHome.put("username", username);
        newHome.put("namehome", home);
        newHome.put("world", world.getName());
        newHome.put("x", x);
        newHome.put("y", y);
        newHome.put("z", z);

        Map<String, Object> data = loadData();
        List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");

        if (homes == null) {
            homes = new ArrayList<>();
            data.put("homes", homes);
        }

        boolean homeExists = false;
        for (Map<String, Object> f : homes) {
            if (f.get("namehome").equals(home) && f.get("username").equals(username)) {
                player.sendMessage(configManager.getDuplicateNameHome());
                homeExists = true;
                break;
            }
        }

        if (!homeExists) {
            homes.add(newHome);
            player.sendMessage(configManager.getSuccesEnterSetHome());
        }

        saveData(data);

    }

    private Map<String, Object> loadData() {
        if (!homesFile.exists()) {
            Map<String, Object> data = new HashMap<>();
            data.put("homes", new ArrayList<>());
            return data;
        }

        try (Reader reader = new FileReader(homesFile)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);

            if (data == null) {
                data = new HashMap<>();
                data.put("homes", new ArrayList<>());
            }

            if (!data.containsKey("homes")) {
                data.put("homes", new ArrayList<>());
            }

            return data;
        } catch (IOException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("homes", new ArrayList<>());
            return data;
        }
    }

    private void saveData(Map<String, Object> data) {
        try (Writer writer = new FileWriter(homesFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            plugin.getLogger().warning("Ошибка при сохранении homes.json: " + e.getMessage());
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return null;
    }
}
