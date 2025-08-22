package de.julianweinelt.pathfinder.util;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;

import java.util.Collection;

public class StringUtil {
    public static <T extends Collection<? super String>> @NotNull T copyPartialMatches(@NotNull String token, @NotNull Iterable<String> originals, @NotNull T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Preconditions.checkArgument(token != null, "Search token cannot be null");
        Preconditions.checkArgument(collection != null, "Collection cannot be null");
        Preconditions.checkArgument(originals != null, "Originals cannot be null");

        for(String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }

        return collection;
    }

    public static boolean startsWithIgnoreCase(@NotNull String string, @NotNull String prefix) throws IllegalArgumentException, NullPointerException {
        Preconditions.checkArgument(string != null, "Cannot check a null string for a match");
        return string.length() < prefix.length() ? false : string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
