package br.com.gokan.mmodos.manager;

import br.com.gokan.mmodos.Main;
import br.com.gokan.mmodos.caches.PickupCaches;
import br.com.gokan.mmodos.controller.PickupController;
import br.com.gokan.mmodos.controller.model.CustomDrop;
import br.com.gokan.mmodos.listeners.BlockBreak;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PickupManager {

    private Main main;
    private PickupCaches caches;

    public PickupManager(Main main) {
        this.main = main;
        this.caches = new PickupCaches(main);
        reloadCaches(Bukkit.getConsoleSender());
    }


    public void reloadCaches(CommandSender sender){
        caches.clearCaches();
        caches.loadWorlds(sender);
        caches.loadPickups(sender);
    }


    public Boolean hasWorld( Player player ){
        return caches.getWorldNames().contains(player.getWorld().getName());
    }


    public void onOnBlockBreak( Player player, BlockBreakEvent event ){
        Block block = event.getBlock();
        if (!hasWorld(player)) return;
        List<PickupController> loadC = caches.getCachesBlocks();
        Optional<PickupController> filter = loadC.stream().filter(pickupController -> pickupController.getMaterial().equals(block.getType()) && pickupController.getData().equals(block.getData())).findFirst();

        if (filter.isPresent()){
            PickupController pickupController = filter.get();
            if (pickupController.hasOptions()){
                if (pickupController.getOptions().hasDesativeDrop()){
                    event.getBlock().getDrops().clear();
                }
                if (pickupController.getOptions().hasCustomDrops()){
                    List<CustomDrop> customDrops = pickupController.getOptions().getCustomDrops();
                    customDrops.forEach(customDrop -> customDrop.executeActions(player));
                }
            }
        }

    }

}
