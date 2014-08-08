package net.njay.uhc.listeners.match;

import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.player.PlayerLeaveMatchEvent;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.timers.EndingCountdown;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveMatchListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getEntity());
        if (player.getMatch() == null) return;
        player.getMatch().broadcast(e.getDeathMessage());
        e.setDeathMessage("");
        PlayerLeaveMatchEvent event = new PlayerLeaveMatchEvent(player.getMatch(), player, PlayerLeaveMatchEvent.LeaveCause.DEATH);
        event.call();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerQuitEvent e){
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getPlayer());
        if (player.getMatch() == null) return;
        PlayerLeaveMatchEvent event = new PlayerLeaveMatchEvent(player.getMatch(), player, PlayerLeaveMatchEvent.LeaveCause.QUIT);
        event.call();
    }

    @EventHandler
    public void onPlayerLeave(PlayerLeaveMatchEvent e){
        e.getPlayer().setMatch(null);
        if (e.getCause() == PlayerLeaveMatchEvent.LeaveCause.QUIT)
            e.getMatch().broadcast(ChatColor.BLUE + e.getPlayer().getBukkit().getName()
                    + ChatColor.GOLD + " has left the match!");
        e.getMatch().broadcast(ChatColor.RED.toString() + UHC.getPlayerManager().getPlayers(e.getMatch()).size() +
                ChatColor.GOLD + " players remain.");
        if (UHC.getPlayerManager().getPlayers(e.getMatch()).size() == 1){
            e.getMatch().broadcast(ChatColor.BLUE + UHC.getPlayerManager().getPlayers(e.getMatch()).iterator().next().getBukkit().getName() +
                ChatColor.GREEN + " has won the match! Congrats!");
            e.getMatch().getCountdownManager().start(new EndingCountdown(e.getMatch()), 10);
        }
    }

}
