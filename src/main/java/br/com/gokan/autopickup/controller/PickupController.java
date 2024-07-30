package br.com.gokan.mmodos.controller;

import br.com.gokan.mmodos.controller.model.CustomDrop;
import br.com.gokan.mmodos.controller.model.Options;
import org.bukkit.Material;

import java.util.List;

public class PickupController {

    Integer xpAmount;
    Material material;
    Byte data;
    Options options;

    public PickupController(Integer xpAmount, Material material, Byte data, Options options) {
        this.xpAmount = xpAmount;
        this.material = material;
        this.data = data;
        this.options = options;
    }

    public Integer getXpAmount() {
        return xpAmount;
    }

    public Material getMaterial() {
        return material;
    }

    public Byte getData() {
        return data;
    }

    public Options getOptions() {
        return options;
    }

    public Boolean hasOptions() {
        return options != null;
    }
}
