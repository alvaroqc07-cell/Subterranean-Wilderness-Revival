package melonslise.subwild.client.event;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import melonslise.subwild.SubWild;
import melonslise.subwild.client.model.BrightnessBakedModel;
import melonslise.subwild.common.init.SubWildBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SubWild.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SubWildClientModEvents
{
	public static final List<Pair<ModelResourceLocation, Function<BakedModel, BakedModel>>> MODEL_OVERRIDES = Lists.newArrayList();

	@SubscribeEvent
	public static void onSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> {
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.SHORT_FOXFIRE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.LONG_FOXFIRE.get(), RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_PATCH.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.WATER_PUDDLE.get(), RenderType.translucent());

			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_STONE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_GRANITE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_DIORITE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_ANDESITE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_SANDSTONE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_RED_SANDSTONE_SPELEOTHEM.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.FROZEN_OBSIDIAN_SPELEOTHEM.get(), RenderType.translucent());

			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICICLE.get(), RenderType.translucent());

			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_STONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_GRANITE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_DIORITE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_ANDESITE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_SANDSTONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_SMOOTH_SANDSTONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_RED_SANDSTONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_SMOOTH_RED_SANDSTONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_OBSIDIAN.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_BLACKSTONE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.MOLTEN_BASALT.get(), RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_COAL_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_IRON_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_GOLD_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_LAPIS_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_REDSTONE_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_DIAMOND_ORE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(SubWildBlocks.ICE_EMERALD_ORE.get(), RenderType.translucent());

			addBlockOverride(SubWildBlocks.SHORT_FOXFIRE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("glowing")));
			addBlockOverride(SubWildBlocks.LONG_FOXFIRE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("glowing")));

			addFullOverride(SubWildBlocks.MOLTEN_STONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_GRANITE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_DIORITE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_ANDESITE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_SANDSTONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_SMOOTH_SANDSTONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_RED_SANDSTONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_SMOOTH_RED_SANDSTONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_OBSIDIAN.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_BLACKSTONE.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
			addFullOverride(SubWildBlocks.MOLTEN_BASALT.get(), model -> new BrightnessBakedModel(model, tex -> tex.getPath().contains("lava")));
		});
	}

	// 1.20.1: ColorHandlerEvent.Block → RegisterColorHandlersEvent.Block
	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event)
	{
		event.register((state, world, pos, layer) ->
			world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : -1,
			SubWildBlocks.WATER_PUDDLE.get());
	}

	// 1.20.1: ColorHandlerEvent.Item → RegisterColorHandlersEvent.Item
	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event)
	{
		event.register((stack, layer) -> 0x3F76E4, SubWildBlocks.WATER_PUDDLE.get());
	}

	// 1.20.1: ModelBakeEvent → ModelEvent.ModifyBakingResult
	@SubscribeEvent
	public static void onModelBake(ModelEvent.ModifyBakingResult event)
	{
		Map<ResourceLocation, BakedModel> registry = event.getModels();
		for(Pair<ModelResourceLocation, Function<BakedModel, BakedModel>> override : MODEL_OVERRIDES)
		{
			BakedModel model = registry.get(override.getLeft());
			if(model != null)
				registry.put(override.getLeft(), override.getRight().apply(model));
		}
	}

	public static void addBlockOverride(Block block, Function<BakedModel, BakedModel> wrap)
	{
		for(BlockState state : block.getStateDefinition().getPossibleStates())
			MODEL_OVERRIDES.add(Pair.of(BlockModelShaper.stateToModelLocation(state), wrap));
	}

	public static void addItemOverride(ItemLike item, Function<BakedModel, BakedModel> wrap)
	{
		ResourceLocation rl = ForgeRegistries.ITEMS.getKey(item.asItem());
		if(rl != null)
			MODEL_OVERRIDES.add(Pair.of(new ModelResourceLocation(rl, "inventory"), wrap));
	}

	public static void addFullOverride(Block block, Function<BakedModel, BakedModel> wrap)
	{
		addBlockOverride(block, wrap);
		addItemOverride(block, wrap);
	}
}
