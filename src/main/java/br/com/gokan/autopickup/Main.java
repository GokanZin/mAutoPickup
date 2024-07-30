package br.com.gokan.autopickup;

import br.com.gokan.autopickup.command.AdmCommands;
import br.com.gokan.autopickup.listeners.BlockBreak;
import br.com.gokan.autopickup.manager.PickupManager;
import br.com.gokan.autopickup.utils.CancelDropsVersions;
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
    public CancelDropsVersions dropsType;
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
        dropsType = new CancelDropsVersions(this);
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
