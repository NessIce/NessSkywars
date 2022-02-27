package br.com.ness.skywars.game.map;

import br.com.ness.skywars.Skywars;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MapResume extends MapRenderer {

    private final String player;
    private final String kills;
    private final String coins;
    private final String time;

    public MapResume(String player, String kills, String coins, String time){
        this.player = player;
        this.kills = kills;
        this.coins = coins;
        this.time = time;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        try {
            BufferedImage image = ImageIO.read(new File(Skywars.getPlugin().getDataFolder().getPath()+"/game-images/"+name+".png"));
            mapCanvas.drawImage(0, 0, image);
            mapCanvas.drawText(33, 53, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.player);
            mapCanvas.drawText(96, 77, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.kills);
            mapCanvas.drawText(96, 94, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.coins);
            mapCanvas.drawText(85, 111, MinecraftFont.Font, "§"+MapPalette.matchColor(Color.BLACK)+";"+this.time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
