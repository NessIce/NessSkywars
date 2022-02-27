package br.com.ness.skywars.listener;

import br.com.ness.skywars.Skywars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {


    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e){
        Player player = e.getPlayer();

    }
}
