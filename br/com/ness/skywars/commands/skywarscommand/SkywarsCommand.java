package br.com.ness.skywars.commands.skywarscommand;

import br.com.ness.skywars.commands.skywarscommand.subcommands.CageSubCommand;
import br.com.ness.skywars.commands.skywarscommand.subcommands.CreateSubCommand;
import br.com.ness.skywars.commands.skywarscommand.subcommands.NPCSubCommand;
import br.com.ness.skywars.commands.skywarscommand.subcommands.StartSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SkywarsCommand extends BukkitCommand {

    private final CreateSubCommand createSubCommand;
    private final NPCSubCommand npcSubCommand;
    private final CageSubCommand cageSubCommand;
    private final StartSubCommand startSubCommand;

    public SkywarsCommand(String cmd){
        super(cmd);
        getAliases().add("sw");

        createSubCommand = new CreateSubCommand();
        npcSubCommand = new NPCSubCommand();
        cageSubCommand = new CageSubCommand();
        startSubCommand = new StartSubCommand();
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("ness.skywars.admin")){
            player.sendMessage("§cApenas §4Gerente §cou superior pode utilizar este comando!");
            return true;
        }

        if(args.length > 0){
            String argument = args[0];
            List<String> newArgs = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (i == 0) { continue; }
                newArgs.add(args[i]);
            }

            switch (argument.toLowerCase()){
                case "create" : case "criar":{
                    createSubCommand.execute(player, newArgs.toArray(new String[0]));
                    return true;
                }

                case "playnpc" : case "jogarnpc":{
                    npcSubCommand.execute(player, newArgs.toArray(new String[0]));
                    return true;
                }

                case "cage" : case "jaula":{
                    cageSubCommand.execute(player, newArgs.toArray(new String[0]));
                    return true;
                }

                case "start" : case "iniciar":{
                    startSubCommand.execute(player, newArgs.toArray(new String[0]));
                    return true;
                }
            }
        }

        player.sendMessage("");
        player.sendMessage(" §c§lCOMANDOS DO SKYWARS");
        player.sendMessage("");
        player.sendMessage("§c/sw criar - para criar um jogo!");
        player.sendMessage("§c/sw jogarnpc - para setar um npc!");
        player.sendMessage("§c/sw jaula <Nome> - para criar jaula!");
        player.sendMessage("§c/sw iniciar - para iniciar partida!");
        player.sendMessage("");
        return false;
    }
}
