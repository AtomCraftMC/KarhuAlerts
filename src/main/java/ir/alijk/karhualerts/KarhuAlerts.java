package ir.alijk.karhualerts;

import ir.alijk.karhualerts.config.Config;
import ir.alijk.karhualerts.listeners.KarhuHandler;
import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.utils.Common;
import me.liwk.karhu.api.KarhuAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public final class KarhuAlerts extends MegaPlugin {
    private String ALERT_PERMISSION = "karhu.alerts";
    private String HOVER_DEBUG_PERMISSION = "karhu.hover-debug";

    @Override
    public void onPluginEnable() {
        getConfigManager().register(Config.class);
        KarhuAPI.getEventRegistry().addListener(new KarhuHandler());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getBungeeApi().registerForwardListener("karhu:alerts", (channelName, player, bytes) -> {
            String[] data = (new String(bytes)).split(",,,");

            if (data[0].equalsIgnoreCase(Config.SERVER_NAME)) return;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(ALERT_PERMISSION)) {
                    TextComponent message = new TextComponent(data[3]);
                    message.setClickEvent(
                            new ClickEvent(
                                    ClickEvent.Action.RUN_COMMAND,
                                    Config.ALERT_CLICK_COMMAND
                                            .replace("%player%", data[1])
                            )
                    );
                    message.setHoverEvent(
                            new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder(
                                            p.hasPermission(HOVER_DEBUG_PERMISSION)
                                                    ? Common.colorize(data[2])
                                                    : Common.colorize(Config.ALERT_HOVER_MESSAGE)
                                    ).create()
                            )
                    );
                    p.spigot().sendMessage(message);
                }
            }
        });
    }

    @Override
    public void onPluginDisable() {}
}
