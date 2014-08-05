package net.njay.uhc.match;

import com.google.common.collect.Maps;
import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.UHC;
import net.njay.uhc.event.match.player.PlayerJoinMatchEvent;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;
import net.njay.uhc.timer.UHCCountdownManager;
import net.njay.uhc.timer.timers.LobbyCountdown;
import net.njay.uhc.timer.timers.StartingCountdown;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joda.time.Instant;

import java.util.Collection;
import java.util.Map;


/**
 * Class to represent a Match instance
 */
public class Match {

    protected final int id;
    protected final World world;
    protected MatchState state;
    protected Map<MatchState, Instant> matchStateInstantMap = Maps.newHashMap();

    private UHCCountdownManager countdownManager;

    public Match(int id, World world) {
        this.setState(MatchState.IDLE);
        this.id = id;
        this.world = world;

        this.countdownManager = new UHCCountdownManager();
        this.countdownManager.start(new LobbyCountdown(this), 30);
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

    public UHCCountdownManager getCountdownManager() {
        return this.countdownManager;
    }

    public void setState(MatchState newState) {
        matchStateInstantMap.put(newState, Instant.now());
        MatchState oldState = state;
        this.state = newState;
    }

    /**
     * Adds the provided player to the match, putting him on the default team
     *
     * @param player the player to add
     */
    public void addPlayer(UHCPlayer player) {
        player.setMatch(this);

        PlayerJoinMatchEvent event = new PlayerJoinMatchEvent(this, player);
        event.call();
        // TODO: PMHTeam defaultTeam = this.getMap().getModules().getModule(PMHTeamModule.class).getDefaultTeam();
        // TODO: player.setTeam(defaultTeam);
    }

    public Collection<UHCPlayer> getPlayers() {
        return UHC.getPlayerManager().getPlayers(this);
    }

    public void broadcast(String message) {
        for (UHCPlayer player : this.getPlayers())
            player.getBukkit().sendMessage(message);
    }

    public String getStatusMessage() {
        switch (state) {
            case IDLE:
                return ChatColor.BLUE + "The match is empty! Be the first to join!";
            case STARTING:
                // should be safe to assume its a startingcountdown because there should be one running when matchstate == starting
                UHCCountdown countdown = this.getCountdownManager().getCurrentCountdown();
                int seconds = countdown == null ? 0 : countdown.getSeconds();
                return ChatColor.GOLD + "The match will begin in " + ChatColor.DARK_GREEN + seconds + ChatColor.GOLD + " second" + (seconds == 1 ? "!" : "s!");
            case RUNNING:
                return ChatColor.RED + "The match is already running. You cannot join.";
            case FINISHED:
                return ChatColor.RED + "The match is finishing up and will become available soon.";
            default:
                return "An error occurred";
        }
    }

}
