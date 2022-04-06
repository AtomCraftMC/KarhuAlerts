package ir.alijk.karhualerts.listeners;

import ir.alijk.karhualerts.KarhuAlerts;
import ir.alijk.karhualerts.config.Config;
import ir.jeykey.megacore.utils.Common;
import me.clip.placeholderapi.PlaceholderAPI;
import me.liwk.karhu.api.event.KarhuEvent;
import me.liwk.karhu.api.event.KarhuListener;
import me.liwk.karhu.api.event.impl.KarhuAlertEvent;
import me.liwk.karhu.api.event.impl.KarhuBanEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class KarhuHandler implements KarhuListener {
    private static Method getHandleMethod;
    private static Field pingField;
    private long delay = 0;

    @Override
    public void onEvent(KarhuEvent event) {
        String formatted = null, playerName = null;
        Player player = null;
        String hover = null;

        if(event instanceof KarhuAlertEvent){
            if (!(Config.SEND_ALERTS)) return;
            if (!(System.currentTimeMillis() - delay >= Config.ALERT_DELAY)) return;

            KarhuAlertEvent alertEvent = (KarhuAlertEvent) event;

            player = alertEvent.getPlayer();
            playerName = player.getName();
            String checkName = alertEvent.getCheck().getName();
            if (alertEvent.getCheck().isExperimental()) checkName = checkName + "&aΔ";

            int violation = alertEvent.getViolations();

            formatted = Config.ALERT_FORMAT
                                    .replace("%server%", Config.SERVER_NAME)
                                    .replace("%player%", playerName)
                                    .replace("%check%", checkName)
                                    .replace("%vl%", Integer.toString(violation));
            hover = "&7Info:\n&4" + alertEvent.getDebug().getDebug() + "\n&8[&bPing: &40 &bTPS: &420.0&8]";

            delay = System.currentTimeMillis();
        } else if (event instanceof KarhuBanEvent) {
            KarhuBanEvent banEvent = (KarhuBanEvent) event;

            player = banEvent.getPlayer();
            playerName = player.getName();
            String checkName = banEvent.getCheck().getName();
            if (banEvent.getCheck().isExperimental()) checkName = checkName + "&aΔ";

            formatted = Config.BAN_FORMAT
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", playerName)
                    .replace("%check%", checkName);

        }

        if (formatted != null) {
            alert(
                    PlaceholderAPI.setPlaceholders(player, formatted),
                    hover,
                    playerName
            );
        }
    }

    private void alert(String message, String hoverMessage, String playerName) {
        KarhuAlerts.getBungeeApi().forward(
                "ALL",
                "karhu:alerts",
                (
                        Config.SERVER_NAME +
                                ",,," + playerName +
                                ",,," + hoverMessage +
                                ",,," + Common.colorize(message)
                ).getBytes()
        );
    }
}
