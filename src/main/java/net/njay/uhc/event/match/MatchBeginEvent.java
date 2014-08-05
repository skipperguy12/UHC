package net.njay.uhc.event.match;

import net.njay.uhc.match.Match;
import org.bukkit.event.HandlerList;

public class MatchBeginEvent extends MatchEvent {

    public MatchBeginEvent(Match match) {
        super(match);
    }

}
