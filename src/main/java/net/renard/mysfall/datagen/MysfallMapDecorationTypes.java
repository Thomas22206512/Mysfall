package net.renard.mysfall.datagen;

import net.minecraft.block.MapColor;
import net.minecraft.item.map.MapDecorationType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.renard.mysfall.Mysfall;

public class MysfallMapDecorationTypes {
    public static final RegistryEntry<MapDecorationType> DARK_TEMPLE_DECORATION =
            register("dark_temple", "dark_temple", true, MapColor.LIGHT_GRAY.color, false, true);

    private static RegistryEntry<MapDecorationType> register(
            String id, String assetId, boolean showOnItemFrame, int mapColor, boolean trackCount, boolean explorationMapElement
    ) {
        RegistryKey<MapDecorationType> registryKey = RegistryKey.of(RegistryKeys.MAP_DECORATION_TYPE, Identifier.of(Mysfall.MOD_ID, id));
        MapDecorationType mapDecorationType = new MapDecorationType(Identifier.of(Mysfall.MOD_ID, assetId), showOnItemFrame, mapColor, explorationMapElement, trackCount);
        return Registry.registerReference(Registries.MAP_DECORATION_TYPE, registryKey, mapDecorationType);
    }

    public static void registerMapDecorationTypes() {
        System.out.println("Registering Mysfall MapDecorationTypes");
    }
}
