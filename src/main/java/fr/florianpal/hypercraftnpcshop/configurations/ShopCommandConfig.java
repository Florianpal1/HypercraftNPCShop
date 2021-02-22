package fr.florianpal.hypercraftnpcshop.configurations;

import fr.florianpal.hypercraftnpcshop.objects.Barrier;
import fr.florianpal.hypercraftnpcshop.objects.Item;
import fr.florianpal.hypercraftnpcshop.objects.Multiplicator;
import fr.florianpal.hypercraftnpcshop.objects.Shop;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class ShopCommandConfig {
    private Map<Integer, Shop> shopList;

    public void load(Configuration config) {
        shopList = new HashMap<>();
        for (String index : config.getConfigurationSection("shop").getKeys(false)) {
            Map<Integer, Item> items = new HashMap<>();
            Map<Integer, Barrier> barrierList = new HashMap<>();
            Map<Integer, Multiplicator> multiplicatorList = new HashMap<>();
            String shopName = config.getString("shop." + index + ".gui.name");
            int size = config.getInt("shop." + index + ".gui.size");
            for (String place : config.getConfigurationSection("shop." + index + ".block").getKeys(false)) {
                if(config.getString("shop." + index + ".block." + place + ".utility").equalsIgnoreCase("buying") || config.getString("shop." + index + ".block." + place + ".utility").equalsIgnoreCase("selling")) {
                    items.put(Integer.valueOf(place), new Item(
                            config.getString("shop." + index + ".block." + place + ".utility"),
                            config.getString("shop." + index + ".block." + place + ".name"),
                            config.getStringList("shop." + index + ".block." + place + ".descriptions"),
                            Material.getMaterial(config.getString("shop." + index + ".block." + place + ".item")),
                            config.getDouble("shop." + index + ".block." + place + ".price")
                    ));
                } else if (config.getString("shop." + index + ".block." + place + ".utility").equalsIgnoreCase("barrier")) {
                    barrierList.put(Integer.valueOf(place), new Barrier(
                            Material.getMaterial(config.getString("shop." + index + ".block." + place + ".material")),
                            config.getString("shop." + index + ".block." + place + ".title"),
                            config.getStringList("shop." + index + ".block." + place + ".descriptions")
                    ));
                } else if (config.getString("shop." + index + ".block." + place + ".utility").equalsIgnoreCase("multiplicator")) {
                    multiplicatorList.put(Integer.valueOf(place), new Multiplicator(
                            Material.getMaterial(config.getString("shop." + index + ".block." + place + ".material")),
                            config.getString("shop." + index + ".block." + place + ".title"),
                            config.getStringList("shop." + index + ".block." + place + ".descriptions")
                    ));
                }
            }
            shopList.put(Integer.valueOf(index), new Shop(
                    size,
                    shopName,
                    items,
                    barrierList,
                    multiplicatorList
            ));
        }
    }

    public void save(Configuration config) {}

    public Map<Integer, Shop> getShopList() {
        return shopList;
    }
}
