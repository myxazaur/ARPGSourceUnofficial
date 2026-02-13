package com.vivern.arpg;

import net.minecraftforge.common.config.Config;

@SuppressWarnings("unused")
@Config(modid = Tags.MOD_ID, name = "general")
public class ARPGConfig {

    @Config.Name("general")
    @Config.Comment("General settings")
    public static final GeneralCategory general = new GeneralCategory();

    public static class GeneralCategory {
        @Config.Comment("Disable Realms")
        public boolean disableRealms = true;
    }

}
