package melonslise.subwild.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import melonslise.subwild.common.init.SubWildConfiguredFeatures;
import melonslise.subwild.common.init.SubWildPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class SubWildWorldGenProvider extends DatapackBuiltinEntriesProvider
{
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.CONFIGURED_FEATURE, SubWildConfiguredFeatures::bootstrap)
		.add(Registries.PLACED_FEATURE,     SubWildPlacedFeatures::bootstrap);

	public SubWildWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
	{
		super(output, registries, BUILDER, java.util.Set.of("subwild"));
	}

	@Override
	public String getName()
	{
		return "Subterranean Wilderness Revival - WorldGen";
	}
}
