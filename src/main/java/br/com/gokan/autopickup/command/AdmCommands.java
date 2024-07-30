package br.com.gokan.autopickup.command;

import br.com.gokan.autopickup.utils.builders.CommandBuilder;

public class AdmCommands {

    void reloadCommand(){
        new CommandBuilder("reload")
                .permissionMessage("Sem permissão para recarregar o plugin")
    }
}
