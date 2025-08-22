package de.julianweinelt.pathfinder.suggestion;

import java.util.List;

public interface CustomListProvider {
    List<String> onSuggest();
}
