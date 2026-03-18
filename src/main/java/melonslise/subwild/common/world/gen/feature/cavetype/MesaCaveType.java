package melonslise.subwild.common.world.gen.feature.cavetype;

import java.util.Random;

import melonslise.subwild.common.capability.INoise;
import melonslise.subwild.common.config.SubWildConfig;
import melonslise.subwild.common.init.SubWildBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MesaCaveType extends BasicCaveType
{
	// 1.20.1: BadlandsSurfaceBuilder eliminado — usamos una tabla de terracota manual
	private static final Block[] TERRACOTTA_BANDS = new Block[] {
		Blocks.TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA,
		Blocks.BROWN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.WHITE_TERRACOTTA,
		Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.TERRACOTTA, Blocks.ORANGE_TERRACOTTA
	};

	public MesaCaveType(String domain, String path)
	{
		super(domain, path);
		this.floorCh = 0f;
		this.defSpel = SubWildBlocks.RED_SANDSTONE_SPELEOTHEM;
		this.defStairs = () -> Blocks.RED_SANDSTONE_STAIRS;
		this.defSlab = () -> Blocks.RED_SANDSTONE_SLAB;
	}

	@Override
	public void genFloor(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 0)
		{
			final double d = this.getNoise(noise, pos, 0.125d);
			if(d > 0.2d)
				this.replaceBlock(world, pos, Blocks.RED_SAND.defaultBlockState());
			else
				this.genTerracotta(world, pos);
		}
		super.genFloor(world, noise, pos, depth, pass, rand);
	}

	@Override
	public void genFloorExtra(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 1)
		{
			final double d = this.getNoise(noise, pos, 0.1d);
			if(SubWildConfig.GENERATE_PATCHES.get() && d > -0.5d && d < 0.5d)
				this.genLayer(world, pos, SubWildBlocks.RED_SAND_PATCH.get().defaultBlockState(), d, -0.5d, 0.5d, 5);
			else if(SubWildConfig.GENERATE_DEAD_BUSHES.get() && rand.nextFloat() < (SubWildConfig.MESA_DEAD_BUSHES_CHANCE.get().floatValue() / 100))
				this.genBlock(world, pos, Blocks.DEAD_BUSH.defaultBlockState());
		}
		super.genFloorExtra(world, noise, pos, depth, pass, rand);
	}

	@Override
	public void genCeil(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 0)
			this.genTerracotta(world, pos);
		super.genCeil(world, noise, pos, depth, pass, rand);
	}

	@Override
	public void genWall(WorldGenLevel world, INoise noise, BlockPos pos, float depth, int pass, RandomSource rand)
	{
		if(pass == 0)
			this.genTerracotta(world, pos);
		super.genWall(world, noise, pos, depth, pass, rand);
	}

	@Override
	public void genWallExtra(WorldGenLevel world, INoise noise, BlockPos pos, Direction wallDir, float depth, int pass, RandomSource rand) {}

	public void genTerracotta(WorldGenLevel world, BlockPos pos)
	{
		// Usa ruido basado en Y para simular las bandas de terracota del badlands
		int band = Math.floorMod(pos.getY(), TERRACOTTA_BANDS.length);
		this.replaceBlock(world, pos, TERRACOTTA_BANDS[band].defaultBlockState());
	}
}
