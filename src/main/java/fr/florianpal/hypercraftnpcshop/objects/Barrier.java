package fr.florianpal.hypercraftnpcshop.objects;

import org.bukkit.Material;

import java.util.List;

public class Barrier {
    private Material material;
    private String title;
    private List<String> description;
    public Barrier(Material material, String title, List<String> description) {
        this.material = material;
        this.title = title;
        this.description = description;
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
}
