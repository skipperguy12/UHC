package net.njay.uhc.match.spectator;

import com.google.common.collect.Lists;
import net.njay.uhc.match.Match;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.util.location.SportBukkitUtil;

import java.util.List;

public class SpectatorHandler {

    private Match match;
    private List<UHCPlayer> spectators;

    public SpectatorHandler(Match match) {
        this.match = match;
        spectators = Lists.newArrayList();
    }

    public Match getMatch() {
        return this.match;
    }

    public List<UHCPlayer> getSpectators() {
        return this.spectators;
    }

    public void addSpectator(UHCPlayer player) {
        spectators.add(player);
        if (SportBukkitUtil.hasSportBukkitApi()) {
            SportBukkitUtil.setCollidesWithEntities(player.getBukkit(), false);
            SportBukkitUtil.setAffectsSpawning(player.getBukkit(), false);
        }
        player.getBukkit().setAllowFlight(true);
        hideAll();
    }

    public void removeSpectator(UHCPlayer player) {
        spectators.remove(player);
        if (SportBukkitUtil.hasSportBukkitApi()) {
            SportBukkitUtil.setCollidesWithEntities(player.getBukkit(), true);
            SportBukkitUtil.setAffectsSpawning(player.getBukkit(), true);
        }
        player.getBukkit().setAllowFlight(false);
        hideAll();
    }

    public void onAddToMatch(UHCPlayer player) {
        player.getBukkit().setAllowFlight(false);
        hideAll();
    }

    public void onRemoveFromMatch(UHCPlayer player) {
        for (UHCPlayer spectator : spectators)
            player.getBukkit().showPlayer(spectator.getBukkit());
    }

    private void hideAll() {
        for (UHCPlayer matchPlayer : match.getPlayers())
            for (UHCPlayer spectator : spectators)
                matchPlayer.getBukkit().hidePlayer(spectator.getBukkit());
    }
}