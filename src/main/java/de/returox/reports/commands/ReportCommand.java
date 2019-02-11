package de.returox.reports.commands;

import de.returox.reports.Reports;
import de.returox.reports.manager.ReportManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {

    public ReportCommand() {
        super("report");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if (args.length == 0) {
                proxiedPlayer.sendMessage(Reports.getCommandSytax());
            } else if (args.length == 1) {
                proxiedPlayer.sendMessage(Reports.getReasonsSyntax());
            } else if (args.length == 2) {
                try {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    ReportManager.Reason reason = ReportManager.Reason.valueOf(args[1]);
                    if (reason == null) {
                        proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7Der angegebene §cGrund §7ist nicht in unserem System vorhanden. Bitte verwende einen vorhandenen Grund oder den Grund \"§eSonstiges\"§7!"));
                    } else {
                        ReportManager.openReport(proxiedPlayer, target, reason);
                    }
                } catch (Exception ex) {
                    proxiedPlayer.sendMessage(TextComponent.fromLegacyText(Reports.getPrefix() + "§7" + args[0] + " konnte nicht §creportet §7werden!"));
                }
            } else {
                proxiedPlayer.sendMessage(Reports.getCommandSytax());
            }
        }
    }

}
