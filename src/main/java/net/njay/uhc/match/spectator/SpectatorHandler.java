package net.njay.uhc.match.spectator;

import com.google.common.collect.Lists;
import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;

import java.util.List;

public class SpectatorHandler {

    private Match match;
    private List<UHCPlayer> spectators;

    public SpectatorHandler(Match match){
        this.match = match;
        spectators = Lists.newArrayList();
    }

    public Match getMatch(){ return this.match; }
    public List<UHCPlayer> getSpectators(){ return this.spectators; }

    public void addSpectator(UHCPlayer player){
        spectators.add(player);
        player.getBukkit().setCollidesWithEntities(false);
        for (UHCPlayer matchPlayer : match.getPlayers())
            matchPlayer.getBukkit().hidePlayer(player.getBukkit());
    }

    public void removeSpectator(UHCPlayer player){
        spectators.remove(player);
        player.getBukkit().setCollidesWithEntities(true);
        for (UHCPlayer matchPlayer : match.getPlayers())
            matchPlayer.getBukkit().showPlayer(player.getBukkit());
    }

}
