package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.util.Vector;

import java.util.Arrays;

/**
 * Class that controls the boss bar HUD for each player.
 * This is primarily used as a compass.
 */
public class PlayerBossBar {
    private final static float DEGREES = 120;
    private final static int TOTAL_SIZE = 200;
    private final BossBar bar;
    private final PlayerData playerData;
    final char[] fullCompass = new char[TOTAL_SIZE];
    int startYaw = 0;
    int endYaw = 0;

    public PlayerBossBar(PlayerData playerData) {
        this.playerData = playerData;
        bar = playerData.player.getServer().createBossBar("Compass", BarColor.WHITE, BarStyle.SOLID);
        bar.addPlayer(playerData.player);
    }

    void putIconOnCompass(float yaw, char icon) {
        // Clamp yaw to between 0 and 360
        yaw = yaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        // Calculate index of icon
        int index = (int) (yaw / 360 * TOTAL_SIZE);
        fullCompass[index] = icon;
    }

    public void render() {
        // Get player facing direction (0-360)
        float yaw = playerData.player.getLocation().getYaw() + 180;
        startYaw = (int) (yaw - DEGREES / 2);
        endYaw = (int) (yaw + DEGREES / 2);

        // Store compass icons
        Arrays.fill(fullCompass, '-');

        // Set compass markers
        for (int markerYaw = 0; markerYaw < 360; markerYaw += 30) {
            putIconOnCompass(markerYaw, '|');
        }

        // Add N S E W markers
        putIconOnCompass(0, 'N');
        putIconOnCompass(90, 'E');
        putIconOnCompass(180, 'S');
        putIconOnCompass(270, 'W');

        // Add quest indicator
        if (!playerData.activeQuests.isEmpty()) {
            // Add quest indicator, calculate angle
            Vector questVec = playerData.activeQuests.get(0).getObjectiveLocation();
            Location questLoc = new Location(playerData.player.getWorld(), questVec.getX(), questVec.getY(), questVec.getZ());
            Location playerLoc = playerData.player.getEyeLocation();

            // Get direction to quest
            Location direction = questLoc.setDirection(playerLoc.subtract(questLoc).toVector());

            // Get yaw direction to quest
            int angle = (int) (direction.getYaw());
            putIconOnCompass(angle, '⏺');
        }

        // Create string from array
        String fullCompass = new String(this.fullCompass);
        String title; // The string we actually display

        // Cut out the part we need
        try {
            if (startYaw > 0 && endYaw <= 360) {
                // Within boundary
                title = fullCompass.substring((int) (startYaw / 360f * TOTAL_SIZE), (int) (endYaw / 360f * TOTAL_SIZE));
            } else {
                // Outside boundary
                if (startYaw < 0) {
                    // Start is negative
                    title = fullCompass.substring((int) ((360 + startYaw) / 360f * TOTAL_SIZE), TOTAL_SIZE) + fullCompass.substring(0, (int) (endYaw / 360f * TOTAL_SIZE));
                } else {
                    // End is greater than 360
                    title = fullCompass.substring((int) (startYaw / 360f * TOTAL_SIZE), TOTAL_SIZE) + fullCompass.substring(0, (int) ((endYaw - 360) / 360f * TOTAL_SIZE));
                }
            }
            bar.setTitle(applyColours(title));
        } catch (Exception e) {
            // TODO: Sometimes an exception is thrown on line 96, doesn't affect rendering and not sure why
            return;
        }
    }

    private String applyColours(String stringToApply) {
        // Add ChatColours for each icon type
        String applied = stringToApply.replaceAll("\\|", ChatColor.WHITE + "|");
        applied = applied.replaceAll("⏺", ChatColor.YELLOW + "⏺");
        applied = applied.replaceAll("-", ChatColor.GRAY + "-");
        // Use a regex that matches N E W S to replace
        applied = applied.replaceAll("N", ChatColor.WHITE + "N");
        applied = applied.replaceAll("E", ChatColor.WHITE + "E");
        applied = applied.replaceAll("S", ChatColor.WHITE + "S");
        applied = applied.replaceAll("W", ChatColor.WHITE + "W");

        return applied;
    }
}