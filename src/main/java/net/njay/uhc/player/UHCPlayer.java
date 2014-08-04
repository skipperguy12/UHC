package net.njay.uhc.player;

import net.njay.player.MenuPlayer;
import net.njay.uhc.match.Match;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Class to wrap org.bukkit Player
 */
public class UHCPlayer extends MenuPlayer {
    private
    @Nullable
    Match match;

    public UHCPlayer(Player bukkit) {
        super(bukkit);
    }

    @Nullable
    public Match getMatch() {
        return match;
    }

    public void setMatch(@Nullable Match match) {
        this.match = match;
    }
}
