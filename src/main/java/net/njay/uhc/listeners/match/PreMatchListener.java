package net.njay.uhc.listeners.match;

import net.njay.uhc.UHC;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreMatchListener implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getPlayer());
        if (player.getMatch() == null) return;
        if (player.getMatch().getState() != MatchState.RUNNING)
            e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if ( !(e.getEntity() instanceof Player) && !(e.getDamager() instanceof Player)) return;
        UHCPlayer player = UHC.getPlayerManager().getPlayer((e.getEntity() instanceof Player ? (Player) e.getEntity() : (Player) e.getDamager()));
        if (player.getMatch() == null) return;
        if (player.getMatch().getState() != MatchState.RUNNING)
            e.setCancelled(true);
    }

}
