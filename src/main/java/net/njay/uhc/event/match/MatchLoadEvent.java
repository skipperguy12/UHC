package net.njay.uhc.event.match;

import net.njay.uhc.match.Match;
import org.bukkit.event.HandlerList;

public class MatchLoadEvent extends MatchEvent {

    public MatchLoadEvent(Match match) {
        super(match);
    }

}
