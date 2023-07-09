package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import tomasborsje.plugin.fantasymmo.FantasyMMO;

/**
 * Called when a player pings the server in the server list.
 * We use this to display our fancy MOTD.
 */
public class ServerListPingListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd(FantasyMMO.motd);
    }
}
