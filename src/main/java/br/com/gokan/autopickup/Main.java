package br.com.gokan.autopickup;

import br.com.gokan.autopickup.api.VaultAPI;
import br.com.gokan.autopickup.api.WorldGuardAPI;
import br.com.gokan.autopickup.command.AdmCommands;
import br.com.gokan.autopickup.listeners.BlockBreak;
import br.com.gokan.autopickup.manager.PickupManager;
import br.com.gokan.autopickup.utils.UtilsVersions;
import br.com.gokan.autopickup.utils.ColorConsole;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    String prefix = "mAutoPickup";
    PickupManager pickupManager;
    static YamlConfiguration customdrops;
    public UtilsVersions dropsType;
    public WorldGuardAPI worldGuardAPI;
    @Override
    public void onLoad() {
        reload();
    }

    public static YamlConfiguration getDropsConfig() {
        return customdrops;
    }

    public void reload(){
        File file2 = new File(getDataFolder(), "config.yml");
        if (!file2.exists()) {
            saveDefaultConfig();
        }
    reloadConfig();
    File file = new File(getDataFolder(), "customdrops.yml");
        if (!file.exists()) {
        saveResource("customdrops.yml", false);
    }
    customdrops = YamlConfiguration.loadConfiguration(file);
}
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage( ColorConsole.GREEN + "Plugin foi inicializado com sucesso!" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Criador: [Gokan]" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + " Suporte: https://discord.gg/22gnYtuTTs" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "DEPENDENCIAS: " + ColorConsole.RESET);
        if (getServer().getPluginManager().isPluginEnabled("Vault")){
            VaultAPI.setupVault();
            if (VaultAPI.hasEconomy()){
                Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "[Vault] foi encontrado e ativado com sucesso!" + ColorConsole.RESET);
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "[Vault] foi encontrado, mas não foi possível ativar o sistema de economia!" + ColorConsole.RESET);
            }
        }else {
            Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "[Vault] não foi encontrado, o sistema de economia não foi ativado!" + ColorConsole.RESET);
        }
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")){
             this.worldGuardAPI= new WorldGuardAPI();
            Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "[WorldGuard] foi encontrado e ativado com sucesso!" + ColorConsole.RESET);
        }else {
            Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "[WorldGuard] não foi encontrado, o sistema de WorldGuard não foi ativado!" + ColorConsole.RESET);
        }
        dropsType = new UtilsVersions(this);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "Config Loader:" + ColorConsole.RESET);
        pickupManager = new PickupManager(this);
        this.getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
        new AdmCommands(this);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "-------------------------" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.GREEN + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Plugin foi desligado com sucesso!" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Criador: [Gokan]" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + " Suporte: https://discord.gg/22gnYtuTTs" + ColorConsole.RESET);
        Bukkit.getConsoleSender().sendMessage(ColorConsole.RED + "<-------[" + prefix + "]------->" + ColorConsole.RESET);
    }

    public PickupManager getPickupManager() {
        return pickupManager;
    }
}
