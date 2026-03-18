package melonslise.subwild;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import melonslise.subwild.common.config.SubWildConfig;
import melonslise.subwild.common.init.SubWildBlocks;
import melonslise.subwild.common.init.SubWildFeatures;
import melonslise.subwild.common.init.SubWildItems;
import melonslise.subwild.common.init.SubWildPlacements;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SubWild.ID)
public class SubWild
{
	public static final String ID = "subwild";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SubWild(FMLJavaModLoadingContext context)
	{
		// 1.20.1: nuevo patrón — recibir context por constructor en lugar de ModLoadingContext.get()
		context.registerConfig(Type.COMMON, SubWildConfig.SPEC);

		IEventBus modBus = context.getModEventBus();
		SubWildBlocks.register(modBus);
		SubWildItems.register(modBus);
		SubWildPlacements.register(modBus);
		SubWildFeatures.register(modBus);
	}
}
