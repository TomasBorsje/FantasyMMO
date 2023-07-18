package tomasborsje.plugin.fantasymmo.events;

import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class TabCompleteCommandListener implements Listener {

    private static List<String> itemIdCache = null; //
    private static List<String> countIndicator = List.of("[count]");

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String buffer = event.getBuffer();

        // Item give command
        if(buffer.startsWith("/giveitem")) {
            // Show number two or more spaces exist (the item id is already specified)
            if(StringUtils.countMatches(buffer, " ") > 1) {
                event.setCompletions(countIndicator);
            }
            // Otherwise show all the item ids
            else {
                // Load item ids if not cached yet
                if (itemIdCache == null) {
                    itemIdCache = ItemRegistry.ITEMS.getAllValues().stream()
                            .map(item -> item.get().getCustomId()).collect(Collectors.toList());
                }
                event.setCompletions(itemIdCache);
            }
        }
    }
}
