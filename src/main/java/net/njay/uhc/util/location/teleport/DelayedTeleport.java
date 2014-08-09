package net.njay.uhc.util.location.teleport;

import net.njay.uhc.util.location.LocationUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedTeleport extends BukkitRunnable{

    private Player player;
    private Location to;

    public DelayedTeleport(Player player, Location to){
        this.player = player;
        this.to = to;
    }

    @Override
    public void run() {
        player.teleport(LocationUtil.placeOnGround(to));
    }

}
