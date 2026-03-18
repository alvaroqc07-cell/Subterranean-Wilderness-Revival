package melonslise.subwild.common.init;

import melonslise.subwild.SubWild;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class SubWildTags
{
	public static final TagKey<Block>
		FOXFIRE    = blockTag(SubWild.ID, "foxfire"),
		SPELEOTHEMS = blockTag(SubWild.ID, "speleothems"),
		TERRACOTTA = blockTag("forge", "terracotta");

	public static final TagKey<Item>
		COAL_ORES     = itemTag(SubWild.ID, "coal_ores"),
		IRON_ORES     = itemTag(SubWild.ID, "iron_ores"),
		GOLD_ORES     = itemTag(SubWild.ID, "gold_ores"),
		LAPIS_ORES    = itemTag(SubWild.ID, "lapis_ores"),
		REDSTONE_ORES = itemTag(SubWild.ID, "redstone_ores"),
		DIAMOND_ORES  = itemTag(SubWild.ID, "diamond_ores"),
		EMERALD_ORES  = itemTag(SubWild.ID, "emerald_ores");

	private static TagKey<Block> blockTag(String namespace, String path)
	{
		return TagKey.create(Registries.BLOCK, new ResourceLocation(namespace, path));
	}

	private static TagKey<Item> itemTag(String namespace, String path)
	{
		return TagKey.create(Registries.ITEM, new ResourceLocation(namespace, path));
	}
}
