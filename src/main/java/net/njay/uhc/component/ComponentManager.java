package net.njay.uhc.component;

import com.google.common.collect.Maps;
import net.njay.uhc.Config;
import net.njay.uhc.Debug;
import net.njay.uhc.component.components.RegenComponent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ComponentManager {
    private HashMap<String, Component> components = Maps.newHashMap();


    public void registerComponents() {
        // this is basically just default UHC, but whatever, disable it if you want!
        components.put("regen", new RegenComponent(this));
        components.get("regen").setEnabled(Config.Component.enabledComponents.contains("regen"));


        for (Component entry : components.values())
            Debug.log(getName(entry.getClass()) + " component " + (entry.isEnabled() ? "enabled" : "disabled"), Debug.LogLevel.DEBUG);
    }

    public
    @Nullable
    Component getComponent(String component) {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(component))
                return entry.getValue();
        }
        return null;
    }

    public
    @Nullable
    String getName(Class<? extends Component> component) {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            if (entry.getValue().getClass() == component)
                return entry.getKey();
        }
        return null;
    }
}
