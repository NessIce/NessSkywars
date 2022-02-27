package br.com.ness.skywars.commands.skywarscommand.subcommands;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.commands.skywarscommand.SubCommand;
import br.com.ness.skywars.game.Game;
import br.com.ness.skywars.game.GameStatus;
import org.bukkit.entity.Player;

public class StartSubCommand extends SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = Profile.getByPlayer(player);

        if(!profile.isGaming()){
            player.sendMessage("§cVocê não está em uma partida!");
            return;
        }

        Game game = (Game) profile.getGame();

        if(game.getStatus() == GameStatus.PREPARING){
            game.getPreparingGameTask().cancel();
            game.startingGame();
            return;
        }

        player.sendMessage("§cVocê não pode forçar o inicio desta partida nesse momento!");
    }
}
