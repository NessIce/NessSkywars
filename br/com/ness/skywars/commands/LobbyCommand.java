package br.com.ness.skywars.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LobbyCommand extends BukkitCommand {

    public List<Player> confirmLobby;

    public LobbyCommand(String cmd){
        super(cmd);
        getAliases().add("hub");

        confirmLobby = new ArrayList<>();
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }
        Player player = (Player)sender;

//        if(args.length == 1 && args[0].equalsIgnoreCase("definir")){
//            if(player.hasPermission("ness.lobby")){
//                Skywars.getLocationsFile().getConfig().set("locations.lobby", LocationUtils.getData(player.getLocation()));
//                Skywars.getLocationsFile().save();
//                player.sendMessage("§aO lobby foi definido para a sua localização!");
//                return true;
//            }
//            player.sendMessage("§cApenas §6Master §cou superior pode utilizar este comando!");
//            return true;
//        }
//
//        Profile profile = Skywars.getProfile(player);
//
//        if(profile.getMeta().isLobbyConfirm()){
//            if(!confirmLobby.contains(player)){
//                confirmLobby.add(player);
//                player.sendMessage("§eVocê tem certeza? Utilize /lobby novamente para voltar ao lobby.");
//                return true;
//            }
//            confirmLobby.remove(player);
//        }
//        player.sendMessage("§aConectando...");
//
//        if(profile.isGaming()){
//            profile.quitGame();
//            profile.sendLobby();
//            return true;
//        }
//
//        player.teleport(LocationUtils.lobbyLocation());
        return false;
    }
}
