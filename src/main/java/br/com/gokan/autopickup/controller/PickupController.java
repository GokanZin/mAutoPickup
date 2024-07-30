package br.com.gokan.autopickup.controller;

import br.com.gokan.autopickup.controller.model.Options;
import org.bukkit.Material;

public class PickupController {

    Integer xpAmount;
    Material material;
    Byte data;
    Boolean forceBlock;
    Options options;

    public PickupController(Integer xpAmount, Material material, Byte data, Options options, boolean forceBlock) {
        this.xpAmount = xpAmount;
        this.material = material;
        this.data = data;
        this.forceBlock = forceBlock;
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

    public boolean isForceBlock() {
        return forceBlock;
    }
    public Options getOptions() {
        return options;
    }

    public Boolean hasOptions() {
        return options != null;
    }
}
