package br.com.gokan.autopickup.utils.commandframework;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandRegistry {

    public static void registerCommand(Command commandInstance, String command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            org.bukkit.command.CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(command, commandInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
