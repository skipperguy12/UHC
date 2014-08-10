package net.njay.uhc.timer.timers;

import com.google.common.collect.Lists;
import net.njay.uhc.Config;
import net.njay.uhc.event.match.MatchBeginEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.match.PartyMatch;
import net.njay.uhc.match.party.Party;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;
import org.bukkit.ChatColor;

import java.util.List;

public class StartingCountdown extends UHCCountdown {

    public StartingCountdown(Match match) {
        super(match);
    }

    @Override
    public void tick(int seconds) {
        match.broadcast(ChatColor.GREEN + "Match starting in " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + seconds + ChatColor.GREEN + " second" + (seconds == 1 ? "!" : "s!"));
    }

    @Override
    public void end() {
        if (!(match instanceof PartyMatch)) {
            match.setState(MatchState.RUNNING);
            new MatchBeginEvent(match).call();
            match.broadcast(ChatColor.GREEN.toString() + ChatColor.GREEN + "The match has started!"); //TODO: maybe display match info?
        }else{
            PartyMatch pMatch = (PartyMatch) match;
            int validTeams = 0;
            for (Party party : pMatch.getParties())
                if (party.getMembers().size() >= pMatch.getPartySize())
                    validTeams++;
            if (validTeams < 2){
                match.broadcast("Failed to start! More valid parties needed!");
                match.getCountdownManager().start(new LobbyCountdown(match), Config.Match.playerwaitTime);
            }else{
                match.setState(MatchState.RUNNING);
                new MatchBeginEvent(match).call();
                match.broadcast(ChatColor.GREEN.toString() + ChatColor.GREEN + "The match has started!"); //TODO: maybe display match info?
                List<Party> badParties = Lists.newArrayList();
                for (UHCPlayer player : pMatch.getPlayers())
                    if (pMatch.getParty(player) == null || pMatch.getParty(player).getMembers().size() < pMatch.getPartySize()){
                        if (!badParties.contains(pMatch.getParty(player))) badParties.add(pMatch.getParty(player));
                        player.getBukkit().teleport(Config.Spawns.serverSpawn);
                        player.getBukkit().sendMessage("Your party did not meet the required number of players!");
                        player.setMatch(null);
                    }
                for (Party party : badParties)
                    pMatch.removeParty(party);
            }
        }
    }
}
