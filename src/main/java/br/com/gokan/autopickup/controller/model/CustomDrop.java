package br.com.gokan.autopickup.controller.model;

import br.com.gokan.autopickup.utils.OtherUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CustomDrop {

    Double chance;
    List<String> actions;

    public CustomDrop(Double chance, List<String> actions) {
        this.chance = chance;
        this.actions = actions;
    }

    public Double getChance() {
        return chance;
    }

    Boolean hasAction(String name){
        return actions.contains(name);
    }

    void sendChat( Player player ){
        if (!hasAction("chat")) return;


    }
    void sendBar( Player player ){
        if (!hasAction("actiobar")) return;

    }
    void sendTitle( Player player ){
        Optional<String> title = actions.stream()
                .filter(x -> x.startsWith("title"))
                .map(x -> x.replace("title", "").replace(":", "").replace("&", "§"))
                .findFirst();

        Optional<String> subtitle = actions.stream()
                .filter(x -> x.startsWith("subtitle"))
                .map(x -> x.replace("subtitle", "").replace(":", "").replace("&", "§"))
                .findFirst();

        if (title.isPresent() || subtitle.isPresent()){
            player.sendTitle(title.orElse(""), subtitle.orElse(""));
        }
    }

    void giveMoney(Player player){
        if (!hasAction("money"));
        }
    void executeCommands(Player player){
        if (hasAction("commands") || hasAction("command") || hasAction("comand")){

            return;
        }
    }

    public void executeActions(Player player){
        if (OtherUtils.isChanceSuccessful(chance)){
            giveMoney(player);
            executeCommands(player);
            sendChat(player);
            sendBar(player);
            sendTitle(player);
        }
    }
}
