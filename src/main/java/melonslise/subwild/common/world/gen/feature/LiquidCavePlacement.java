package melonslise.subwild.common.world.gen.feature;

import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import melonslise.subwild.common.config.SubWildConfig;
import melonslise.subwild.common.init.SubWildCapabilities;
import melonslise.subwild.common.init.SubWildPlacements;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.CarvingMask;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class LiquidCavePlacement extends PlacementModifier
{
	public static final LiquidCavePlacement INSTANCE = new LiquidCavePlacement();
	public static final Codec<LiquidCavePlacement> CODEC = Codec.unit(INSTANCE);

	@Override
	public PlacementModifierType<?> type()
	{
		return SubWildPlacements.LIQUID_CAVE_PLACEMENT_TYPE.get();
	}

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource rand, BlockPos pos)
	{
		Level world = context.getLevel().getLevel();
		if (!SubWildConfig.isAllowed(world) || !world.getCapability(SubWildCapabilities.NOISE_CAPABILITY).isPresent())
			return Stream.empty();

		ChunkAccess chunk = context.getLevel().getChunk(pos);
		ChunkPos chPos = chunk.getPos();
		// 1.20.1: CarvingMask.stream(ChunkPos, minY) en lugar de BitSet
		CarvingMask mask = ((ProtoChunk) chunk).getOrCreateCarvingMask(GenerationStep.Carving.LIQUID);
		return mask.stream(chPos, context.getLevel().getMinBuildHeight());
	}
}
