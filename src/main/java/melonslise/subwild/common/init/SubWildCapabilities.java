package melonslise.subwild.common.init;

import melonslise.subwild.common.capability.CapabilityProvider;
import melonslise.subwild.common.capability.INoise;
import melonslise.subwild.common.capability.OpenSimplex2FNoise;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public final class SubWildCapabilities
{
	// 1.20.1: @CapabilityInject eliminado → usar CapabilityManager.get()
	public static final Capability<INoise> NOISE_CAPABILITY =
		CapabilityManager.get(new CapabilityToken<>() {});

	public static void attachToWorld(AttachCapabilitiesEvent<Level> event)
	{
		if(event.getObject() instanceof WorldGenLevel worldGenLevel)
			event.addCapability(OpenSimplex2FNoise.ID, new CapabilityProvider(NOISE_CAPABILITY, new OpenSimplex2FNoise(worldGenLevel)));
	}
}
