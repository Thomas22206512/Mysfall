package net.renard.mysfall.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.renard.mysfall.Mysfall;

import java.util.function.Function;

public class MysfallBlocks {
    public static final Block EPIPHANY_CAKE = registerBlock("epiphany_cake",
            properties -> new  EpiphanyCake(properties.strength(4f).requiresTool()));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mysfall.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(Mysfall.MOD_ID, name), toRegister);
    }

    private static Block registerBlockWithoutItem(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mysfall.MOD_ID, name))));

        return Registry.register(Registries.BLOCK, Identifier.of(Mysfall.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Mysfall.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mysfall.MOD_ID,name)))));
    }

    public static void registerModBlocks(){
        Mysfall.LOGGER.info("Registering Mysfall Blocks");
    }

}
