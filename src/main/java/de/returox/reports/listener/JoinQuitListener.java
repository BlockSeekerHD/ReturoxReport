package de.returox.reports.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();
    }

}
