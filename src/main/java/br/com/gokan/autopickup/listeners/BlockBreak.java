package br.com.gokan.mmodos.listeners;

import br.com.gokan.mmodos.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    
    private Main main;
    
    public BlockBreak(Main main) {
        this.main = main;
    } 
    
    @EventHandler
    void onBlockBreak( BlockBreakEvent event ){
        Player player = event.getPlayer();
        main.getPickupManager().onOnBlockBreak(player, event);
    }
}
