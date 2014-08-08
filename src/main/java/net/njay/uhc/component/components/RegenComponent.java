package net.njay.uhc.component.components;

import net.njay.uhc.Debug;
import net.njay.uhc.component.Component;
import net.njay.uhc.component.ComponentManager;
import net.njay.uhc.event.match.MatchBeginEvent;
import org.bukkit.event.EventHandler;

public class RegenComponent extends Component {
    public RegenComponent(ComponentManager componentManager) {
        super(componentManager);
    }

    @EventHandler
    public void onMatchBeginEvent(MatchBeginEvent event) {
        event.getMatch().getWorld().setGameRuleValue("naturalRegeneration", "false");
        Debug.log("Natural regeneration disabled for " + event.getMatch().getWorld().getName() + "! You are now playing Ultra Hard Core.", Debug.LogLevel.DEBUG);
    }

}
