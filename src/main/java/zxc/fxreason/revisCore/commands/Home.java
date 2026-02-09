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
import zxc.fxreason.revisCore.manager.ConfigManager;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home implements CommandExecutor {
    private ConfigManager configManager;
    private final RevisCore plugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File homesFile;

    public Home(ConfigManager configManager, RevisCore plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
        setupFile();
    }

    private void setupFile() {
        File pluginFolder = plugin.getDataFolder();
        homesFile = new File(pluginFolder, "homes.json");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cКоманда доступна только для игроков");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        Map<String, Object> data = loadData();

        if (data == null) {
            player.sendMessage(configManager.getHomeDataError());
            return true;
        }

        List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");
        List<Map<String, Object>> playerHomes = new ArrayList<>();
        int playerHomesCount = getPlayerHomesCount(player.getName());

        List<String> homesPlayerNames = new ArrayList<>();

        for (Map<String, Object> f : homes) {
            if (player.getName().equals(f.get("username"))) {
                playerHomes.add(f);
                homesPlayerNames.add((String) f.get("namehome"));
            }
        }

        if (playerHomesCount == 0) {
            player.sendMessage(configManager.getNoHomesPoint());
            return true;
        }

        if (playerHomesCount == 1) {
            Map<String, Object> playerHome = playerHomes.get(0);

            if (args.length == 0 || (args.length == 1 && args[0].equals(playerHome.get("namehome")))) {
                teleportToHome(player, playerHome);
                player.sendMessage(configManager.getTeleportedPlayerToHome() + "§f.");
                return true;
            } else if (args.length == 1) {
                String homeName = playerHome.get("namehome").toString();
                player.sendMessage(configManager.getOneHomesPoint() + homeName);
                return true;
            }
        }
        else if (playerHomesCount > 1) {
            if (args.length == 0) {
                player.sendMessage(configManager.getMorePointsHome());
                player.sendMessage("§fДоступные точки: §a" + String.join("§f, §a", homesPlayerNames));
                return true;
            } else if (args.length == 1) {
                String targetHomeName = args[0];
                Map<String, Object> targetHome = null;

                for (Map<String, Object> home : playerHomes) {
                    if (targetHomeName.equals(home.get("namehome"))) {
                        targetHome = home;
                        break;
                    }
                }

                if (targetHome != null) {
                    teleportToHome(player, targetHome);
                    player.sendMessage(configManager.getTeleportedPlayerToHome() + targetHomeName + "§f.");
                    return true;
                } else {
                    player.sendMessage(configManager.getNotFoundPointHome() + targetHomeName + " §fне найдена!");
                    player.sendMessage("§fДоступные точки дома: §a" + String.join("§f, §a", homesPlayerNames));
                    return true;
                }
            }
        }

        player.sendMessage(configManager.getUsageHome());
        return true;
    }

    private void teleportToHome(Player player, Map<String, Object> home) {
        try {
            float x = ((Number) home.get("x")).floatValue();
            float y = ((Number) home.get("y")).floatValue();
            float z = ((Number) home.get("z")).floatValue();

            String worldName = (String) home.get("world");
            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                player.teleport(new Location(world, x, y, z));
            } else {
                player.sendMessage(configManager.getWorldHomeNotFound());
            }
        } catch (Exception e) {
            player.sendMessage(configManager.getErrorLoadsCoordHome());
        }
    }

    private int getPlayerHomesCount(String username) {
        Map<String, Object> data = loadData();
        List<Map<String, Object>> homes = (List<Map<String, Object>>) data.get("homes");

        if (homes == null) {
            return 0;
        }

        int count = 0;
        for (Map<String, Object> f : homes) {
            if (f.get("username").equals(username)) {
                count++;
            }
        }
        return count;
    }

    private Map<String, Object> loadData() {
        try (Reader reader = new FileReader(homesFile)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);
            return data;
        } catch (IOException e) {
            plugin.getLogger().warning("Ошибка чтения базы данных (homes)");
            return null;
        }
    }
}