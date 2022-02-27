package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    @EventHandler
    public void onDropEvent(PlayerDropItemEvent e){
        if(e.getPlayer().getGameMode()!= GameMode.CREATIVE){
            Profile profile = Profile.getByPlayer(e.getPlayer());
            if(profile.isGaming() && ((Game) profile.getGame()).getStatus() == GameStatus.PLAYING){
                return;
            }
            e.setCancelled(true);
        }
    }
}
