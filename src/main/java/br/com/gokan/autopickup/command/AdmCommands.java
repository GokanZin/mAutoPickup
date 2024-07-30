package br.com.gokan.autopickup.command;

import br.com.gokan.autopickup.Main;
import br.com.gokan.autopickup.utils.builders.CommandBuilder;

public class AdmCommands {

    private Main main;
    public AdmCommands(Main main) {
        this.main = main;
        Commands();
    }


    void Commands(){
        new CommandBuilder("mautopickup")
                .permissionMessage("Sem permissão para recarregar o plugin")
                .aliases("recarregar")
                .executor(( sender, args ) -> {
                    main.reload();
                    main.getPickupManager().reloadCaches(sender);
                    sender.sendMessage("§aConfiguração foram recarregadas!");
                    return true;
                }).build();
    }
}
