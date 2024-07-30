package br.com.gokan.autopickup.utils;

import br.com.gokan.autopickup.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

public class CancelDropsVersions {

    String version;
    private final Main main;


    public CancelDropsVersions(Main main) {
        this.main = main;
        loadTypeCancel();

    }

    public void cancelDrops(BlockBreakEvent event){
        switch (version){
            case "default":
                cancelDrops1_7x(event);
                break;
            case "newversion":
                cancelDrops1_12x(event);
                break;
            default:
                cancelDrops1_7x(event);
        }
    }
    void cancelDrops1_7x( BlockBreakEvent event ){
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);

    }

    void cancelDrops1_12x( BlockBreakEvent event ){
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        event.setDropItems(false);
    }




    public void loadTypeCancel() {
        String version = getServerVersion();
        Bukkit.getConsoleSender().sendMessage("Version: " + version);
        if (version.startsWith("v1_7") || version.startsWith("v1_8")){
            this.version = "default";
        }
        else if (version.startsWith("v1_12") ||version.startsWith("v1_13") || version.startsWith("v1_14") ||
                version.startsWith("v1_15") || version.startsWith("v1_16") ||
                version.startsWith("v1_17") || version.startsWith("v1_18") ||
                version.startsWith("v1_19") || version.startsWith("v1_20")) {
            this.version = "newversion";
        } else {
            this.version = "default";
        }
    }

    private String getServerVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return  packageName.substring(packageName.lastIndexOf('.') + 1);
    }


}
