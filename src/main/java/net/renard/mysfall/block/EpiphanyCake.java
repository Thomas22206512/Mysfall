package net.renard.mysfall.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.Random;

public class EpiphanyCake extends Block {
    public static final MapCodec<net.minecraft.block.CakeBlock> CODEC = createCodec(net.minecraft.block.CakeBlock::new);
    public static final IntProperty BITES = Properties.BITES;
    public static final int[] L = {1,0,0,0,0,0};
    public static final Random RAND = new Random();
    protected static final VoxelShape[] BITES_TO_SHAPE = new VoxelShape[] {
            Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(3.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(5.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(7.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(9.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(11.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            Block.createCuboidShape(13.0, 0.0, 1.0, 15.0, 4.0, 15.0)
    };

    @Override
    public MapCodec<net.minecraft.block.CakeBlock> getCodec() {return CODEC;}

    public EpiphanyCake(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(BITES, 0));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BITES_TO_SHAPE[state.get(BITES)];
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item = stack.getItem();
        if (stack.isIn(ItemTags.CANDLES) && (Integer)state.get(BITES) == 0 && Block.getBlockFromItem(item) instanceof CandleBlock candleBlock) {
            stack.decrementUnlessCreative(1, player);
            world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, CandleCakeBlock.getCandleCakeFromCandle(candleBlock));
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            if (tryEat(world,world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return tryEat(world,world, pos, state, player);
    }

    protected static ActionResult tryEat(World world1, WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(2, 0.1F);
            int i = (Integer)state.get(BITES);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            if (i < 6) {
                if (L[i] == 1) {
                    world.spawnEntity(new ItemEntity(world1,pos.getX(),pos.getY() + 0.5,pos.getZ(),new ItemStack(Items.GOLD_NUGGET)));
                }
                world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
            } else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return ActionResult.SUCCESS;
        }
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.math.random.Random random) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (int i = 0; i < 6; i++) {
            int randomIndexToSwap = RAND.nextInt(L.length);
            int temp = L[randomIndexToSwap];
            L[randomIndexToSwap] = L[i];
            L[i] = temp;
        }
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return getComparatorOutput((Integer)state.get(BITES));
    }

    public static int getComparatorOutput(int bites) {
        return (7 - bites) * 2;
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
