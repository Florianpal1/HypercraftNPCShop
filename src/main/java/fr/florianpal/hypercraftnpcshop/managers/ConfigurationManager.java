package fr.florianpal.hypercraftnpcshop.managers;

import fr.florianpal.hypercraftnpcshop.HypercraftNPCShop;
import fr.florianpal.hypercraftnpcshop.configurations.ShopCommandConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {
    private HypercraftNPCShop core;

    private ShopCommandConfig shop = new ShopCommandConfig();
    private File shopFile;
    private FileConfiguration shopConfig;

    private File langFile;
    private FileConfiguration langConfig;

    public ConfigurationManager(HypercraftNPCShop core) {
        this.core = core;

        shopFile = new File(this.core.getDataFolder(), "shop.yml");
        core.createDefaultConfiguration(shopFile, "shop.yml");
        shopConfig = YamlConfiguration.loadConfiguration(shopFile);

        shop.load(shopConfig);
    }

    public void saveDatabaseConfig() {
        try {
            shopConfig.save(shopFile);
        } catch (IOException e) {
            core.getLogger().severe("Failed to save database config");
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        shop.load(shopConfig);

    }

    public ShopCommandConfig getShop() {
        return shop;
    }
}
