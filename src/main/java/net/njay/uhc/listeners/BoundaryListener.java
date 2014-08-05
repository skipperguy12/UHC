package net.njay.uhc.listeners;

import net.njay.uhc.Config;
import net.njay.uhc.UHC;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BoundaryListener implements Listener{

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        UHCPlayer player = UHC.getPlayerManager().getPlayer(e.getPlayer());
        if (player.getMatch() == null) return;
        if (e.getTo().getX() > Config.Match.matchRadius ||
                e.getTo().getX() < -Config.Match.matchRadius ||
                e.getTo().getZ() > Config.Match.matchRadius ||
                e.getTo().getZ() < -Config.Match.matchRadius) {
            player.getBukkit().sendMessage(ChatColor.RED + "Please stay within the match area!");
            e.setCancelled(true);
        }
    }

}
