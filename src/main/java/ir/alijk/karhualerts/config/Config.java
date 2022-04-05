package ir.alijk.karhualerts.config;

import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.config.Configurable;

public class Config extends Configurable {
    public static String SERVER_NAME, ALERT_FORMAT;

    public Config(MegaPlugin instance) {
        super(instance, "config.yml");
    }

    @Override
    public void init() {
        SERVER_NAME = getConfig().getString("server-name");
        ALERT_FORMAT = getConfig().getString("alert-format");
    }
}
