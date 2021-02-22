package fr.florianpal.hypercraftnpcshop.objects;

import java.util.Map;

public class Shop {
    private String shopName;
    private int size;
    private Map<Integer, Item> items;
    private Map<Integer, Barrier> barrier;
    private Map<Integer, Multiplicator> multiplicator;

    public Shop(int  size, String shopName, Map<Integer, Item> items, Map<Integer, Barrier> barrier, Map<Integer, Multiplicator> multiplicator) {
        this.shopName = shopName;
        this.size = size;
        this.items = items;
        this.barrier = barrier;
        this.multiplicator = multiplicator;
    }

    public String getShopName() {
        return shopName;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, Barrier> getBarrier() {
        return barrier;
    }

    public Map<Integer, Multiplicator> getMultiplicator() {
        return multiplicator;
    }
}
