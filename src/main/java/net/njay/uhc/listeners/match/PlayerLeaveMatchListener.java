package net.njay.uhc.listeners.match;

import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.player.PlayerEliminateEvent;
import net.njay.uhc.event.match.player.PlayerLeaveMatchEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.match.PartyMatch;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.timers.EndingCountdown;
import net.njay.uhc.util.location.teleport.spawn.ForceRespawnUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveMatchListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ForceRespawnUtil.forceRespawn(e.getEntity());
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getEntity());
        if (player.getMatch() == null) return;
        player.getMatch().broadcast(e.getDeathMessage());
        e.setDeathMessage("");
        player.getMatch().addSpectator(player);
        broadcastRemaining(player.getMatch());
        checkEnd(player.getMatch());
        new PlayerEliminateEvent(player.getMatch(), player).call();
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
        e.getPlayer().getBukkit().teleport(Config.Spawns.serverSpawn);
        e.getMatch().removePlayer(e.getPlayer());
        broadcastRemaining(e.getMatch());
        checkEnd(e.getMatch());
    }

    private void checkEnd(Match match){
        if (!(match instanceof PartyMatch)) {
            if (UHC.getPlayerManager().getParticipatingPlayers(match).size() == 1) {
                match.broadcast(ChatColor.BLUE + UHC.getPlayerManager().getParticipatingPlayers(match).iterator().next().getBukkit().getName() +
                        ChatColor.GREEN + " has won the match! Congrats!");
                match.getCountdownManager().start(new EndingCountdown(match), Config.Match.endTime);
                match.setState(MatchState.FINISHED);
            } else if (UHC.getPlayerManager().getParticipatingPlayers(match).size() < 1) {
                match.getCountdownManager().start(new EndingCountdown(match), 1);
            }
        }else{
            PartyMatch partyMatch = (PartyMatch) match;
            if (partyMatch.getAliveParties().size() <= 1){
                match.getCountdownManager().start(new EndingCountdown(match), Config.Match.endTime);
                match.setState(MatchState.FINISHED);
                match.broadcast("Party " + partyMatch.getAliveParties().get(0).getName() + " has won the match! Congrats!");
            }
        }
    }

    private void broadcastRemaining(Match match){
        match.broadcast(ChatColor.RED.toString() + UHC.getPlayerManager().getParticipatingPlayers(match).size() +
                ChatColor.GOLD + " players remain.");
    }
}