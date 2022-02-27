package br.com.ness.skywars.playnpc;

import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.skywars.menus.playnpc.DoubleNPCMenu;
import br.com.ness.skywars.menus.playnpc.DuelNPCMenu;
import br.com.ness.skywars.menus.playnpc.SoloNPCMenu;
import br.com.ness.skywars.menus.playnpc.TeamNPCMenu;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCManager implements Listener {

    @EventHandler
    public void onClickNPCEvent(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked() instanceof ArmorStand){
            if(e.getRightClicked().hasMetadata("soloNPC")){
                e.setCancelled(true);
                new SoloNPCMenu(MenuManager.getMenu(e.getPlayer())).open();
                return;
            }
            if(e.getRightClicked().hasMetadata("duelNPC")){
                e.setCancelled(true);
                new DuelNPCMenu(MenuManager.getMenu(e.getPlayer())).open();
                return;
            }
            if(e.getRightClicked().hasMetadata("doubleNPC")){
                e.setCancelled(true);
                new DoubleNPCMenu(MenuManager.getMenu(e.getPlayer())).open();
                return;
            }
            if(e.getRightClicked().hasMetadata("teamNPC")){
                new TeamNPCMenu(MenuManager.getMenu(e.getPlayer())).open();
                e.setCancelled(true);
            }
        }
    }
}
