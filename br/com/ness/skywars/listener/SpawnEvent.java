package br.com.ness.skywars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnEvent implements Listener {

    @EventHandler
    public void onSpawnEvent(CreatureSpawnEvent e){
        if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM){
            e.setCancelled(true);
        }
    }
}
