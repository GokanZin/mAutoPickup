package br.com.gokan.mmodos.utils.commandframework;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public abstract class CommandHandler extends Command {

    public CommandHandler(String name) {
        super(name);
    }

    public CommandHandler(String name, String description, String[] aliases) {
        super(name, description, "", Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (getPermission() != null && !getPermission().isEmpty() && !sender.hasPermission(getPermission())) {
            sender.sendMessage("§cOps.. Você não tem permissão!!");
            return false;
        }

        return onExecute(sender, label, args);
    }

    public abstract boolean onExecute(CommandSender sender, String label, String[] args);

    public void setAliases(String... aliases) {
        this.setAliases(Arrays.asList(aliases));
    }
}
