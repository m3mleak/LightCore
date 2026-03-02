package zxc.fxreason.revisCore.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.fxreason.revisCore.RevisCore;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private File file;
    private FileConfiguration config;

    public DataManager(String name, RevisCore plugin) {
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        if (file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
