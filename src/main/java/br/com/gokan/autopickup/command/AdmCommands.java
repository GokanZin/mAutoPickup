package br.com.gokan.mmodos.command;

import br.com.gokan.mmodos.utils.builders.CommandBuilder;

public class AdmCommands {

    void reloadCommand(){
        new CommandBuilder("reload")
                .permissionMessage("Sem permissão para recarregar o plugin")
    }
}
