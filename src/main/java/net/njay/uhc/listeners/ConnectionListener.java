package net.njay.uhc.listeners;

import net.njay.uhc.UHC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        UHC.getMatchManager().getMenu().show(event.getPlayer());
    }
}
