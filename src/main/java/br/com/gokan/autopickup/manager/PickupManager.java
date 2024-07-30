package br.com.gokan.autopickup.manager;

import br.com.gokan.autopickup.Main;
import br.com.gokan.autopickup.caches.PickupCaches;
import br.com.gokan.autopickup.controller.PickupController;
import br.com.gokan.autopickup.utils.builders.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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


    public void onOnBlockBreak(Player player, BlockBreakEvent event) {
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getItemInHand().getType() == Material.AIR) {
            return;
        }
        if (hasWorld(player)) {
            return;
        }
        List<PickupController> loadC = caches.getCachesBlocks();
        Optional<PickupController> filter = loadC.stream()
                .filter(pc -> pc.getMaterial() == event.getBlock().getType() && pc.getData() == event.getBlock().getData())
                .findFirst();
        if (filter.isPresent() && filter.get().hasOptions()) {
            PickupController pickupController = filter.get();
            boolean region = hasRegion(player, pickupController);
            if (!region) {
                return;
            }
            executePickupActions(event, player, pickupController);
        }
    }
    private boolean hasRegion(Player player, PickupController pickupController) {
        if (pickupController.getOptions().hasRegionNames()) {
            return pickupController.getOptions().getRegionNames().stream()
                    .anyMatch(regionName -> main.worldGuardAPI != null && main.worldGuardAPI.hasRegion(regionName, player));
        } else {
            return true;
        }
    }

    private void executePickupActions(BlockBreakEvent event, Player player, PickupController pickupController) {
        event.setExpToDrop(pickupController.getXpAmount());
        if (!pickupController.getOptions().hasDesativeDrop()) {
            addDropsPlayer(event.getBlock().getDrops(), player, event, pickupController);
        }
        pickupController.getOptions().getCustomDrops().forEach(customDrop -> customDrop.executeActions(player));
        main.dropsType.cancelDrops(event);
    }



    boolean addDropsPlayer(Collection<ItemStack> drops, Player player, BlockBreakEvent event, PickupController pickup) {
        for (ItemStack drop : drops) {
            if (!hasInventorySpace(player, drop)) {
                new MessageBuilder(main.getConfig().getString("messages.inventoryFull")).sendToPlayer(player);
                if (pickup.isForceBlock()) {
                    event.setCancelled(true);
                    return false;
                }
                return true;
            }

            int fortuneLevel = player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            int quantity = drop.getAmount() * (fortuneLevel + 1);
            drop.setAmount(quantity);
            player.getInventory().addItem(drop);
        }
        return true;
    }


    public boolean hasInventorySpace(Player player, ItemStack item) {
        return main.dropsType.verifyInventory(player, item);
    }

}
