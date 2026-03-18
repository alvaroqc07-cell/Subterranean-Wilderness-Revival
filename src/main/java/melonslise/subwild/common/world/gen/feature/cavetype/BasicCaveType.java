package melonslise.subwild.common.world.gen.feature.cavetype;

import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

import melonslise.subwild.common.capability.INoise;
import melonslise.subwild.common.config.SubWildConfig;
import melonslise.subwild.common.init.SubWildBlocks;
import melonslise.subwild.common.init.SubWildLookups;
import melonslise.subwild.common.init.SubWildProperties;
import melonslise.subwild.common.util.SubWildUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.common.Tags;

public class BasicCaveType extends CaveType
{
	public static final Supplier<Block>[] FOXFIRE = new Supplier[] { SubWildBlocks.SHORT_FOXFIRE, SubWildBlocks.LONG_FOXFIRE };

	public ImmutableSet<Direction> dirs = ImmutableSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
	public float floorCh = 2f, ceilCh = 3f;
	public Supplier<Block>
		defSpel = SubWildBlocks.STONE_SPELEOTHEM,
		defStairs = () -> Blocks.STONE_STAIRS,
		defSlab = () -> Blocks.STONE_SLAB;

	public BasicCaveType(ResourceLocation name)
	{
		super(name);
	}

	public BasicCaveType(String domain, String path)
	{
		super(domain, path);
	}

	@Override
	public void genFloor(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand) {}

