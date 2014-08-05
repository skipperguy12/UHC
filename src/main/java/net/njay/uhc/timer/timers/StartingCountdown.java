package net.njay.uhc.timer.timers;

import net.njay.uhc.event.match.MatchBeginEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.timer.UHCCountdown;
import org.bukkit.ChatColor;

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
        match.setState(MatchState.RUNNING);
        new MatchBeginEvent(match).call();
        match.broadcast(ChatColor.GREEN.toString() + ChatColor.GREEN + "The match has started!"); //TODO: maybe display match info?
    }
}
