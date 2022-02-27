package br.com.ness.skywars.game.map;

import br.com.ness.skywars.Skywars;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapImage extends MapRenderer {

    private final String name;
    public MapImage(String name){
        this.name = name;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        try {
            BufferedImage image = ImageIO.read(new File(Skywars.getPlugin().getDataFolder().getPath()+"/game-images/"+name+".png"));
            mapCanvas.drawImage(0, 0, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
