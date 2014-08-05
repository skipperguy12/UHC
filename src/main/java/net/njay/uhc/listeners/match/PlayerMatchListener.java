package net.njay.uhc.listeners.match;

import net.njay.uhc.Config;
import net.njay.uhc.event.match.player.PlayerJoinMatchEvent;
import net.njay.uhc.event.match.player.PlayerLeaveMatchEvent;
import net.njay.uhc.util.location.LocationUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMatchListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinMatchEvent e){
        e.getPlayer().getBukkit().teleport(LocationUtil.findSuitableLocation(
                new Location(e.getMatch().getWorld(), 0, 0, 0), Config.Match.matchRadius));
    }

    @EventHandler
    public void onPlayerQuit(PlayerLeaveMatchEvent e){
        //TODO: Check to see if there is only one player left, if so, then end the game

    }


}
