package br.com.gokan.autopickup.command;

import br.com.gokan.autopickup.Main;
import br.com.gokan.autopickup.utils.builders.CommandBuilder;
import br.com.gokan.autopickup.utils.builders.MessageBuilder;
import org.bukkit.command.CommandSender;

public class AdmCommands {

    private Main main;
    public AdmCommands(Main main) {
        this.main = main;
        Commands();
    }


    void Commands(){
        new CommandBuilder("mautopickup")
                .permissionMessage(main.getConfig().getString("messages.noPermission", "You don't have permission to use this command"))
                .permission("mautopickup.adm")
                .aliases("mautopickups")
                .executor(( sender, args ) -> {
                    if (args.length == 0) {
                        new MessageBuilder(main.getConfig().getString("messages.commands")).sendToPlayer(sender);
                        return true;
                    }
                    String subComando = args[0].toLowerCase();
                    switch (subComando){
                        case "reload":
                            reload(sender, args);
                            break;
                        default:
                            new MessageBuilder(main.getConfig().getString("messages.commands")).sendToPlayer(sender);
                            break;
                    }
                    return true;
                }).build();
    }


     void reload( CommandSender sender, String[] args ){
        main.reload();
        main.getPickupManager().reloadCaches(sender);
        new MessageBuilder(main.getConfig().getString("messages.reload")).sendToPlayer(sender);
    }
}
