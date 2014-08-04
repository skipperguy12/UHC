package net.njay.uhc.event.match;

import net.njay.uhc.event.UHCEvent;
import net.njay.uhc.match.Match;

public abstract class MatchEvent extends UHCEvent {
    protected Match match;

    public MatchEvent(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }
}
