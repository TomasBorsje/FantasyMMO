package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import tomasborsje.plugin.fantasymmo.FantasyMMO;

public class SoundUtil {
    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    public static void PlayEatSound(Player player) {
        player.playSound(player, Sound.ENTITY_GENERIC_EAT, 1, 1);
        // Play two delayed sounds
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.ENTITY_GENERIC_EAT, 1, 1), 5);
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.ENTITY_GENERIC_EAT, 1, 1), 10);
    }
    public static void PlayDrinkSound(Player player) {
        player.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1, 1);
        // Play two delayed sounds
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1, 1), 5);
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1, 1), 10);
    }

    public static void PlayQuestCompleteSound(Player player) {
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        // Play two delayed sounds
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.5f), 3);
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 2f), 6);
    }

    public static void PlayNPCInteractSound(Player player, float pitch) {
        player.playSound(player, Sound.ENTITY_VILLAGER_YES, 1, pitch);
    }

    public static void PlayQuestAcceptSound(Player player) {
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 2f);
    }

    public static void PlayQuestProgressSound(Player player) {
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        // Play one delayed sound
        scheduler.runTaskLater(FantasyMMO.Plugin, () -> player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.5f), 5);
    }
}
