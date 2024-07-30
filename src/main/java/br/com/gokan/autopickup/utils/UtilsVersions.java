package br.com.gokan.autopickup.utils;

import br.com.gokan.autopickup.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.lang.reflect.Method;

public class CancelDropsVersions {

    private final Main main;
    Boolean version1_12x;

    public CancelDropsVersions(Main main) {
        this.main = main;
        this.version1_12x = hasMethod(BlockBreakEvent.class, "setDropItems");

    }

    public void cancelDrops(BlockBreakEvent event){
        boolean hasSetDropItemsMethod = hasMethod(BlockBreakEvent.class, "setDropItems");
        System.out.println("BlockBreakEvent has setDropItems method: " + hasSetDropItemsMethod);
        if (version1_12x) {
            cancelDrops1_12x(event);
        } else {
            cancelDrops1_7x(event);
        }

    }
    void cancelDrops1_7x( BlockBreakEvent event ){
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);

    }

    void cancelDrops1_12x( BlockBreakEvent event ){
        event.setDropItems(false);
    }


    private String getServerVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return  packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public static boolean hasMethod(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getMethod(methodName, boolean.class);
            return method != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }


}
