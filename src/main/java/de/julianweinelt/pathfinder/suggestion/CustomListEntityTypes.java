package de.julianweinelt.pathfinder.suggestion;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class CustomListEntityTypes implements CustomListProvider{
    @Override
    public List<String> onSuggest() {
        List<String> entityTypes = new ArrayList<>();
        for (EntityEntry entityType : ForgeRegistries.ENTITIES) {
            entityTypes.add(entityType.getName());
            entityTypes.add(entityType.getRegistryName().getResourceDomain() + ":" + entityType.getName());
        }
        return entityTypes;
    }
}