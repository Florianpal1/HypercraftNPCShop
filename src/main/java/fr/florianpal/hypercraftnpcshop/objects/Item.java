package fr.florianpal.hypercraftnpcshop.objects;

import org.bukkit.Material;

import java.util.List;

public class Item {

    private String type;
    private String name;
    private List<String> descriptions;
    private Material material;
    private double price;

    public Item(String type, String name, List<String> descriptions, Material material, double price) {
        this.type = type;
        this.name = name;
        this.descriptions = descriptions;
        this.material = material;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }
}
