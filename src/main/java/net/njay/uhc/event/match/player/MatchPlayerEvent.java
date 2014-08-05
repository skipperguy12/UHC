package net.njay.uhc.event.match.player;

import net.njay.uhc.event.match.MatchEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

public class MatchPlayerEvent extends MatchEvent {

    private UHCPlayer player;

    public MatchPlayerEvent(Match match, UHCPlayer player) {
        super(match);
        this.player = player;
    }

    public UHCPlayer getPlayer(){
        return this.player;
    }
}
