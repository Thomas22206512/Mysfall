package net.renard.mysfall.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.renard.mysfall.Mysfall;
import net.renard.mysfall.block.MysfallBlocks;

public class MysfallItemGroups {
    public static final ItemGroup MYSFALL_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Mysfall.MOD_ID, "mysfall_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(MysfallItems.TREASURE_BOTTLE_SCULK))
                    .displayName(Text.translatable("itemgroup.mysfall.mysfall_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(MysfallItems.TREASURE_BOTTLE_SCULK);
                        entries.add(MysfallBlocks.EPIPHANY_CAKE);
                    }).build());

    public static void registerItemGroups() {
        Mysfall.LOGGER.info("Registering Mysfall ItemGroups");
    }
}
