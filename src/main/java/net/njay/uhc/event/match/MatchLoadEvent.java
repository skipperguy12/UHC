package net.njay.uhc.event.match;

import net.njay.uhc.match.Match;
import org.bukkit.event.HandlerList;

public class MatchLoadEvent extends MatchEvent {
    // Bukkit Handlers
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public MatchLoadEvent(Match match) {
        super(match);
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
