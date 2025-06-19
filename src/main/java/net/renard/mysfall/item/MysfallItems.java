package net.renard.mysfall.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.renard.mysfall.Mysfall;

import java.util.function.Function;

public class MysfallItems {

    public static final Item TREASURE_BOTTLE_SCULK = registerItem("treasure_bottle_with_sculk", settings -> new TreasureBottleWithSculk(settings.maxDamage(1).fireproof()));

    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(Mysfall.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mysfall.MOD_ID,name)))));
    }

    public static void registerMysfallItems() {
        Mysfall.LOGGER.info("Registering Mysfall Items");
    }
}
