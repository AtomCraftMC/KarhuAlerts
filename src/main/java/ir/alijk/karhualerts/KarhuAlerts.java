package ir.alijk.karhualerts;

import ir.alijk.karhualerts.config.Config;
import ir.alijk.karhualerts.listeners.KarhuHandler;
import ir.jeykey.megacore.MegaPlugin;
import me.liwk.karhu.api.KarhuAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public final class KarhuAlerts extends MegaPlugin {
    private String ALERT_PERMISSION = "karhu.alerts";

    @Override
    public void onPluginEnable() {
        getConfigManager().register(Config.class);
        KarhuAPI.getEventRegistry().addListener(new KarhuHandler());

        getBungeeApi().registerForwardListener("karhu:alerts", (channelName, player, bytes) -> {
            String[] data = (new String(bytes)).split(",");

            if (data[0].equalsIgnoreCase(Config.SERVER_NAME)) return;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(ALERT_PERMISSION)) {
                    p.sendMessage(data[1]);
                }
            }
        });

    }

    @Override
    public void onPluginDisable() {

    }
}
