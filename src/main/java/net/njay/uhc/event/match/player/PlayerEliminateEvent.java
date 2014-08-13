package net.njay.uhc.event.match.player;

import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

public class PlayerEliminateEvent extends MatchPlayerEvent {

    public PlayerEliminateEvent(Match match, UHCPlayer player) {
        super(match, player);
    }

}
