package br.com.gokan.autopickup.utils;

import br.com.gokan.autopickup.Main;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;

public class UtilsVersions {

    private final Main main;
    Boolean version1_12x;

    public UtilsVersions( Main main) {
        this.main = main;
        this.version1_12x = hasMethod(BlockBreakEvent.class, "setDropItems");

    }

    public void cancelDrops(BlockBreakEvent event){
        boolean hasSetDropItemsMethod = hasMethod(BlockBreakEvent.class, "setDropItems");
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


    public Boolean verifyInventory(Player player, ItemStack item) {
        if (version1_12x) {
            return verifyInventory1_12x(player, item);
        } else {
           return verifyInventory1_7x(player, item);
        }
    }

     boolean verifyInventory1_12x( Player player, ItemStack item ) {
        PlayerInventory inventory = player.getInventory();
        int maxStackSize = item.getMaxStackSize();
        int amount = item.getAmount();
        for (ItemStack slot : inventory.getStorageContents()) {
            if (slot == null || slot.getType() == Material.AIR) {
                return true;
            } else if (slot.isSimilar(item)) {
                int totalAmount = slot.getAmount() + amount;
                if (totalAmount <= maxStackSize) {
                    return true;
                }
            }
        }
        return false;
    }


     boolean verifyInventory1_7x( Player player, ItemStack item ) {
        PlayerInventory inventory = player.getInventory();
        int maxStackSize = item.getMaxStackSize();
        int amount = item.getAmount();
        for (ItemStack slot : inventory.getContents()){
            if (slot == null || slot.getType() == Material.AIR) {
                return true;
            } else if (slot.isSimilar(item)) {
                int totalAmount = slot.getAmount() + amount;
                if (totalAmount <= maxStackSize) {
                    return true;
                }
            }
        }
        return false;
    }

}
