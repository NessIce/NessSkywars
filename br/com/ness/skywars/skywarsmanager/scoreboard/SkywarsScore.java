package br.com.ness.skywars.skywarsmanager.scoreboard;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.scoreboard.Sidebar;
import br.com.ness.core.bukkit.profile.scoreboard.VirtualTeam;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class SkywarsScore extends Sidebar {

    @Override
    public List<String> getDisplayName() {
        List<String> displayName = new ArrayList<>();
        displayName.add("§6§lSKYWARS");
        displayName.add("§e§lS§6§lKYWARS");
        displayName.add("§f§lS§e§lK§6§lYWARS");
        displayName.add("§e§lS§f§lK§e§lY§6§lWARS");
        displayName.add("§6§lS§e§lK§f§lY§e§lW§6§lARS");
        displayName.add("§6§lSK§e§lY§f§lW§e§lA§6§lRS");
        displayName.add("§6§lSKY§e§lW§f§lA§e§lR§6§lS");
        displayName.add("§6§lSKYW§e§lA§f§lR§e§lS");
        displayName.add("§6§lSKYWA§e§lR§f§lS");
        displayName.add("§6§lSKYWAR§e§lS");
        displayName.add("§6§lSKYWARS");
        displayName.add("§6§lSKYWARS");
        displayName.add("§6§lSKYWARS");
        displayName.add("§e§lSKYWARS");
        displayName.add("§f§lSKYWARS");
        displayName.add("§e§lSKYWARS");
        return displayName;
    }

    @Override
    public List<VirtualTeam> getScores(Profile profile) {
        List<VirtualTeam> scores = new ArrayList<>();


        int winsGeral =
                ((Integer)profile.get("skywars", "wins-solo"))+
                        ((Integer)profile.get("skywars", "wins-duel"))+
                        ((Integer)profile.get("skywars", "wins-double"))+
                        ((Integer)profile.get("skywars", "wins-team"));

        int killsGeral =
                ((Integer)profile.get("skywars", "kills-solo"))+
                        ((Integer)profile.get("skywars", "kills-duel"))+
                        ((Integer)profile.get("skywars", "kills-double"))+
                        ((Integer)profile.get("skywars", "kills-team"));

        int deathsGeral =
                ((Integer)profile.get("skywars", "deaths-solo"))+
                        ((Integer)profile.get("skywars", "deaths-duel"))+
                        ((Integer)profile.get("skywars", "deaths-double"))+
                        ((Integer)profile.get("skywars", "deaths-team"));

        int matchesGeral =
                ((Integer)profile.get("skywars", "matches-solo"))+
                        ((Integer)profile.get("skywars", "matches-duel"))+
                        ((Integer)profile.get("skywars", "matches-double"))+
                        ((Integer)profile.get("skywars", "matches-team"));

        scores.add(new VirtualTeam(13, "§1"));
        scores.add(new VirtualTeam(12, "§f Geral:"));
        scores.add(new VirtualTeam(11, "§f  Abates: §a"+killsGeral));
        scores.add(new VirtualTeam(10, "§f  Vitórias: §a"+winsGeral));
        scores.add(new VirtualTeam(9, "§f  Mortes: §a"+deathsGeral));
        scores.add(new VirtualTeam(8, "§f  Partidas: §a"+matchesGeral));
        scores.add(new VirtualTeam(7, "§2"));
        scores.add(new VirtualTeam(6, "§f Rank: §a"+profile.get("geral", "rank")));
        scores.add(new VirtualTeam(5, "§3"));
        scores.add(new VirtualTeam(4, "§f Coins: §e"+profile.getCoins()));
        scores.add(new VirtualTeam(3, "§f Cash: §6"+profile.getCash()));
        scores.add(new VirtualTeam(2, "§f Ruby: §c"+profile.getRuby()));
        scores.add(new VirtualTeam(1, "§4"));
        scores.add(new VirtualTeam(0, "§eloja.fryzzen.com"));

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
