package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Stop hunger from going below max for players.
 */
public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void OnFoodLevelChange(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }
}
