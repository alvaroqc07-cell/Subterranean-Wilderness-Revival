        package melonslise.subwild.common.world.gen.feature;

        import java.util.HashSet;
        import java.util.Set;
        import java.util.stream.Stream;

        import com.mojang.serialization.Codec;

        import melonslise.subwild.common.config.SubWildConfig;
        import melonslise.subwild.common.init.SubWildCapabilities;
        import melonslise.subwild.common.init.SubWildPlacements;
        import net.minecraft.core.BlockPos;
        import net.minecraft.util.RandomSource;
        import net.minecraft.world.level.ChunkPos;
        import net.minecraft.world.level.Level;
        import net.minecraft.world.level.block.Blocks;
        import net.minecraft.world.level.chunk.ChunkAccess;
        import net.minecraft.world.level.chunk.ProtoChunk;
        import net.minecraft.world.level.levelgen.CarvingMask;
        import net.minecraft.world.level.levelgen.GenerationStep;
        import net.minecraft.world.level.levelgen.Heightmap;
        import net.minecraft.world.level.levelgen.placement.PlacementContext;
        import net.minecraft.world.level.levelgen.placement.PlacementModifier;
        import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

        public class CavePlacement extends PlacementModifier
        {
            public static final CavePlacement INSTANCE = new CavePlacement();
            public static final Codec<CavePlacement> CODEC = Codec.unit(INSTANCE);

            @Override
            public PlacementModifierType<?> type()
            {
                return SubWildPlacements.CAVE_PLACEMENT_TYPE.get();
            }

            @Override
            public Stream<BlockPos> getPositions(PlacementContext context, RandomSource rand, BlockPos pos)
            {
                Level world = context.getLevel().getLevel();
                if (!SubWildConfig.isAllowed(world) || !world.getCapability(SubWildCapabilities.NOISE_CAPABILITY).isPresent())
                    return Stream.empty();

                Set<BlockPos> set = new HashSet<>(1024);
                ChunkAccess chunk = context.getLevel().getChunk(pos);
                ChunkPos chPos = chunk.getPos();

                if (SubWildConfig.EXPENSIVE_SCAN.get())
                {
                    BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
                    for (int x = 0; x < 16; ++x)
                        for (int z = 0; z < 16; ++z)
                            for (int y = context.getLevel().getMinBuildHeight(),
                                yMax = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z); y < yMax; ++y)
                                if (chunk.getBlockState(mut.set(x, y, z)).getBlock() == Blocks.CAVE_AIR)
                                    set.add(mut.offset(chPos.getMinBlockX(), 0, chPos.getMinBlockZ()).immutable());
                }

                // 1.20.1: CarvingMask.stream(ChunkPos, minY) en lugar de iterar BitSet
                CarvingMask mask = ((ProtoChunk) chunk).getOrCreateCarvingMask(GenerationStep.Carving.AIR);
                mask.stream(chPos, context.getLevel().getMinBuildHeight()).forEach(set::add);

                return set.stream();
            }
        }
