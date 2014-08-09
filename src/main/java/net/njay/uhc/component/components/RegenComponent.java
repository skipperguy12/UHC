package net.njay.uhc.component.components;

import com.google.common.collect.Lists;
import net.njay.uhc.Debug;
import net.njay.uhc.component.Component;
import net.njay.uhc.component.ComponentManager;
import net.njay.uhc.event.match.MatchBeginEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RegenComponent extends Component {
    public RegenComponent(ComponentManager componentManager) {
        super(componentManager);
    }

    private static List<Short> bannedPotions = Lists.newArrayList((short)8193, (short)8197, (short)8229, (short)8289, (short)8225,
            (short)8257, (short)8261, (short)16389, (short)16385, (short)16417, (short)16421, (short)16428, (short)16449, (short)16481);

    @EventHandler
    public void onMatchBeginEvent(MatchBeginEvent event) {
        event.getMatch().getWorld().setGameRuleValue("naturalRegeneration", "false");
        Debug.log("Natural regeneration disabled for " + event.getMatch().getWorld().getName() + "! You are now playing Ultra Hard Core.", Debug.LogLevel.DEBUG);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (itemInHand == null || itemInHand.getType() != Material.POTION) return;
        if (bannedPotions.contains(itemInHand.getDurability())){
            e.getPlayer().setItemInHand(new ItemStack(Material.POTION));
            e.setCancelled(true);
        }
    }
}
