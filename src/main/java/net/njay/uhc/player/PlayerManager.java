package net.njay.uhc.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.njay.player.MenuPlayerManager;
import net.njay.uhc.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Class to manage all players
 */
public class PlayerManager extends MenuPlayerManager {
    // Mapping of Bukkit players to PMHPlayers
    private HashMap<Player, UHCPlayer> players = Maps.newHashMap();

    /**
     * Gets the PMHPlayer by player name
     *
     * @param p The players name
     * @return The PMHPlayer
     */
    public UHCPlayer getPlayer(String p) {
        return getPlayer(Bukkit.getPlayerExact(p));
    }

    /**
     * Gets the PMHPlayer from player
     *
     * @param p The player
     * @return The PMHPlayer from Player
     */
    public UHCPlayer getPlayer(Player p) {
        if (players.containsKey(p)) return players.get(p);
        UHCPlayer pl = new UHCPlayer(p);
        players.put(p, pl);
        return pl;
    }

    /**
     * Removes the player from the list from player
     *
     * @param p The player
     */
    public void removePlayer(Player p) {
        players.remove(p);
    }

    /**
     * Returns a list of PMHPlayers in the specified match
     *
     * @param m The Match to fetch the list from
     * @return The list of PMHPlayers
     */
    public Collection<UHCPlayer> getPlayers(Match m) {
        List<UHCPlayer> returning = Lists.newArrayList();
        for (UHCPlayer p : players.values())
            if (p.getMatch() != null && p.getMatch().equals(m)) returning.add(p);
        return returning;
    }

    /**
     * Returns a list of participating PMHPlayers in the specified match
     *
     * @param m The Match to fetch the list from
     * @return The list of PMHPlayers
     */
    public Collection<UHCPlayer> getParticipatingPlayers(Match m) {
        List<UHCPlayer> returning = Lists.newArrayList();
        for (UHCPlayer p : players.values())
            if (p.getMatch() != null && p.getMatch().equals(m) && !(p.getMatch().isSpectator(p))) returning.add(p);
        return returning;
    }
}
