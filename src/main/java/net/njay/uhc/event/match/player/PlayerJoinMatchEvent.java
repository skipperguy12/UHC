package net.njay.uhc.event.match.player;

import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

public class PlayerJoinMatchEvent extends MatchPlayerEvent {

    public PlayerJoinMatchEvent(Match match, UHCPlayer player) {
        super(match, player);
    }

}
