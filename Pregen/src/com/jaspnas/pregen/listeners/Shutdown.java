package com.jaspnas.pregen.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Shutdown implements Listener {

	private Main plugin;

	public Shutdown(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent e) {

		if (plugin.getConfig().getString("stop-on-disconnect") == "true" && Globals.ispregenerating == true) {
			Bukkit.getServer().shutdown();
		}
	}
}
