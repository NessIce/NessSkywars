package br.com.ness.skywars;

import br.com.ness.core.logger.CoreLogger;
import br.com.ness.skywars.commands.LobbyCommand;
import br.com.ness.skywars.commands.VoteCommand;
import br.com.ness.skywars.commands.skywarscommand.SkywarsCommand;
import br.com.ness.skywars.game.creator.GameCreator;
import br.com.ness.skywars.listener.*;
import br.com.ness.skywars.playnpc.NPC;
import br.com.ness.skywars.playnpc.NPCManager;
import br.com.ness.skywars.skywarsmanager.SkywarsHotbar;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Skywars extends JavaPlugin {
    @Override
    public void onEnable() {

        ((CraftServer) getServer()).getCommandMap().register("skywars", new SkywarsCommand("skywars"));
        ((CraftServer) getServer()).getCommandMap().register("vote", new VoteCommand("vote"));
        ((CraftServer) getServer()).getCommandMap().register("lobby", new LobbyCommand("lobby"));

        NPC.register(this);
        SkywarsHotbar.register(this);

        Bukkit.getPluginManager().registerEvents(new GameCreator(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new NPCManager(), this);
        Bukkit.getPluginManager().registerEvents(new ChangeWorldEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DropEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);

        Bukkit.getConsoleSender().sendMessage("§6[NessSkywars] §aativado com sucesso!");
    }

    @Override
    public void onDisable() {
        NPC.unregister();
        Bukkit.getConsoleSender().sendMessage("§6[NessSkywars] §cdesativado com sucesso!");
    }

    public static Skywars getPlugin(){
        return Skywars.getPlugin(Skywars.class);
    }
}
