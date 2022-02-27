package br.com.ness.skywars.commands.skywarscommand.subcommands;

import br.com.ness.skywars.commands.skywarscommand.SubCommand;
import br.com.ness.skywars.game.GameCuboid;
import org.bukkit.entity.Player;

public class CageSubCommand extends SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage("§cUtilize /sw cage <Nome>");
            return;
        }

        GameCuboid cage = new GameCuboid(player.getLocation().clone().add(1,3,1), player.getLocation().clone().subtract(1,1,1));
        cage.save(args[0].toLowerCase(), "cages");
        player.sendMessage("§aVocê acabou de criar uma nova Cage!");
    }
}
