package fr.florianpal.hypercraftnpcshop.objects;

import org.bukkit.Material;

import java.util.List;

public class Multiplicator {
    private String title;
    private Material material;
    private List<String> description;
    private int multiplicator;
    public Multiplicator(Material material, String title, List<String> description) {
        this.material = material;
        this.title = title;
        this.description = description;
        this.multiplicator = 1;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDescriptions() {
        return description;
    }

    public int getMultiplicator() {
        return multiplicator;
    }

    public void setMultiplicator(int multiplicator) {
        this.multiplicator = multiplicator;
    }
}
