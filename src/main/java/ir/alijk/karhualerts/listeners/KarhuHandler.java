package ir.alijk.karhualerts.listeners;

import ir.alijk.karhualerts.KarhuAlerts;
import ir.alijk.karhualerts.config.Config;
import ir.jeykey.megacore.utils.Common;
import me.liwk.karhu.api.event.KarhuEvent;
import me.liwk.karhu.api.event.KarhuListener;
import me.liwk.karhu.api.event.impl.KarhuAlertEvent;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class KarhuHandler implements KarhuListener {
    private static Method getHandleMethod;
    private static Field pingField;

    @Override
    public void onEvent(KarhuEvent event) {
        if(event instanceof KarhuAlertEvent){
            KarhuAlertEvent alertEvent = (KarhuAlertEvent) event;

            Player player = alertEvent.getPlayer();
            String playerName = player.getName();
            String checkName = alertEvent.getCheck().getName();
            int violation = alertEvent.getViolations();
            int ping = -1;

            String formatted =
                    Common.colorize(
                            Config.ALERT_FORMAT
                                .replace("%server%", Config.SERVER_NAME)
                                .replace("%player%", playerName)
                                .replace("%check%", checkName)
                                .replace("%vl%", Integer.toString(violation))
                                .replace("%ping%", Integer.toString(ping))
                    );

            KarhuAlerts.getBungeeApi().forward("ALL", "karhu:alerts", (Config.SERVER_NAME + "," + formatted).getBytes());
        }
    }
}
