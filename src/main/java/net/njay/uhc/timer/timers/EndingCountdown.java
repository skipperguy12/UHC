package net.njay.uhc.timer.timers;

import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;
import org.bukkit.ChatColor;

import java.util.Collection;

public class EndingCountdown extends UHCCountdown {

    public EndingCountdown(Match match) {
        super(match);
    }

    @Override
    public void tick(int seconds) {
        match.broadcast(ChatColor.GREEN + "Match ending in " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + seconds + ChatColor.GREEN + " second" + (seconds == 1 ? "!" : "s!"));
    }

    @Override
    public void end() {
        UHC.getMatchManager().cycle(match);
    }
}
