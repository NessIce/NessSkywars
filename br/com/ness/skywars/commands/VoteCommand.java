package br.com.ness.skywars.commands;

import br.com.ness.skywars.game.GameType;
import br.com.ness.skywars.playnpc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class VoteCommand extends BukkitCommand {

    public VoteCommand(String cmd){
        super(cmd);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }

        NPC.getNPC(GameType.SOLO).forEach(npc -> npc.setCount(Integer.parseInt(args[0])));

//        Player player = (Player)sender;
//        Profile profile = Skywars.getProfile(player);
//
//        if(!profile.isGaming()){
//            player.sendMessage("§cVocê não está em uma partida para votar!");
//            return true;
//        }
//
//        if(profile.getGame().getStatus() != GameStatus.FINISHING){
//            player.sendMessage("§cVocê não pode votar mais nesta partida!");
//        }
//
//        Game game = profile.getGame();
//
//        if(game.hasVotePlayer(profile)){
//            player.sendMessage("§cVocê já votou neste mapa!");
//            return true;
//        }
//
//        if(args.length == 1){
//            switch (args[0].toLowerCase()){
//                case "otimo":{
//                    player.sendMessage("§aMaravilha, que bom que você gostou do mapa. Com certeza faremos mais mapas assim no futuro!");
//                    game.addVotePlayer(profile, "otimo");
//                    break;
//                }
//                case "bom":{
//                    player.sendMessage("§eOpa, que bom que você curtiu o mapa, Tentaremos fazer mais mapas como este no futuro.");
//                    game.addVotePlayer(profile, "bom");
//                    break;
//                }
//                case "aceitavel":{
//                    player.sendMessage("§6Com certeza esse não é uma boa nota, tentaremos fazer mapas melhores no futuro.");
//                    game.addVotePlayer(profile, "aceitavel");
//                    break;
//                }
//                case "ruim":{
//                    player.sendMessage("§cParece que você não gostou muito... tentaremos fazer mapas melhores no futuro.");
//                    game.addVotePlayer(profile, "ruim");
//                    break;
//                }
//                case "horrivel":{
//                    player.sendMessage("§4Que pena que você não gostou, trabalharemos para construir mapas melhores no futuro.");
//                    game.addVotePlayer(profile, "horrivel");
//                    break;
//                }
//            }
//        }
        return false;
    }
}
