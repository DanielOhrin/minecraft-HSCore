package net.highskiesmc.hscore.utils;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {
    public static String toTitleCase(@NonNull String str) {
        return Arrays.stream(str.split(" ")).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }
}
