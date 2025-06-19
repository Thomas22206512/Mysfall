package net.renard.mysfall.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.renard.mysfall.datagen.MysfallStrutureTags;
import net.renard.mysfall.item.util.StructureMapGenerator;

public class TreasureBottleWithSculk extends Item {

    public TreasureBottleWithSculk(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            ItemStack map = StructureMapGenerator.createMapToStructure(
                    serverWorld,
                    player.getBlockPos(),
                    MysfallStrutureTags.DARK_TEMPLE_TAG,
                    Text.literal("Dark Temple Map")
            );

            if (!map.isEmpty()) {
                player.setStackInHand(hand, map);
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_FREEZE, SoundCategory.BLOCKS, 4.0F, 0.6F + 1 * 0.4F);

            } else {
                player.sendMessage(Text.literal("No structure found nearby."),true);
            }
        }

        return ActionResult.SUCCESS;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        ConsumableComponent consumableComponent = stack.get(DataComponentTypes.CONSUMABLE);
        return consumableComponent != null ? consumableComponent.finishConsumption(world, user, stack) : stack;
    }
}
