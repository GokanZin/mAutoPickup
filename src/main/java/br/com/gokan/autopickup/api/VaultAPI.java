
package br.com.gokan.autopickup.api;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultAPI
{
    private static Permission permission;
    private static Economy economy;
    private static Chat chat;
    
    public static Permission getPermission() {
        return VaultAPI.permission;
    }
    
    public static Chat getChat() {
        return VaultAPI.chat;
    }
    
    public static Economy getEconomy() {
        return VaultAPI.economy;
    }
    
    public static boolean hasVault() {
        return Bukkit.getPluginManager().getPlugin("Vault") != null;
    }
    
    public static boolean hasEconomy() {
        return VaultAPI.economy != null;
    }
    
    public static boolean hasChat() {
        return VaultAPI.chat != null;
    }
    
    public static boolean hasPermission() {
        return VaultAPI.permission != null;
    }
    
    private static boolean setupChat() {
        final RegisteredServiceProvider<Chat> chatProvider = (RegisteredServiceProvider<Chat>)Bukkit.getServer().getServicesManager().getRegistration((Class)Chat.class);
        if (chatProvider != null) {
            VaultAPI.chat = (Chat)chatProvider.getProvider();
        }
        return VaultAPI.chat != null;
    }
    
    private static boolean setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = (RegisteredServiceProvider<Economy>)Bukkit.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (economyProvider != null) {
            VaultAPI.economy = (Economy)economyProvider.getProvider();
        }
        return VaultAPI.economy != null;
    }
    
    private static boolean setupPermissions() {
        final RegisteredServiceProvider<Permission> permissionProvider = (RegisteredServiceProvider<Permission>)Bukkit.getServer().getServicesManager().getRegistration((Class)Permission.class);
        if (permissionProvider != null) {
            VaultAPI.permission = (Permission)permissionProvider.getProvider();
        }
        return VaultAPI.permission != null;
    }
    
    public static void setupVault() {
        setupEconomy();
        setupChat();
        setupPermissions();
    }
    
    static {
        VaultAPI.permission = null;
        VaultAPI.economy = null;
        VaultAPI.chat = null;
        if (hasVault()) {
            setupVault();
        }
    }
}
