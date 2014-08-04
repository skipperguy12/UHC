package net.njay.uhc.match;

import com.google.common.collect.Maps;
import net.njay.uhc.UHC;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joda.time.Instant;


/**
 * Class to represent a Match instance
 */
public class Match {
    protected final int id;
    protected final World world;
    protected MatchState state;
    protected java.util.Map<MatchState, Instant> matchStateInstantMap = Maps.newHashMap();

    public Match(int id, World world) {
        setState(MatchState.IDLE);
        this.id = id;
        this.world = world;
    }

    public int getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }


    public MatchState getState() {
        return state;
    }

    public void setState(MatchState newState) {
        matchStateInstantMap.put(newState, Instant.now());
        MatchState oldState = state;
        this.state = newState;
    }

    /**
     * Adds the provided player to the match, putting him on the default team
     *
     * @param p the player to add
     */
    public void addPlayer(Player p) {
        UHCPlayer player = UHC.getPlayerManager().getPlayer(p);
        player.setMatch(this);

        // TODO: PMHTeam defaultTeam = this.getMap().getModules().getModule(PMHTeamModule.class).getDefaultTeam();
        // TODO: player.setTeam(defaultTeam);
    }

}
