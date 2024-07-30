package br.com.gokan.autopickup.controller.model;

import java.util.List;

public class Options {

    Boolean desativeDrop;
    List<String> regionNames;
    List<CustomDrop> customDrops;

    public Options() {
    }

    public Boolean hasDesativeDrop() {
        return desativeDrop;
    }

    public List<String> getRegionNames() {
        return regionNames;
    }

    public Boolean hasRegionNames() {
        return regionNames != null;
    }

    public List<CustomDrop> getCustomDrops() {
        return customDrops;
    }

    public Boolean hasCustomDrops() {
        return customDrops != null;
    }

    public void setDesativeDrop( Boolean desativeDrop ) {
        this.desativeDrop = desativeDrop;
    }

    public void setRegionNames( List<String> regionNames) {
        this.regionNames = regionNames;
    }

    public void setCustomDrops(List<CustomDrop> customDrops) {
        this.customDrops = customDrops;
    }



}
