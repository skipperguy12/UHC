package net.njay.uhc.match;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import net.njay.uhc.UHC;
import net.njay.uhc.component.ComponentManager;
import net.njay.uhc.event.match.MatchLoadEvent;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

/**
 * Class to manage the multiple match instances
 */
public class MatchManager {
    private List<Match> matches = Lists.newArrayList();
    private ComponentManager componentManager;

    private static int id = -1;

    public MatchManager(int matchCount) {
        componentManager = new ComponentManager();
        componentManager.registerComponents();
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
        int currentId;
        if (old == null)
            currentId = ++id;
        else
            currentId = old.getId();
        WorldCreator wc = new WorldCreator("match-" + currentId);
        World newWorld = wc.createWorld();
        newWorld.setAutoSave(false);
        Match newMatch = new Match(currentId, newWorld);

        newMatch.getWorld().setGameRuleValue("naturalRegeneration", "false");

        MatchLoadEvent matchLoadEvent = new MatchLoadEvent(newMatch);
        Bukkit.getPluginManager().callEvent(matchLoadEvent);

        if (old != null) {
            matches.set(matches.indexOf(old), newMatch);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(old.getWorld().getName())) continue;
                UHCPlayer uhcPlayer = UHC.getPlayerManager().getPlayer(player);
                uhcPlayer.setMatch(null);
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                UHC.getMenu().show(player);
            }
            unloadMatch(old);
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
        Bukkit.unloadWorld(match.getWorld(), false);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Unloaded " + match.getWorld().getName());
    }

}