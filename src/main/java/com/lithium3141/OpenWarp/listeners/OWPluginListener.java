package com.lithium3141.OpenWarp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.lithium3141.OpenWarp.util.MVConnector;

/**
 * Plugin listener for OpenWarp. Listens for plugin enable events
 * in order to connect to Multiverse 2; in turn, this allows Multiverse
 * users to access OpenWarp warps via Multiverse commands.
 */
public class OWPluginListener implements Listener {
    @EventHandler()
    public void pluginEnable(PluginEnableEvent event) {
        Plugin p = event.getPlugin();
        if (p.getDescription().getName().equalsIgnoreCase("Multiverse-Core")) {
            new MVConnector(p);
            System.out.println("[OpenWarp] Found Multiverse 2, Support Enabled.");
        }
    }
}
