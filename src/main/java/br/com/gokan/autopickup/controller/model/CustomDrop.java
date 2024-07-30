package br.com.gokan.autopickup.controller.model;

import br.com.gokan.autopickup.api.ActionBar;
import br.com.gokan.autopickup.api.VaultAPI;
import br.com.gokan.autopickup.utils.OtherUtils;
import org.bukkit.Bukkit;
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
        Optional<String> value = actions.stream().filter(x -> x.startsWith("chat")).findFirst();
        if (value.isPresent()){
            player.sendMessage(value.get().replace("chat:", "").replace("chat", "").replace("&", "§"));
        }
    }
    void sendBar( Player player ){
        Optional<String> value = actions.stream().filter(x -> x.startsWith("actionbar")).findFirst();
        if (value.isPresent()){
            ActionBar.send(player, value.get().replace("actionbar:", "").replace("actionbar", "").replace("&", "§"));
        }
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
            player.sendTitle(title.orElse("").replace("&", "§"), subtitle.orElse("").replace("&", "§"));
        }
    }

    void giveMoney(Player player){
        if (!hasAction("money")) return;
        Optional<String> value = actions.stream().filter(x -> x.startsWith("money")).findFirst();
        if (value.isPresent()){
            Double money = OtherUtils.extractDouble(value.get());
            if (VaultAPI.getEconomy() != null){
                VaultAPI.getEconomy().depositPlayer(player, money);
            }
        }
    }
    void executeCommands(Player player){
        actions.stream()
                .filter(x -> x.startsWith("command") || x.startsWith("commands") || x.startsWith("comand"))
                .map(x -> x.replace("command: ", "").replace("command", "").replace("commands", "").replace("comand", "").replace(":",""))
                .forEach(x -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), x.replace("%player%", player.getName()));
                });
        return;

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
