package net.highskiesmc.hscore.commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Supplier;

public class CommandConfig<T> {
    public enum Type {
        USAGE_TEMPLATE,
        USAGE,
        NO_PERMISSION,
        OTHER
    }

    @Nullable
    private final String identifier;
    private final CommandConfig.Type type;
    @NonNull
    private final String path;
    @Nullable
    private final Character colorCode;
    @NonNull
    private final Supplier<@NonNull FileConfiguration> configGenerator;
    @NonNull
    private FileConfiguration config;
    @NonNull
    private final T def;
    @Nullable
    private T value;
    public CommandConfig(
            CommandConfig.Type type,
            @NonNull Supplier<FileConfiguration> configGenerator,
            @NonNull String path,
            @NonNull T def,
            Character colorCode
    ) {
        this.identifier = null;

        this.type = type;
        this.configGenerator = configGenerator;
        this.path = path;
        this.def = def;
        this.colorCode = colorCode;

        reload();
    }

    public CommandConfig(
            CommandConfig.Type type,
            @NonNull Supplier<FileConfiguration> configGenerator,
            @NonNull String path,
            @NonNull T def,
            Character colorCode,
            @NonNull String identifier
    ) {
        this.identifier = identifier;

        this.type = type;
        this.configGenerator = configGenerator;
        this.path = path;
        this.def = def;
        this.colorCode = colorCode;

        reload();
    }

    /*
    Reloads the value into memory
     */
    private void reload() {
        this.value = def;
        this.config = configGenerator.get();

        if (this.value instanceof String) {
            this.value = (T) ChatColor.translateAlternateColorCodes(colorCode, config.getString(path, (String) def));
            return;
        }

        this.value = (T)this.config.getObject(this.path, this.value.getClass());
    }

    /**
     * @return Cached value of the option
     */
    @NonNull
    public T getValue() {
        return (this.value == null ? this.def : this.value);
    }

    /**
     *
     * @return {@link CommandConfig.Type} of the Config option
     */
    public CommandConfig.Type getType() {
        return this.type;
    }

    /**
     *
     * @return Identifying String of the object
     */
    @Nullable
    public String getIdentifier() {
        return this.identifier;
    }
}