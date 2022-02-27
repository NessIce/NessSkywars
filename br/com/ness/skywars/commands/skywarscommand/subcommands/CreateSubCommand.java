package br.com.ness.skywars.commands.skywarscommand.subcommands;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.commands.skywarscommand.SubCommand;
import br.com.ness.skywars.game.GameType;
import br.com.ness.skywars.game.creator.GameCreator;
import org.bukkit.entity.Player;

public class CreateSubCommand extends SubCommand {

    @Override//  /sw create <Name> <Modo>
    public void execute(Player player, String[] args) {
        if(args.length != 4){
            player.sendMessage("§cUtilize /sw criar <Nome> <Modo> <Min> <Max>");
            return;
        }

        String name = args[0];
        String type = args[1];
        String min = args[2];
        String max = args[3];

        if(!GameType.has(type)){
            player.sendMessage("§cNão existe um modo de jogo com este nome!");
            return;
        }

        if(!isInteger(min) || !isInteger(max)){
            player.sendMessage("§cUtilize valores validos para o minimo e o maximo!");
            return;
        }

        Profile profile = Profile.getByPlayer(player);

        if(GameCreator.inCreatorMode(profile)){
            player.sendMessage("§cVocê já este no modo criador!");
            return;
        }

        ConfigFile file = new ConfigFile(name.toLowerCase(), Skywars.getPlugin().getDataFolder().getPath()+"/games", false, Skywars.getPlugin());

        file.getConfig().set("game.name", name);
        file.getConfig().set("game.type", type.toUpperCase());
        file.getConfig().set("game.min", Integer.valueOf(min));
        file.getConfig().set("game.max", Integer.valueOf(max));
        file.save();

        GameCreator.addCreatorMode(profile, file);

        player.sendMessage("");
        player.sendMessage(" §6§lMODO CRIADOR");
        player.sendMessage(" §eVocê esta criando o jogo §f"+name+"§e!");
        player.sendMessage("");
    }

    private boolean isInteger(String integer){
        try {
            Integer.valueOf(integer);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
