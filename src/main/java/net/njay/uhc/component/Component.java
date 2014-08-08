package net.njay.uhc.component;

import net.njay.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class Component implements Listener {
    protected boolean enabled;
    protected ComponentManager componentManager;

    public Component(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public void enable() {
        enabled = false;
        Bukkit.getPluginManager().registerEvents(this, UHC.getInstance());
    }

    public void disable() {
        enabled = false;
        HandlerList.unregisterAll(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected void setEnabled(boolean enabled) {
        if (this.enabled == enabled)
            throw new IllegalStateException("State already set!");

        this.enabled = enabled;

        if (enabled)
            enable();
        else
            disable();
    }
}
