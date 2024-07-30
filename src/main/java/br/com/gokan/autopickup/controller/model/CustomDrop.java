package br.com.gokan.mmodos.controller.model;

import org.bukkit.entity.Player;

public class CustomDrop {

    Double chance;
    String actions;

    public CustomDrop(Double chance, String actions) {
        this.chance = chance;
        this.actions = actions;
    }

    public Double getChance() {
        return chance;
    }


    void giveItem( Player player ){

    }

    void giveMoney(Player player, Double amount){

    }
    void executeCommands(Player player){

    }

    public void executeActions(Player player){
    }
}
