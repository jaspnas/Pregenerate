package com.jaspnas.pregen.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Join implements Listener {

	private Main plugin;

	public Join(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {

		if (plugin.getConfig().getString("no-players") == "true" && Globals.ispregenerating == true) {
			Player p = e.getPlayer();
			if (!(plugin.getConfig().getString("exclude_pregen.pregen.bypass") == "true")
					&& !(p.hasPermission("pregen.pregen.bypass"))) {
				p.kickPlayer(null);
			}
		}

	}

}