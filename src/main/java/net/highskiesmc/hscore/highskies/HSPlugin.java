package net.highskiesmc.hscore.highskies;

import net.highskiesmc.hscore.configuration.Config;
import net.highskiesmc.hscore.inventory.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class HSPlugin extends JavaPlugin {
    protected final Config config;

    public HSPlugin() {
        config = new Config();
    }

    @Override
    public final void onEnable() {
        if (isUsingInventories()) {
            Bukkit.getPluginManager().registerEvents(new InventoryHandler(), this);
        }

        enable();
    }

    @Override
    public final void onDisable() {
        disable();
    }

    public final void onReload() {
        config.reload();

        reload();
    }

    public abstract void enable();

    public abstract void disable();

    public abstract void reload();

    protected abstract boolean isUsingInventories();

    @Override
    @Deprecated(forRemoval = true)
    /**
     * Use static config class in
     */
    public final FileConfiguration getConfig() {
        return null;
    }

    protected void register(Listener handler) {
        Bukkit.getPluginManager().registerEvents(handler, this);
    }

    public Config getConfigs() {
        return this.config;
    }
}
