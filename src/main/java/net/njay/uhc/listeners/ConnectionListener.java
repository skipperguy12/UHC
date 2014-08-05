package net.njay.uhc.listeners;

import net.njay.uhc.UHC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        UHC.getMenu().show(event.getPlayer());
}

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){ UHC.getPlayerManager().removePlayer(e.getPlayer()); }
}
