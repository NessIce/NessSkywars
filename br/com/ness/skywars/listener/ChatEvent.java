package br.com.ness.skywars.listener;

import br.com.ness.core.bukkit.message.JsonMessage;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.game.Game;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        Player player = e.getPlayer();
        Profile profile = Profile.getByPlayer(player);

        String color = player.hasPermission("ness.chat")?"§f":"§7";

        if(!profile.isGaming()){
            JsonMessage message = new JsonMessage();
            message.addText(profile.getDisplayName(),
                    ClickEvent.Action.SUGGEST_COMMAND, "/tell "+ profile.getPlayer().getName()+" ",
                    HoverEvent.Action.SHOW_TEXT,
                    profile.getDisplayName()+"\n"+
                    "§fGrupo: "+profile.getGroup().getDisplayName()+"\n"+
                    "\n"+
                    "§eClique para mandar uma mensagem privada!");
            message.addText("§a: "+color+e.getMessage());

            Bukkit.getOnlinePlayers().forEach(p->{ if(!Profile.getByPlayer(p).isGaming()){ message.sendMessage(p);}});
            return;
        }

        Game game = (Game) profile.getGame();

        if(game.getSpectators().contains(profile)){
            game.getSpectators().forEach(p->p.sendMessage("§8[ESPEC] "+profile.getDisplayName()+"§a: §f"+e.getMessage()));
            return;
        }

        JsonMessage message = new JsonMessage();
        message.addText(profile.getDisplayName(),
                ClickEvent.Action.SUGGEST_COMMAND, "/tell "+ profile.getPlayer().getName()+" ",
                HoverEvent.Action.SHOW_TEXT,
                profile.getDisplayName()+"\n"+
                        "§fGrupo: "+profile.getGroup().getDisplayName()+"\n"+
                        "\n"+
                        "§eClique para mandar uma mensagem privada!");
        message.addText("§a: "+color+e.getMessage());

        game.getPlayers().forEach(p->message.sendMessage(p.getPlayer()));
    }
}
