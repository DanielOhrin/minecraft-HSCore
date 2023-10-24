package net.highskiesmc.hscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.UUID;

@Deprecated(forRemoval = true, since = "TextUtils.(de)serializeLocation")
public class LocationUtils {
    /**
     * Serializes a location of a block
     *
     * @param location Location to serialize
     * @return Serialized location as a String
     */
    public static String serializeLocation(@NonNull Location location) {
        String worldUUID = Objects.requireNonNull(location.getWorld()).getUID().toString();
        String x = String.valueOf(location.getBlockX());
        String y = String.valueOf(location.getBlockY());
        String z = String.valueOf(location.getBlockZ());

        return worldUUID + '/' + x + '/' + y + '/' + z;
    }

    /**
     * Deserializes a location of a block
     *
     * @param location String to deserialize
     * @return Deserialized String as a Location
     */
    public static Location deserializeLocation(@NonNull String location) {
        String[] arr = location.split("/");

        return new Location(Bukkit.getWorld(UUID.fromString(arr[0])), Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
    }
}
