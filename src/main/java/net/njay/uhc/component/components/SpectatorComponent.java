package net.njay.uhc.component.components;

import net.njay.uhc.UHC;
import net.njay.uhc.component.Component;
import net.njay.uhc.component.ComponentManager;
import net.njay.uhc.player.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SpectatorComponent extends Component {
    public SpectatorComponent(ComponentManager componentManager) {
        super(componentManager);
    }

    private boolean isSpectator(Player player) {
        if (UHC.getPlayerManager().getPlayer(player).getMatch() == null)
            return true; // lobby, stop everything in lobby.
        return UHC.getPlayerManager().getPlayer(player).getMatch().isSpectator(UHC.getPlayerManager().getPlayer(player));
    }

    private boolean isSpectator(UHCPlayer player) {
        return player.getMatch().isSpectator(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Player player = null;
        if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        } else return;
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        Player player = null;
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else return;
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTarget(EntityTargetEvent event) {
        Player player = null;
        if (event.getTarget() instanceof Player) {
            player = (Player) event.getTarget();
        } else return;
        if (isSpectator(player)) {
            event.setCancelled(true);
        }
    }
}
