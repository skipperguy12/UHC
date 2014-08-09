package net.njay.uhc.listeners;

import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.util.location.teleport.DelayedTeleport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        UHC.getMenu().show(event.getPlayer());
        event.getPlayer().sendMessage(Config.Messages.joinMessage);
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), new DelayedTeleport(event.getPlayer(), Config.Spawns.serverSpawn), 1);

        for (Player player : Bukkit.getOnlinePlayers()) {
            event.getPlayer().showPlayer(player);
            player.showPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){ UHC.getPlayerManager().removePlayer(e.getPlayer()); }

}
