package de.returox.reports;

import de.returox.reports.manager.ReportManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public final class Reports extends Plugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return "§8│ §eReport §8➟ ";
    }

    public static TextComponent getCommandSytax() {
        return new TextComponent(getPrefix() + "§7Benutze \"/report <§eSpieler§7> <§eGrund§7>\"!");
    }

    public static TextComponent getReasonsSyntax() {
        String reasons = "";
        for (ReportManager.Reason reason : ReportManager.Reason.values()) {
            reasons = reasons + reason.name() + ", ";
        }
        reasons = reasons.substring(0, reasons.length()-3);
        return new TextComponent(getPrefix() + "§7Verfügbare §eGründe§7: " + reasons);
    }

}
