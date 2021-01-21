package de.ellpeck.miningtweaks;

import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;

import java.lang.reflect.Field;

public class Util {

	public static Field harvestLevel;
	public static Field harvestTool;

	static {
		try {
			harvestLevel = Block.class.getDeclaredField("harvestLevel");
			harvestLevel.setAccessible(true);

			harvestTool = Block.class.getDeclaredField("harvestTool");
			harvestTool.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException("oh no", e);
		}
	}

	public static void setHarvestLevel(final Block b, int level) {
		try {
			final int prevLevel = harvestLevel.getInt(b);
			harvestLevel.setInt(b, level);
			MiningTweaks.UNDO.push(()->{
				try {
					harvestLevel.setInt(b, prevLevel);
				} catch(ReflectiveOperationException e) {
					throw new RuntimeException("oh no", e);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("oh no", e);
		}
	}

	public static void setHarvestTool(final Block b, ToolType tool) {
		try {
			final Object prevTool = harvestTool.get(b);
			harvestTool.set(b, tool);
			MiningTweaks.UNDO.push(()->{
				try {
					harvestTool.set(b, prevTool);
				} catch(ReflectiveOperationException e) {
					throw new RuntimeException("oh no", e);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("oh no", e);
		}
	}
}
