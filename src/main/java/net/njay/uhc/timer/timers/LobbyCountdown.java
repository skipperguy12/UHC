package net.njay.uhc.timer.timers;

import net.njay.uhc.UHC;
import net.njay.uhc.event.match.MatchBeginEvent;
import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;
import org.bukkit.Bukkit;

import java.util.Collection;

public class LobbyCountdown extends UHCCountdown {

    private Match match;

    public LobbyCountdown(int length, Match match) {
        super(length);
        this.match = match;
    }

    @Override
    public void onFinish() {
        Collection<UHCPlayer> players = UHC.getPlayerManager().getPlayers(match);
        System.out.println(players.size());
        if (players.size() < 1){ //TODO: SET MIN_PLAYERS TO VARIABLE
            //TODO: TELL PLAYERS THAT THERE ARE NOT ENOUGH TO START
            match.setCountdown(new LobbyCountdown(super.length, match).start());
        }else{
            MatchBeginEvent beginEvent = new MatchBeginEvent(match);
            Bukkit.getPluginManager().callEvent(beginEvent);
        }
    }
}
