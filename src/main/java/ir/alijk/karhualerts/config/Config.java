package ir.alijk.karhualerts.config;

import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.config.Configurable;

public class Config extends Configurable {
    public static String SERVER_NAME, ALERT_FORMAT, BAN_FORMAT, ALERT_HOVER_MESSAGE, ALERT_CLICK_COMMAND;
    public static boolean SEND_ALERTS;
    public static int ALERT_DELAY;

    public Config(MegaPlugin instance) {
        super(instance, "config.yml");
    }

    @Override
    public void init() {
        SEND_ALERTS = getConfig().getBoolean("send-alerts");
        SERVER_NAME = getConfig().getString("server-name");
        ALERT_FORMAT = getConfig().getString("alert-format");
        ALERT_DELAY = getConfig().getInt("alert-delay");
        BAN_FORMAT = getConfig().getString("ban-format");
        ALERT_HOVER_MESSAGE = getConfig().getString("alert-hover-message");
        ALERT_CLICK_COMMAND = getConfig().getString("alert-click-command");
    }
}
