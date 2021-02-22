package fr.florianpal.hypercraftnpcshop.gui;

import co.aikar.commands.CommandIssuer;
import fr.florianpal.hypercraftnpcshop.HypercraftNPCShop;
import fr.florianpal.hypercraftnpcshop.languages.MessageKeys;
import fr.florianpal.hypercraftnpcshop.managers.CommandManager;
import fr.florianpal.hypercraftnpcshop.managers.VaultIntegrationManager;
import fr.florianpal.hypercraftnpcshop.objects.Barrier;
import fr.florianpal.hypercraftnpcshop.objects.Item;
import fr.florianpal.hypercraftnpcshop.objects.Multiplicator;
import fr.florianpal.hypercraftnpcshop.objects.Shop;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListGui implements InventoryHolder, Listener {
    private final Inventory inv;
    private Shop shop;
    private HypercraftNPCShop plugin;
    private CommandManager commandManager;

    public ListGui(HypercraftNPCShop plugin, Player p, Shop shop) {
        this.plugin = plugin;
        this.commandManager = plugin .getCommandManager();
        inv = Bukkit.createInventory(this, shop.getSize(), shop.getShopName());
        initializeItems(shop);
        p.openInventory(inv);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    private void initializeItems(Shop shop) {
        this.shop = shop;
        for (Map.Entry<Integer, Item> entry : shop.getItems().entrySet()) {
            inv.setItem(entry.getKey(), createGuiItem(entry.getValue()));
        }
        for (Map.Entry<Integer, Barrier> entry : shop.getBarrier().entrySet()) {
            inv.setItem(entry.getKey(), createGuiItem(entry.getValue()));
        }
        for (Map.Entry<Integer, Multiplicator> entry : shop.getMultiplicator().entrySet()) {
            inv.setItem(entry.getKey(), createGuiItem(entry.getValue()));
        }
    }

    private ItemStack createGuiItem(Item shop) {
        ItemStack item = new ItemStack(shop.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        String title = shop.getName();

        title = ChatColor.translateAlternateColorCodes('&', title);
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits ( 2 );
        List<String> listDescription = new ArrayList<>();

        for(String desc : shop.getDescriptions()) {
            desc = desc.replace("{itemName}", shop.getName());
            desc = desc.replace("{price}", String.valueOf(shop.getPrice()));
            desc = ChatColor.translateAlternateColorCodes('&', desc);
            listDescription.add(desc);
        }
        if (meta != null) {
            meta.setDisplayName(title);
            meta.setLore(listDescription);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createGuiItem(Barrier barrier) {
        ItemStack item = new ItemStack(barrier.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        String title = barrier.getTitle();

        title = ChatColor.translateAlternateColorCodes('&', title);
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits ( 2 );
        List<String> listDescription = new ArrayList<>();

        for(String desc : barrier.getDescriptions()) {
            desc = ChatColor.translateAlternateColorCodes('&', desc);
            listDescription.add(desc);
        }
        if (meta != null) {
            meta.setDisplayName(title);
            meta.setLore(listDescription);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createGuiItem(Multiplicator multiplicator) {
        ItemStack item = new ItemStack(multiplicator.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        String title = multiplicator.getTitle();

        title = ChatColor.translateAlternateColorCodes('&', title);
        title = title.replace("{multiplicator}", String.valueOf(multiplicator.getMultiplicator()));
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits ( 2 );
        List<String> listDescription = new ArrayList<>();

        for(String desc : multiplicator.getDescriptions()) {
            desc = desc.replace("{multiplicator}", String.valueOf(multiplicator.getMultiplicator()));
            desc = ChatColor.translateAlternateColorCodes('&', desc);
            listDescription.add(desc);
        }
        if (meta != null) {
            meta.setDisplayName(title);
            meta.setLore(listDescription);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInvetoryMove(InventoryClickEvent e) {
        if (e.getView().getTopInventory() == inv) {
            e.setCancelled(true);
            return;
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (inv.getHolder() != this) {
            return;
        }
        if (!(e.getClickedInventory() == inv)) {
            return;
        }

        VaultIntegrationManager vault = plugin.getVaultIntegrationManager();
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        CommandIssuer issuerTarget = commandManager.getCommandIssuer(p);
        int id = e.getSlot();
        for (Map.Entry<Integer, Item> entry : shop.getItems().entrySet()) {
            if(entry.getKey() == id) {
                Multiplicator multiplicator = null;

                for(Map.Entry<Integer , Multiplicator> mults : shop.getMultiplicator().entrySet()) {
                    multiplicator = mults.getValue();
                }
                if(multiplicator == null) {
                    p.sendMessage("Multiplicator null");
                    return;
                }
                if(entry.getValue().getType().equalsIgnoreCase("buying")) {
                    int amount = 0; double gain = 0;
                    if (e.getClick().isLeftClick()) {
                        if(!p.getInventory().containsAtLeast(new ItemStack(entry.getValue().getMaterial()), multiplicator.getMultiplicator())) {
                            issuerTarget.sendInfo(MessageKeys.SHOP_BUYING_NOT_HAVE_ITEMS,"{amount}", String.valueOf(multiplicator.getMultiplicator()));
                            return;
                        }
                        amount = multiplicator.getMultiplicator();
                        gain = entry.getValue().getPrice() * multiplicator.getMultiplicator();
                        ItemStack item = new ItemStack(entry.getValue().getMaterial(), amount);
                        p.getInventory().removeItem(item);
                        vault.getEconomy().depositPlayer(p, gain);
                    } else if(e.getClick().isRightClick()) {
                        if(!p.getInventory().containsAtLeast(new ItemStack(entry.getValue().getMaterial()), 1)) {
                            issuerTarget.sendInfo(MessageKeys.SHOP_BUYING_NOT_HAVE_ITEMS,"{amount}", String.valueOf(1));
                            return;
                        }
                        int nbItem = 0;
                        ItemStack itemEntry = new ItemStack(entry.getValue().getMaterial());
                        for(ItemStack item : p.getInventory().getContents()) {
                            if(item != null && item.getType().equals(itemEntry.getType())) {
                                nbItem = nbItem + item.getAmount();
                            }
                        }
                        amount = nbItem;
                        gain = entry.getValue().getPrice() * nbItem;
                        ItemStack item = new ItemStack(entry.getValue().getMaterial(), nbItem);
                        p.getInventory().removeItem(item);
                        vault.getEconomy().depositPlayer(p, gain);
                    } else if (e.getClick().isShiftClick()) {
                        e.setCancelled(true);
                    }
                    issuerTarget.sendInfo(MessageKeys.SHOP_BUYING_SUCCESS,"{amount}", String.valueOf(amount), "{gain}", String.valueOf(gain), "{material}", entry.getValue().getMaterial().toString());
                } else if(entry.getValue().getType().equalsIgnoreCase("selling")) {
                    if(!vault.getEconomy().has(p, entry.getValue().getPrice())) {
                        issuerTarget.sendInfo(MessageKeys.SHOP_SELLING_NOT_HAVE_MONEY,"{price}", String.valueOf(entry.getValue().getPrice()));
                        return;
                    }
                    ItemStack item = new ItemStack(entry.getValue().getMaterial(), multiplicator.getMultiplicator());
                    p.getInventory().addItem(item);
                    double cost = entry.getValue().getPrice()*multiplicator.getMultiplicator();
                    vault.getEconomy().withdrawPlayer(p, cost);
                    issuerTarget.sendInfo(MessageKeys.SHOP_SELLING_SUCCESS,"{amount}", String.valueOf(multiplicator.getMultiplicator()), "{cost}", String.valueOf(cost), "{material}", entry.getValue().getMaterial().toString());
                }
            }
        }
        for (Map.Entry<Integer, Multiplicator> entry : shop.getMultiplicator().entrySet()) {
            if(entry.getKey() == id) {
                switch (entry.getValue().getMultiplicator()) {
                    case 1: entry.getValue().setMultiplicator(2); break;
                    case 2: entry.getValue().setMultiplicator(4); break;
                    case 4: entry.getValue().setMultiplicator(8); break;
                    case 8: entry.getValue().setMultiplicator(16); break;
                    case 16: entry.getValue().setMultiplicator(32); break;
                    case 32: entry.getValue().setMultiplicator(64); break;
                    case 64: entry.getValue().setMultiplicator(1); break;
                }
                inv.setItem(entry.getKey(), createGuiItem(entry.getValue()));
            }
        }
    }
}