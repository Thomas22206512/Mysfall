package net.renard.mysfall;

import net.fabricmc.api.ModInitializer;

import net.renard.mysfall.block.MysfallBlocks;
import net.renard.mysfall.datagen.MysfallMapDecorationTypes;
import net.renard.mysfall.datagen.MysfallStrutureTags;
import net.renard.mysfall.item.MysfallItemGroups;
import net.renard.mysfall.item.MysfallItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mysfall implements ModInitializer {
	public static final String MOD_ID = "mysfall";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MysfallItemGroups.registerItemGroups();
		MysfallBlocks.registerModBlocks();
		MysfallItems.registerMysfallItems();
		MysfallMapDecorationTypes.registerMapDecorationTypes();
		MysfallStrutureTags.registerStructuresTags();
		LOGGER.info("Mysfall onInitialize");
	}
}