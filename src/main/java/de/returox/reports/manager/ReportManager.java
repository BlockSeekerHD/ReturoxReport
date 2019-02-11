package de.returox.reports.manager;

import com.sun.xml.internal.rngom.parse.host.Base;
import de.returox.reports.Reports;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ReportManager {


    private final Reports reports;

    public ReportManager(Reports reports) {
        this.reports = reports;
        ProxyServer.getInstance().getScheduler().schedule(reports, new Runnable() {
            @Override
            public void run() {
                broadcastReportReminder();
            }
        }, 0, 3, TimeUnit.MINUTES);
    }

    public static ArrayList<ProxiedPlayer> loggedTeamMembers = new ArrayList<>();
    public static HashMap<ProxiedPlayer, Reason> pendingReports = new HashMap<>();

    public static HashMap<ProxiedPlayer, Reason> getPendingReports() {
        return pendingReports;
    }

    public static void openReport(ProxiedPlayer invoker, ProxiedPlayer target, Reason reason) {
        pendingReports.put(target, reason);
        invoker.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Dein §eReport §7gegen " + target.getDisplayName() + "§7 mit dem Grund " + reason.name() + " wurde erstellt!"));
    }

    public static void loginTeamMember(ProxiedPlayer proxiedPlayer) {
        if (loggedTeamMembers.contains(proxiedPlayer)) {
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Du bist bereits §ceingeloggt§7!"));
        } else {
            loggedTeamMembers.add(proxiedPlayer);
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Du wurdest erfolgreich §aeingeloggt§7!"));
        }
    }

    public static void logoutTeamMember(ProxiedPlayer proxiedPlayer) {
        if (!loggedTeamMembers.contains(proxiedPlayer)) {
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Du bist nicht §ceingeloggt§7!"));
        } else {
            loggedTeamMembers.remove(proxiedPlayer);
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Du wurdest erfolgreich §aausgeloggt§7!"));
        }
    }

    public static void broadcastReportReminder() {
        TextComponent textComponentFront = new TextComponent(Reports.getPrefix() + "§7Es sind noch unbearbeitete §eReports §7vorhanden! Klicke ");
        TextComponent textComponentClickable = new TextComponent("§ehier");
        TextComponent textComponentEnd = new TextComponent("§7, um sie anzuzeigen.");
        textComponentClickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke, um die §eReports §7anzuzeigen!").create()));
        textComponentClickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "reports"));
        textComponentClickable.addExtra(textComponentEnd);
        textComponentFront.addExtra(textComponentClickable);
        loggedTeamMembers.forEach(all -> {
            all.sendMessage(textComponentFront);
        });
    }

    public static void broadcastNewReport(ProxiedPlayer invoker, ProxiedPlayer target, Reason reason) {
        loggedTeamMembers.forEach(all -> {
            all.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Es wurde ein neuer §eReport §7erstellt! Es handelt sich dabei um den/die Spieler/-in §e" + target.getName() + "§7. Er/Sie wurde von §e" + invoker.getName() + " §7reportet. Der Grund lautet §e" + reason.name() + "§7."));
            all.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + ""));
        });
    }

    public enum Reason {
        Hacking,
        Teaming,
        Boosting,
        Skin,
        Chatverhalten,
        Sonstiges;
    }

}
