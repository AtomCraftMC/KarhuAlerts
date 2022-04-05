package ir.alijk.karhualerts.listeners;

import ir.alijk.karhualerts.KarhuAlerts;
import ir.alijk.karhualerts.config.Config;
import ir.jeykey.megacore.utils.Common;
import me.liwk.karhu.api.event.KarhuEvent;
import me.liwk.karhu.api.event.KarhuListener;
import me.liwk.karhu.api.event.impl.KarhuAlertEvent;
import me.liwk.karhu.api.event.impl.KarhuBanEvent;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class KarhuHandler implements KarhuListener {
    private static Method getHandleMethod;
    private static Field pingField;

    @Override
    public void onEvent(KarhuEvent event) {
        String formatted = null, playerName = null;

        if(event instanceof KarhuAlertEvent){
            KarhuAlertEvent alertEvent = (KarhuAlertEvent) event;

            Player player = alertEvent.getPlayer();
            playerName = player.getName();
            String checkName = alertEvent.getCheck().getName();
            int violation = alertEvent.getViolations();
            int ping = -1;

            formatted = Config.ALERT_FORMAT
                                    .replace("%server%", Config.SERVER_NAME)
                                    .replace("%player%", playerName)
                                    .replace("%check%", checkName)
                                    .replace("%vl%", Integer.toString(violation))
                                    .replace("%ping%", Integer.toString(ping));
        } else if (event instanceof KarhuBanEvent) {
            KarhuBanEvent banEvent = (KarhuBanEvent) event;

            Player player = banEvent.getPlayer();
            playerName = player.getName();
            String checkName = banEvent.getCheck().getName();
            int ping = -1;

            formatted = Config.BAN_FORMAT
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", playerName)
                    .replace("%check%", checkName)
                    .replace("%ping%", Integer.toString(ping));

        }

        if (formatted != null) alert(formatted, playerName);
    }

    private void alert(String message, String playerName) {
        KarhuAlerts.getBungeeApi().forward(
                "ALL",
                "karhu:alerts",
                (Config.SERVER_NAME + "," + playerName + "," + Common.colorize(message)).getBytes()
        );
    }
}
