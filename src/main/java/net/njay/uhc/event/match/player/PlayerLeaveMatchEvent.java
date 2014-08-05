package net.njay.uhc.event.match.player;

import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

public class PlayerLeaveMatchEvent extends MatchPlayerEvent {

    public PlayerLeaveMatchEvent(Match match, UHCPlayer player) {
        super(match, player);
    }

}