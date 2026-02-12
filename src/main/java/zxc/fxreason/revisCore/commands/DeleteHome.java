package zxc.fxreason.revisCore.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import zxc.fxreason.revisCore.RevisCore;
import zxc.fxreason.revisCore.manager.ConfigManager;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class DeleteHome implements CommandExecutor {
    private final RevisCore plugin;
    private File homesFile;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ConfigManager configManager;

    public DeleteHome(RevisCore plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        setupFile();
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
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(configManager.getEnterHomeForDelete());
            return true;
        } else if (args.length == 1) {
            String homeName = args[0];
            String username = player.getName();

            Map<String, Object> data = loadData();
            List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");

            if (homes == null || homes.isEmpty()) {
                player.sendMessage("Дом на найден");
                return true;
            }

            boolean homeFound = false;
            Iterator<Map<String, Object>> iterator = homes.iterator();

            while (iterator.hasNext()) {
                Map<String, Object> home = iterator.next();
                if (home.get("namehome").equals(homeName) && home.get("username").equals(username)) {

                    iterator.remove();
                    homeFound = true;
                    break;
                }
            }

            if (homeFound) {
                saveData(data);
                player.sendMessage(configManager.getSuccessDeleteHomePoint());
            } else {
                player.sendMessage(configManager.getHomePointNotFound());
            }
            return true;
        } else {
            player.sendMessage(configManager.getEnterHomeForDelete());
        }

        return true;
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
}
