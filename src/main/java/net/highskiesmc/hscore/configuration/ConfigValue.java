package net.highskiesmc.hscore.configuration;

public class ConfigValue<T> {
    private final T value;

    public ConfigValue(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
