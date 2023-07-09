package tomasborsje.plugin.fantasymmo.handlers;

import com.github.johnnyjayjay.spigotmaps.MapBuilder;
import com.github.johnnyjayjay.spigotmaps.RenderedMap;
import com.github.johnnyjayjay.spigotmaps.rendering.ImageRenderer;
import com.github.johnnyjayjay.spigotmaps.util.ImageTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.FantasyMMO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * Stores custom rendered maps.
 */
public class MapHandler {
    private static final String mapFolder = "maps";
    public static MapHandler instance = new MapHandler();
    private RenderedMap worldMap;
    private MapHandler() { }

    public void init() {
        // Load map data
        try {
            Path p = Path.of(FantasyMMO.dataFolder.getPath(), mapFolder, "worldmap.png").toAbsolutePath();
            Bukkit.getLogger().info("Loading world map from "+ p);

            BufferedImage catImage = ImageIO.read(p.toFile()); // read an image from a source, e.g. a file
            catImage = ImageTools.resizeToMapSize(catImage); // resize the image to the minecraft map size

            ImageRenderer catRenderer = ImageRenderer.builder()
                    .image(catImage) // set the image to render
                    .build(); // build the instance

            RenderedMap map = MapBuilder.create() // make a new builder
                    .addRenderers(catRenderer) // add the renderers to this map
                    .world(Bukkit.getServer().getWorlds().get(0)) // set the world this map is bound to, e.g. the world of the target player
                    .build(); // build the map


            worldMap = map;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack getWorldMap() {
        return worldMap == null ? new ItemStack(Material.AIR) : worldMap.createItemStack(ChatColor.GOLD+""+ChatColor.BOLD+"World Map", ChatColor.GRAY+"Right click to open the menu.");
    }
}
