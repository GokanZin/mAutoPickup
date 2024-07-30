package br.com.gokan.autopickup.api;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldGuardAPI {

    public WorldGuardPlugin worldguard;

    public WorldGuardAPI() {
        this.worldguard = WorldGuardPlugin.inst();
    }


    public boolean hasRegion(String name, Player player) {
        World world = player.getWorld();
        RegionManager regionManager = worldguard.getRegionManager(player.getWorld());
        ApplicableRegionSet applicableRegions = regionManager.getApplicableRegions(player.getLocation());
        return applicableRegions.getRegions().stream()
                .anyMatch(region -> region.getId().equalsIgnoreCase(name));
    }
}
