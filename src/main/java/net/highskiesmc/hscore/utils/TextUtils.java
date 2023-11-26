package net.highskiesmc.hscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: January 15 2023
 * Time Created: 12:02 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class TextUtils {

    /**
     *
     * @param text String to capitalize
     * @return String with the very first letter capitalized
     * @example mary poppins --> Mary poppins
     */
    @NonNull
    public static String capitalizeFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Capitalizes every word separated by a space
     * @param str String to transform
     * @return Same string but in title case
     * @example mary poppins --> Mary Poppins
     */
    @NonNull
    public static String toTitleCase(@NonNull String str) {
        return Arrays.stream(str.split(" ")).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }

    public static Boolean isEntityType(String entity) {
        try{
            EntityType.valueOf(entity);
        } catch(IllegalArgumentException e){
            return false;
        }

        return true;
    }

    // -------------------- DEPRECATED ----------------- \\
    /**
     * @see LocationUtils
     */
    @Deprecated(forRemoval = true, since = "11/26/2023")
    public static Location deserializeLocation(@NonNull String raw) {
        final String[] split = raw.split(" ");
        return new Location(
                Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1].replace(",", ".")),
                Double.parseDouble(split[2].replace(",", ".")),
                Double.parseDouble(split[3].replace(",", ".")),
                Float.parseFloat(split[4].replace(",", ".")),
                Float.parseFloat(split[5].replace(",", "."))
        );
    }

    /**
     * @see LocationUtils
     */
    @Deprecated(forRemoval = true, since = "11/26/2023")
    public static String serializeLocation(Location location) {
        if (location == null)
            return "";

        return String.format(
                "%s %f %f %f %f %f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }
}