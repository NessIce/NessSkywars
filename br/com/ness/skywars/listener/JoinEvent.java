package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.listeners.core.CoreJoinEvent;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.skywarsmanager.SkywarsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoinEvent(CoreJoinEvent e){
        Player player = e.getPlayer();
        Profile profile = Profile.getByPlayer(player);

        SkywarsManager.joinLobby(profile);
    }
}
