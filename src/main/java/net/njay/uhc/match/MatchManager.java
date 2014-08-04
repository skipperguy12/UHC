package net.njay.uhc.match;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.MatchLoadEvent;
import net.njay.uhc.menu.join.JoinMenu;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class to manage the multiple match instances
 */
public class MatchManager {
    private List<Match> matches = Lists.newArrayList();

    private static int id = -1;

    public MatchManager(int matchCount) {
        if (matchCount <= 0)
            matchCount = 1;

        for (int i = 0; i < matchCount; i++) {
            matches.add(cycle(null));
        }
    }

    public synchronized List<Match> getMatches() {
        return ImmutableList.copyOf(matches);
    }

    public synchronized Match cycle(@Nullable Match old) {
        WorldCreator wc = new WorldCreator("match-" + ++id);

        Match newMatch = new Match(id, wc.createWorld());

        MatchLoadEvent matchLoadEvent = new MatchLoadEvent(newMatch);
        Bukkit.getPluginManager().callEvent(matchLoadEvent);

        if (old != null)
            matches.set(matches.indexOf(old), newMatch);

        if (old != null) {
            unloadMatch(old);
            //movePlayers(old, newMatch);
            for (UHCPlayer player : UHC.getPlayerManager().getPlayers(old)) {
                player.getBukkit().teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                UHC.getMenu().show(player.getBukkit());
            }
        }

        return newMatch;
    }

    /**
     * Unloads the match, removing all players and deleting the world files
     *
     * @param match the match to unload
     */
    public void unloadMatch(Match match) {
        File dir = match.getWorld().getWorldFolder();
        Bukkit.unloadWorld(match.getWorld(), true);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Unloaded " + match.getWorld().getName());
    }


}
