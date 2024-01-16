package net.highskiesmc.hscore.highskies;

import net.highskiesmc.hscore.configuration.Config;
import org.bukkit.event.Listener;

public class HSListener implements Listener {
    protected final HSPlugin main;
    protected final Config config;

    public HSListener(HSPlugin main) {
        this.main = main;
        this.config = main.getConfigs();
    }
}
