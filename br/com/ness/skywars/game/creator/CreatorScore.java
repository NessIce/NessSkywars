package br.com.ness.skywars.game.creator;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.scoreboard.Sidebar;
import br.com.ness.core.bukkit.profile.scoreboard.VirtualTeam;
import br.com.ness.skywars.game.chest.ChestClass;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class CreatorScore extends Sidebar {

    @Override
    public List<String> getDisplayName() {
        List<String> displayName = new ArrayList<>();
        displayName.add("§6§lCRIANDO PARTIDA");
        return displayName;
    }

    @Override
    public List<VirtualTeam> getScores(Profile profile) {
        List<VirtualTeam> scores = new ArrayList<>();
        GamePreset gamePreset = GameCreator.getGamePreset(profile);

        scores.add(new VirtualTeam(15, "§1"));
        scores.add(new VirtualTeam(14, " §fNome: §a", "game").setSuffix(gamePreset.getName()));
        scores.add(new VirtualTeam(13, " §fTipo: §a", "type").setSuffix(gamePreset.getType().getName()));
        scores.add(new VirtualTeam(12, "§2"));
        scores.add(new VirtualTeam(11, " §fEntrada: ", "spawnJoin").setSuffix(gamePreset.getJoinSpawn()!=null?"§a✔":"§c✘"));
        scores.add(new VirtualTeam(10, " §fSpawns: §a", "spawns").setSuffix(gamePreset.getSpawns().size()+"/"+gamePreset.getMax()));
        scores.add(new VirtualTeam(9, "§3"));
        scores.add(new VirtualTeam(8, " §fBaus:"));
        scores.add(new VirtualTeam(7, "  §fBasico: §a", "chestBasic").setSuffix(gamePreset.getChestsClass(ChestClass.BASIC).size()));
        scores.add(new VirtualTeam(6, "  §fComplemento: §a", "chestComp").setSuffix(gamePreset.getChestsClass(ChestClass.COMPLEMENT).size()));
        scores.add(new VirtualTeam(5, "  §fFeast: §a", "chestFeast").setSuffix(gamePreset.getChestsClass(ChestClass.FEAST).size()));
        scores.add(new VirtualTeam(4, "§4"));
        scores.add(new VirtualTeam(3, " §fCuboid arena: ", "cuboidArena").setSuffix(gamePreset.getCuboidGame()!=null?"§a✔":"§c✘"));
        scores.add(new VirtualTeam(2, " §fCuboid spawn: ", "cuboidSpawn").setSuffix(gamePreset.getCuboidSpawn()!=null?"§a✔":"§c✘"));
        scores.add(new VirtualTeam(1, "§5"));
        return scores;
    }

    @Override
    public List<VirtualTeam> getRoles(Profile profile) {
        List<VirtualTeam> roles = new ArrayList<>();
        roles.add(new VirtualTeam("a_master", "§6[Master] ", ""));
        roles.add(new VirtualTeam("b_gerente", "§4[Gerente] ", ""));
        roles.add(new VirtualTeam("c_admin", "§c[Admin] ", ""));
        roles.add(new VirtualTeam("d_moderador", "§2[Moderador] ", ""));
        roles.add(new VirtualTeam("e_ajudante", "§e[Ajudante] ", ""));
        roles.add(new VirtualTeam("f_youtuber", "§c[YouTuber] ", ""));
        roles.add(new VirtualTeam("g_guerreiro", "§b[Guerreiro] ", ""));
        roles.add(new VirtualTeam("h_feiticeiro", "§a[Feiticeiro] ", ""));
        roles.add(new VirtualTeam("i_alquimista", "§5[Alquimista] ", ""));
        roles.add(new VirtualTeam("j_jogador", "§7", ""));
        return roles;
    }

    @Override
    public void applyRoles(Profile profile, Team teamRole){
        for(Profile profiles : Profile.getProfiles()){
            Team teamProfile = profile.getScoreCore().getTeam(profiles.getGroup().getTeam());
            if(teamRole.equals(teamProfile)){
                if(!teamRole.hasEntry(profiles.getPlayer().getName())){
                    teamRole.addEntry(profiles.getPlayer().getName());
                }
            }
        }
    }
}
