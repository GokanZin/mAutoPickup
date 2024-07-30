package br.com.gokan.autopickup.caches;

import br.com.gokan.autopickup.Main;
import br.com.gokan.autopickup.controller.PickupController;
import br.com.gokan.autopickup.controller.model.CustomDrop;
import br.com.gokan.autopickup.controller.model.Options;
import br.com.gokan.autopickup.utils.builders.ItemBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static br.com.gokan.autopickup.Main.getDropsConfig;

public class PickupCaches {

    private List<PickupController> cachesBlocks = new ArrayList<>();
    private List<String> worldNames = new ArrayList<>();
    private Main main;

    public PickupCaches(Main main) {
        this.main = main;
    }

    public void clearCaches() {
        cachesBlocks.clear();
        worldNames.clear();
    }

    public void setCachesBlocks(List<PickupController> cachesBlocks) {
        this.cachesBlocks = cachesBlocks;
    }

    public void setWorldNames(List<String> worldNames) {
        this.worldNames = worldNames;
    }

    public List<PickupController> getCachesBlocks() {
        return cachesBlocks;
    }

    public List<String> getWorldNames() {
        return worldNames;
    }



    public void loadWorlds( CommandSender sender ){
        List<String> worldDisable = main.getConfig().getStringList("worldDisables");
        if (worldDisable != null){
            setWorldNames(worldDisable);
        }
        sender.sendMessage("[§c!§f] §d" + worldDisable.size() + " disabled worlds were initialized");
    }

    public void loadPickups(CommandSender sender){
        ConfigurationSection itemPickup = main.getConfig().getConfigurationSection("itemPickups");
        if (itemPickup != null) {
            for (String key : itemPickup.getKeys(false)) {
                ConfigurationSection section = itemPickup.getConfigurationSection(key);
                if (!section.contains("id")){
                    sender.sendMessage("§c[!] §fThe id field is required in the itemPickups section" + key);
                    return;
                }
                String id = section.getString("id", "");
                if (id.isEmpty()){
                    sender.sendMessage("§c[!] §fThe id field is required in the itemPickups section" + key);
                    return;
                }
                int data = section.getInt("data", 0);
                int xpAmount = section.getInt("xp", 0);
                ItemStack stack = new ItemBuilder(id, (byte) data).build();
                Boolean disableDrop = false;
                Options optionsModel = null;
                if (section.contains("options")) {
                    optionsModel = new Options();
                    ConfigurationSection options = section.getConfigurationSection("options");

                    if (options.contains("regions")) {
                        List<String> region = options.getStringList("regions");
                        optionsModel.setRegionNames(region);
                    }
                    if (options.contains("disableDrop")) {
                        disableDrop = options.getBoolean("disableDrop", false);
                        optionsModel.setDesativeDrop(disableDrop);
                    }
                    if (options.contains("customDrop")){
                        List<String> customDrop = section.getStringList("customDrop");
                        List<CustomDrop> customDrops = getCustomDrops(customDrop, sender);
                        if (customDrops != null && !customDrops.isEmpty()){
                            optionsModel.setCustomDrops(customDrops);
                        }
                    }
                }
                PickupController pickupContrroler = new PickupController(xpAmount, stack.getType(), stack.getData().getData(), optionsModel);
                cachesBlocks.add(pickupContrroler);
            }
        }
        sender.sendMessage("[§c!§f] §d" + cachesBlocks.size() + " item pickups were initialized");
    }

    List<CustomDrop> getCustomDrops( List<String> custom, CommandSender sender){
        if (!getDropsConfig().contains("drops")) {
            return null;
        }
        List<CustomDrop> customDrops = new ArrayList<>();
        ConfigurationSection drops = getDropsConfig().getConfigurationSection("drops");
        for (String customDrop : drops.getKeys(false)){
            if (custom.contains(customDrop)){
                ConfigurationSection section = drops.getConfigurationSection(customDrop);
                if (section != null){
                    Double chance = section.getDouble("chance", 0);
                    String actions = section.getString("actions");
                    if (actions != null && !actions.isEmpty()){
                        CustomDrop customDrop1 = new CustomDrop(chance, actions);
                        customDrops.add(customDrop1);
                    }else {
                        sender.sendMessage("§c[!] §fThe actions field is required in the custom drop " + customDrop);
                        continue;
                    }
                }
            }else {
               sender.sendMessage("§c[!] §fThe custom drop " + customDrop + " was not found in the customdrops.yml file");
            }
        }
        return customDrops;
    }


}
