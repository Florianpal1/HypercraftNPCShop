package fr.florianpal.hypercraftnpcshop.objects;

import fr.florianpal.hypercraftnpcshop.HypercraftNPCShop;
import fr.florianpal.hypercraftnpcshop.configurations.ShopCommandConfig;
import fr.florianpal.hypercraftnpcshop.gui.ListGui;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;


public class Trader extends Trait {

    private HypercraftNPCShop plugin;
    private ShopCommandConfig shopCommandConfig;
    public Trader() {
        super("HyperShop");
        this.plugin = JavaPlugin.getPlugin(HypercraftNPCShop.class);
        this.shopCommandConfig = plugin.getConfigurationManager().getShop();
    }

    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        for(Map.Entry<Integer, Shop> shop : shopCommandConfig.getShopList().entrySet()) {
            if(shop.getKey() == event.getNPC().getId()) {
                new ListGui(plugin, event.getClicker(), shop.getValue());
                break;
            }
        }

    }

    @Override
    public void run() {
    }

    @Override
    public void onAttach() {
        plugin.getServer().getLogger().info(npc.getName() + "has been assigned HyperShop trait!");
    }

    @Override
    public void onDespawn() {
    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onRemove() {
    }

}
