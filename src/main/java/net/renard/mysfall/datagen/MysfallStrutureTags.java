package net.renard.mysfall.datagen;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.renard.mysfall.Mysfall;

public class MysfallStrutureTags {
    public static final TagKey<Structure> DARK_TEMPLE_TAG = TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(Mysfall.MOD_ID, "dark_temple"));

    public static void registerStructuresTags() {
        System.out.println("Registering Mysfall StructuresTags");
    }
}
