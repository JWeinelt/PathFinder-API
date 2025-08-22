package de.julianweinelt.pathfinder.suggestion;

public interface CustomListProvider {
    /**
     * Provides a list of suggestions based on the given input.
     *
     * @param input The input string to base suggestions on.
     * @return An array of suggestions.
     */
    String[] provideSuggestions(String input);
}
