package net.njay.uhc.listeners.match;

import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.player.PlayerLeaveMatchEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.timers.EndingCountdown;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveMatchListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getEntity());
        if (player.getMatch() == null) return;
        player.getMatch().broadcast(e.getDeathMessage());
        e.setDeathMessage("");
        player.getMatch().addSpectator(player);
        broadcastRemaining(player.getMatch());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getPlayer());
        if (player.getMatch() == null) return;
        PlayerLeaveMatchEvent event = new PlayerLeaveMatchEvent(player.getMatch(), player);
        event.call();
    }

    @EventHandler
    public void onPlayerLeave(PlayerLeaveMatchEvent e) {
        e.getMatch().removePlayer(e.getPlayer());
        broadcastRemaining(e.getMatch());
        if (UHC.getPlayerManager().getParticipatingPlayers(e.getMatch()).size() == 1) {
            e.getMatch().broadcast(ChatColor.BLUE + UHC.getPlayerManager().getParticipatingPlayers(e.getMatch()).iterator().next().getBukkit().getName() +
                    ChatColor.GREEN + " has won the match! Congrats!");
            e.getMatch().getCountdownManager().start(new EndingCountdown(e.getMatch()), Config.Match.endTime);
        }else if (UHC.getPlayerManager().getParticipatingPlayers(e.getMatch()).size() < 1){
            e.getMatch().getCountdownManager().start(new EndingCountdown(e.getMatch()), 1);
        }
    }

    private void broadcastRemaining(Match match){
        match.broadcast(ChatColor.RED.toString() + UHC.getPlayerManager().getParticipatingPlayers(match).size() +
                ChatColor.GOLD + " players remain.");
    }
}