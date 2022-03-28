package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DyeableFishingLines implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("dyeable_fishing_lines");

	@Override
	public void onInitialize() {
		LOGGER.info("Moms Spaghetti");
	}
}
