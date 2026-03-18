package melonslise.subwild.client.event;

import java.util.List;
import java.util.ListIterator;

import melonslise.subwild.SubWild;
import melonslise.subwild.common.world.gen.feature.CaveDecoFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SubWild.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SubWildClientForgeEvents
{
	// 1.20.1: RenderGameOverlayEvent.Text → CustomizeGuiOverlayEvent.DebugText
	// CustomizeGuiOverlayEvent.DebugText provee directamente las listas de texto del F3
	@SubscribeEvent
	public static void onRenderDebugText(CustomizeGuiOverlayEvent.DebugText event)
	{
		Minecraft mc = Minecraft.getInstance();
		if(!mc.options.renderDebug || mc.showOnlyReducedInfo() || mc.player == null)
			return;

		try
		{
			BlockPos pos = mc.player.blockPosition();
			float depth = CaveDecoFeature.depthAt(mc.level, pos);

			List<String> left = event.getLeft();
			ListIterator<String> it = left.listIterator();
			while(it.hasNext())
				if(it.next().startsWith("Biome:"))
				{
					it.add("Cave depth: " + (depth >= 0f ? String.format("%.2f", depth) : "surface"));
					break;
				}
		}
		catch(final Exception e)
		{
			SubWild.LOGGER.error("SubWild: Error rendering debug text", e);
		}
	}
}
