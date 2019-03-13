package mcjty.rftools.blocks.security;

import net.minecraftforge.common.config.Configuration;

public class SecurityConfiguration {

    public static final String CATEGORY_SECURITY = "security";
    public static boolean enabled = true;

    public static void init(Configuration cfg) {
        cfg.addCustomCategoryComment(SecurityConfiguration.CATEGORY_SECURITY, "Settings for the block security system");
        enabled = cfg.get(CATEGORY_SECURITY, "enabled", enabled, "Whether anything related to the block security system should exist").getBoolean();
    }
}