	@Override
	public void genFloorExtra(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 1)
		{
			BlockState support = world.getBlockState(pos.below());
			// 1.20.1: Material.WOOD → BlockTags.LOGS || BlockTags.PLANKS
			if(support.is(BlockTags.LOGS) || support.is(BlockTags.PLANKS))
			{
				if(SubWildConfig.GENERATE_FOXFIRES.get() && this.getNoise(noise, pos, 0.1d) > 0.6d)
					this.genBlock(world, pos, FOXFIRE[rand.nextInt(FOXFIRE.length)].get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(SubWildProperties.GLOWING, true));
			}
			else if(depth > 0d && !support.is(BlockTags.DIRT) && this.getSpelChance(depth, this.floorCh, rand))
				this.genRandSpel(world, pos, SubWildLookups.SPELEOS.getOrDefault(support.getBlock(), this.defSpel.get()).defaultBlockState().setValue(SubWildProperties.VERTICAL_FACING, Direction.UP), depth, rand);
		}
	}

	@Override
	public void genCeil(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand) {}

	@Override
	public void genCeilExtra(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 1)
		{
			BlockState support = world.getBlockState(pos.above());
			if(support.is(BlockTags.LOGS) || support.is(BlockTags.PLANKS))
			{
				if(SubWildConfig.GENERATE_FOXFIRES.get() && this.getNoise(noise, pos, 0.1d) > 0.6d)
					this.genBlock(world, pos, FOXFIRE[rand.nextInt(FOXFIRE.length)].get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN).setValue(SubWildProperties.GLOWING, true));
			}
			else if(!support.is(BlockTags.DIRT))
			{
				if(depth > 0d && this.getSpelChance(depth, this.ceilCh, rand))
					this.genRandSpel(world, pos, SubWildLookups.SPELEOS.getOrDefault(support.getBlock(), this.defSpel.get()).defaultBlockState().setValue(SubWildProperties.VERTICAL_FACING, Direction.DOWN), depth, rand);
			}
			else if(this.getNoise(noise, pos, 0.125d) > 0.4d)
				this.genRoots(world, noise, pos);
		}
	}

	@Override
	public void genWall(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand) {}

	@Override
	public void genWallExtra(WorldGenLevel world, INoise noise, BlockPos pos, Direction wallDir, float depth, int pass, RandomSource rand)
	{
		if(pass == 1)
		{
			BlockState wallBlock = world.getBlockState(pos.relative(wallDir));
			if(wallBlock.is(BlockTags.LOGS) || wallBlock.is(BlockTags.PLANKS))
			{
				if(SubWildConfig.GENERATE_FOXFIRES.get() && this.getNoise(noise, pos, 0.1d) > 0.6d)
					this.genBlock(world, pos, FOXFIRE[rand.nextInt(FOXFIRE.length)].get().defaultBlockState().setValue(BlockStateProperties.FACING, wallDir.getOpposite()).setValue(SubWildProperties.GLOWING, true));
			}
			else
				this.genSlope(world, pos, wallDir, rand);
		}
	}

	@Override
	public void genFill(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand) {}

	@Override
	public boolean canGenSide(WorldGenLevel world, BlockPos pos, BlockState state, float depth, int pass, Direction dir)
	{
		return pass == 0 && (state.is(Tags.Blocks.STONE) || state.is(BlockTags.DIRT) || state.is(Tags.Blocks.GRAVEL)) || state.is(Tags.Blocks.ORES);
	}

	@Override
	public boolean canGenExtra(WorldGenLevel world, BlockPos pos, BlockState state, BlockPos sidePos, BlockState sideState, float depth, int pass, Direction dir)
	{
		// 1.20.1: Material.WOOD → tags
		return pass == 1 && state.isAir() && (sideState.is(Tags.Blocks.ORES) || sideState.is(BlockTags.LOGS) || sideState.is(BlockTags.PLANKS) || this.isNatural(world, sidePos, sideState));
	}

	@Override
	public boolean canGenFill(WorldGenLevel world, BlockPos pos, BlockState state, float depth, int pass)
	{
		return pass == 1 && state.isAir();
	}

	@Override
	public Set<Direction> getGenOrder(int pass)
	{
		return this.dirs;
	}

	@Override
	public int getPasses()
	{
		return 2;
	}

	public boolean getSpelChance(float depth, float baseCh, RandomSource rand)
	{
		return (float) rand.nextInt(100) < baseCh + depth * 3f;
	}

	public void genRandSpel(WorldGenLevel world, BlockPos pos, BlockState state, float depth, RandomSource rand)
	{
		if(SubWildConfig.GENERATE_SPELEOTHEMS.get())
			this.genSpel(world, pos, state, 1 + rand.nextInt(2) + rand.nextInt((int) (depth * 10f) + 1));
	}

	public void genSlope(WorldGenLevel world, BlockPos pos, Direction wallDir, RandomSource rand)
	{
		BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos().set(pos);
		Block wall = world.getBlockState(mutPos.set(pos).move(wallDir)).getBlock();
		final boolean isDown = this.isNatural(world, mutPos.set(pos).move(0, -1, 0), world.getBlockState(mutPos));
		final boolean isUp = this.isNatural(world, mutPos.set(pos).move(0, 1, 0), world.getBlockState(mutPos));
		if(!isDown && !isUp)
			return;
		mutPos.set(pos);
		int air = 0;
		Direction oppDir = wallDir.getOpposite();
		while(air < 16 && !world.getBlockState(mutPos.move(oppDir)).isFaceSturdy(world, mutPos, wallDir))
			++air;
		int chance = SubWildConfig.SLOPE_CHANCE.get();
		if(air <= SubWildConfig.SLOPE_THRESHOLD.get())
			chance = SubWildConfig.SLOPE_THRESHOLD_CHANCE.get();
		if(rand.nextInt(10) >= chance)
			return;
		if(SubWildConfig.GENERATE_STAIRS.get() && rand.nextInt(5) <= 2)
			this.genBlock(world, pos, SubWildUtil.waterlog(SubWildLookups.STAIRS.getOrDefault(wall, this.defStairs.get()).defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, wallDir).setValue(BlockStateProperties.HALF, isDown ? Half.BOTTOM : Half.TOP), world, pos));
		else if(SubWildConfig.GENERATE_SLABS.get())
			this.genBlock(world, pos, SubWildUtil.waterlog(SubWildLookups.SLABS.getOrDefault(wall, this.defSlab.get()).defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, isDown ? SlabType.BOTTOM : SlabType.TOP), world, pos));
	}
}
