package br.com.gokan.mmodos.utils.builders;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageBuilder {

    private String msg;
    private List<String> list;
    private Sound som;
    private Object m;

    public MessageBuilder( String msg) {
        this.msg = msg;
    }
    public MessageBuilder( Object msg) {
        this.m = msg;
    }


    public MessageBuilder( List<String> l) {
        this.list = l;
    }

    public MessageBuilder setTypeSound(Sound som){
        this.som = som;
        return this;
    }

    public MessageBuilder addPlaceholder(String value, String replacement) {
        if (msg != null) {
            this.msg = msg.replace("{" + value + "}", replacement);
        }
        return this;
    }



    public void sendToPlayer(CommandSender player){
        if (msg != null) {
            player.sendMessage(msg.replace("&", "§"));
        }

        if (m != null){
            if (m instanceof String){
                String s = (String) m;
                player.sendMessage(s);
                return;
            }
            if (m instanceof List<?>){
                List<String> s = (List<String>) m;
                if (s != null && !s.isEmpty()){
                    s.stream().forEach(c -> player.sendMessage(c.replace("&", "§")));
                }
            }
        }
    }


    public void sendToPlayer(Player player){
        if (msg != null) {
            player.sendMessage(msg.replace("&", "§"));
        }

        if (som != null) {
            player.playSound(player.getLocation(), som, 1, 1);
        }

        if (m != null){
            if (m instanceof String){
                String s = (String) m;
                player.sendMessage(s);
                return;
            }
            if (m instanceof List<?>){
                List<String> s = (List<String>) m;
                if (s != null && !s.isEmpty()){
                    s.stream().forEach(c -> player.sendMessage(c.replace("&", "§")));
                }
            }
        }
    }
}
