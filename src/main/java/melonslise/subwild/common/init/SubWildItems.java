package melonslise.subwild.common.init;

import melonslise.subwild.SubWild;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SubWildItems
{
	public static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create(ForgeRegistries.ITEMS, SubWild.ID);

	// 1.20.1: CreativeModeTab ahora se registra con DeferredRegister
	public static final DeferredRegister<CreativeModeTab> TABS =
		DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SubWild.ID);

	public static final RegistryObject<CreativeModeTab> TAB = TABS.register(SubWild.ID,
		() -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup." + SubWild.ID))
			.icon(() -> new ItemStack(SubWildBlocks.LONG_FOXFIRE.get()))
			.displayItems((params, output) -> {
				ITEMS.getEntries().forEach(ro -> output.accept(ro.get()));
			})
			.build());

	public static void register()
	{
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static RegistryObject<Item> add(String name, Item item)
	{
		return ITEMS.register(name, () -> item);
	}
}
