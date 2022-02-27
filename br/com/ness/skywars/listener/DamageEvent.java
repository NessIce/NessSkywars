package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onDamageEvent(EntityDamageEvent e){
        if (e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            Profile profile = Profile.getByPlayer(player);

            if (!profile.isGaming()) {
                e.setCancelled(true);
                return;
            }

            if (((Game)profile.getGame()).getStatus() != GameStatus.PLAYING) {
                e.setCancelled(true);
            }
        }
    }
}
