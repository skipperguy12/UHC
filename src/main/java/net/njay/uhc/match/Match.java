package net.njay.uhc.match;

import com.google.common.collect.Maps;
import com.sk89q.minecraft.util.commands.ChatColor;
import net.njay.uhc.UHC;
import net.njay.uhc.player.UHCPlayer;
import net.njay.uhc.timer.UHCCountdown;
import net.njay.uhc.timer.timers.LobbyCountdown;
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

    protected UHCCountdown countdown;

    public Match(int id, World world) {
        setState(MatchState.IDLE);
        this.id = id;
        this.world = world;
       setCountdown(new LobbyCountdown(25, this).start()); //TODO: SET TIMER LENGTH IN CONFIG
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

    public void setCountdown(UHCCountdown countdown){
        this.countdown = countdown;
    }

    public String getStatusMessage(){
        switch (state){
            case IDLE:
                return ChatColor.BLUE + "The match is empty! Be the first to join!";
            case STARTING:
                return ChatColor.GOLD + "The match will begin in " + ChatColor.DARK_GREEN + (countdown != null ? countdown.getTimeLeft() : 0) + ChatColor.GOLD + " seconds!";
            case RUNNING:
                return ChatColor.RED + "The match is already running. You cannot join.";
            case FINISHED:
                return ChatColor.RED + "The match is finishing up and will become available soon.";
            default:
                return "An error occurred";
        }
    }

}
