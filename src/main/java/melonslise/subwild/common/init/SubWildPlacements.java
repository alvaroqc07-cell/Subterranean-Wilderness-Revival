package melonslise.subwild.common.init;

import melonslise.subwild.SubWild;
import melonslise.subwild.common.world.gen.feature.CavePlacement;
import melonslise.subwild.common.world.gen.feature.LiquidCavePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class SubWildPlacements
{
	// En 1.20.1 PlacementModifierType se registra en el registro de Vanilla
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_TYPES =
		DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, SubWild.ID);

	public static final RegistryObject<PlacementModifierType<CavePlacement>>
		CAVE_PLACEMENT_TYPE = PLACEMENT_TYPES.register("cave", () -> () -> CavePlacement.CODEC);

	public static final RegistryObject<PlacementModifierType<LiquidCavePlacement>>
		LIQUID_CAVE_PLACEMENT_TYPE = PLACEMENT_TYPES.register("liquid_cave", () -> () -> LiquidCavePlacement.CODEC);

	public static void register()
	{
		PLACEMENT_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
