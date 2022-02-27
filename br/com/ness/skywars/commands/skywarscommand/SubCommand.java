package br.com.ness.skywars.commands.skywarscommand;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract void execute(Player player, String[] args);
}
