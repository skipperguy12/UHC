package net.njay.uhc.timer.timers;

import net.njay.uhc.UHC;
import net.njay.uhc.match.Match;
import net.njay.uhc.match.MatchState;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;

import java.util.Collection;

public class LobbyCountdown extends UHCCountdown {

    public LobbyCountdown(Match match) {
        super(match);
    }

    @Override
    public void tick(int seconds) {
    }

    @Override
    public void end() {
        Collection<UHCPlayer> players = UHC.getPlayerManager().getPlayers(match);
        if (players.size() < 1) { //TODO: SET MIN_PLAYERS TO VARIABLE
            //TODO: TELL PLAYERS THAT THERE ARE NOT ENOUGH TO START
            match.getCountdownManager().start(new LobbyCountdown(match), 10); //idk what time to restart it with, using 10 for now
        } else {
            match.getCountdownManager().start(new StartingCountdown(match), 30);
            match.setState(MatchState.STARTING);
        }
    }
}