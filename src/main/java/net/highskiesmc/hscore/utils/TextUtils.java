package net.highskiesmc.hscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: January 15 2023
 * Time Created: 12:02 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class TextUtils {

    public static String capitalizeFirst(String text) {
        String result = text.substring(0, 1).toUpperCase() + text.substring(1);

        return result;
    }

    public static String serializeLocation(final Location location) {
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

    public static Boolean isEntityType(String entity) {
        try{
            EntityType.valueOf(entity);
        } catch(IllegalArgumentException e){
            return false;
        }

        return true;
    }

    public static Location deserializeLocation(final String raw) {
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
}