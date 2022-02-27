package br.com.ness.skywars.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent e){
        if(e.getPlayer().getLocation().subtract(0,1,0).getBlock().getType() == Material.SLIME_BLOCK){
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2.4).setY(1));
        }
    }
}
