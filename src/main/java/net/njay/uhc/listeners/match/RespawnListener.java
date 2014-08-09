package net.njay.uhc.listeners.match;

import net.njay.uhc.UHC;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.util.location.teleport.DelayedTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getPlayer());
        if (player.getMatch() == null) return;
        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), new DelayedTeleport(player.getBukkit(), new Location(e.getPlayer().getWorld(), 0, 200, 0)), 1);
    }

}
