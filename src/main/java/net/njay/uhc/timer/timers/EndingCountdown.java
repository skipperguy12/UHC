package net.njay.uhc.timer.timers;

import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.PartyMatch;
import net.njay.uhc.match.party.Party;
import net.njay.uhc.timer.UHCCountdown;
import org.bukkit.ChatColor;

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
        if (match instanceof PartyMatch)
            UHC.getMatchManager().cycle(match, ((PartyMatch)match).getPartySize());
        else
            UHC.getMatchManager().cycle(match, -1);
    }
}
