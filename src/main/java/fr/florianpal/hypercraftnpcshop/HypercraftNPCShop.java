package fr.florianpal.hypercraftnpcshop;

import fr.florianpal.hypercraftnpcshop.commands.NPCShopCommand;
import fr.florianpal.hypercraftnpcshop.managers.CommandManager;
import fr.florianpal.hypercraftnpcshop.managers.ConfigurationManager;
import fr.florianpal.hypercraftnpcshop.managers.VaultIntegrationManager;
import fr.florianpal.hypercraftnpcshop.objects.Trader;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

public class HypercraftNPCShop extends JavaPlugin {

    private ConfigurationManager configurationManager;
    private VaultIntegrationManager vaultIntegrationManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {

        File languageFile = new File(getDataFolder(), "lang_fr.yml");
        createDefaultConfiguration(languageFile, "lang_fr.yml");
        vaultIntegrationManager = new VaultIntegrationManager(this);

        configurationManager = new ConfigurationManager(this);
        commandManager = new CommandManager(this);

        if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        commandManager.registerCommand(new NPCShopCommand(this));

        if(CitizensAPI.getPlugin().isEnabled()) {
            CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(Trader.class).withName("HyperShop"));
        } else {
            System.out.println("Citizens Implementation : " + CitizensAPI.hasImplementation());
        }

    }

    @Override
    public void reloadConfig() {
        configurationManager.reloadConfig();
    }

    public void createDefaultConfiguration(File actual, String defaultName) {
        // Make parent directories
        File parent = actual.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (actual.exists()) {
            return;
        }

        InputStream input = null;
        try {
            JarFile file = new JarFile(this.getFile());
            ZipEntry copy = file.getEntry(defaultName);
            if (copy == null) throw new FileNotFoundException();
            input = file.getInputStream(copy);
        } catch (IOException e) {
            getLogger().severe("Unable to read default configuration: " + defaultName);
        }

        if (input != null) {
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }

                getLogger().info("Default configuration file written: " + actual.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException ignored) {
                }

                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException ignored) {
                }
            }
        }


    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public VaultIntegrationManager getVaultIntegrationManager() {
        return vaultIntegrationManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
