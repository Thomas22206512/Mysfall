package net.renard.mysfall.item.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapColorComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;
import net.renard.mysfall.datagen.MysfallMapDecorationTypes;

public class StructureMapGenerator {
    public static ItemStack createMapToStructure(ServerWorld world, BlockPos origin, TagKey<Structure> structureKey, Text mapName) {

        BlockPos target = world.locateStructure(structureKey, origin, 100, false);

        if (target == null) {
            return ItemStack.EMPTY;
        }

        Byte zoom = 1;

        ItemStack stack = FilledMapItem.createMap(world,target.getX(),target.getZ(), zoom, true, true);

        FilledMapItem.fillExplorationMap(world,stack);

        MapState.addDecorationsNbt(stack, target, "+", MysfallMapDecorationTypes.DARK_TEMPLE_DECORATION);

        Text name = mapName.copy()
                .styled(style -> style.withColor(Formatting.DARK_PURPLE).withItalic(false));

        stack.set(DataComponentTypes.CUSTOM_NAME, name);

        stack.set(DataComponentTypes.MAP_COLOR,new MapColorComponent(0x0B132B));

        return stack;
    }
}