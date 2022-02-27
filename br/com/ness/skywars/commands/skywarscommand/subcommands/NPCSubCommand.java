package br.com.ness.skywars.commands.skywarscommand.subcommands;

import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.utils.SerializerCore;
import br.com.ness.skywars.Skywars;
import br.com.ness.skywars.commands.skywarscommand.SubCommand;
import br.com.ness.skywars.playnpc.NPC;
import br.com.ness.skywars.playnpc.NPCType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NPCSubCommand extends SubCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length < 1){
            player.sendMessage("§cUtilize /sw jogarnpc add/remove <Id> [Modo]");
            return;
        }

        ConfigFile configFile = new ConfigFile("locations", Skywars.getPlugin().getDataFolder().getPath(), true, Skywars.getPlugin());

        if(args[0].equalsIgnoreCase("add") && args.length == 3){
            String id = args[1];
            String mode = args[2].toUpperCase();

            if(!isInteger(id)){
                player.sendMessage("§cO id digitado não é um número valido!");
                return;
            }

            if(NPC.getNPCs().containsKey(id)){
                player.sendMessage("§cJá existe um NPC com este id!");
                return;
            }

            if(!NPCType.has(mode)){
                player.sendMessage("§cNão existe um modo de jogo com este nome!");
                return;
            }

            NPC npc = new NPC(id, player.getLocation(), NPCType.valueOf(mode.toUpperCase()));
            npc.spawn();

            configFile.getConfig().set("locations.playnpc."+id+".location", SerializerCore.getLocationString(player.getLocation()));
            configFile.getConfig().set("locations.playnpc."+id+".type", mode.toUpperCase());
            configFile.save();

            player.sendMessage("§aO NPC de jogo com o id §f"+npc.getId()+"§a foi adcionado com sucesso!");
            return;
        }

        if(args[0].equalsIgnoreCase("debug")){
            for(Entity entity : player.getNearbyEntities(20,20,20)){
                entity.remove();
            }
        }

        if(args[0].equalsIgnoreCase("remove") && args.length == 2){
            String id = args[1];

            if(!isInteger(id)){
                player.sendMessage("§cO id digitado não é um número valido!");
                return;
            }

            if(!NPC.getNPCs().containsKey(id)){
                player.sendMessage("§cNão existe um NPC com este id!");
                return;
            }

            NPC npc = NPC.getNPC(id);
            npc.despawn();

            configFile.getConfig().set("locations.playnpc."+id, null);
            configFile.save();

            player.sendMessage("§cO NPC de jogo com id §f"+npc.getId()+" §cfoi removido com sucesso!");
            return;
        }
        player.sendMessage("§cUtilize /sw jogarnpc add/remove <Id> [Modo]");
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
