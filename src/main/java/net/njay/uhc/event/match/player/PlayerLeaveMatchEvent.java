package net.njay.uhc.event.match.player;

import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

public class PlayerLeaveMatchEvent extends MatchPlayerEvent {

    private LeaveCause cause;

    public PlayerLeaveMatchEvent(Match match, UHCPlayer player, LeaveCause cause) {
        super(match, player);
        this.cause = cause;
    }

    public LeaveCause getCause(){ return this.cause; }

    public enum LeaveCause{
        DEATH, QUIT;
    }
}