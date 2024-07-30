package br.com.gokan.autopickup.utils.builders;

import br.com.gokan.autopickup.utils.commandframework.CommandHandler;
import br.com.gokan.autopickup.utils.commandframework.CommandRegistry;
import org.bukkit.command.CommandSender;

import java.util.function.BiFunction;

public class CommandBuilder {
    private final CommandHandler command;
    private BiFunction<CommandSender, String[], Boolean> executor;

    public CommandBuilder(String name) {
        this.command = new CommandHandler(name) {
            @Override
            public boolean onExecute(CommandSender sender, String label, String[] args) {
                return executor.apply(sender, args);
            }
        };
    }

    public CommandBuilder description(String description) {
        command.setDescription(description);
        return this;
    }

    public CommandBuilder aliases(String... aliases) {
        command.setAliases(aliases);
        return this;
    }

    public CommandBuilder permission(String permission) {
        command.setPermission(permission);
        return this;
    }

    public CommandBuilder permissionMessage(String message) {
        command.setPermissionMessage(message);
        return this;
    }

    public CommandBuilder executor(BiFunction<CommandSender, String[], Boolean> executor) {
        this.executor = executor;
        return this;
    }

    public CommandHandler build() {
        CommandRegistry.registerCommand(command, command.getName());
        return command;
    }
}
