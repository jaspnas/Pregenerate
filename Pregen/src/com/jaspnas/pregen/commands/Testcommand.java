package com.jaspnas.pregen.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jaspnas.pregen.Main;

public class Testcommand implements CommandExecutor {

	private Main plugin;

	public Testcommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("pregen.debug")) {
			return true;
		}

		if (plugin.getConfig().getString("debug") == "true") {
			return true;
		} else {
			sender.sendMessage("Enable debug in config to use this");
			return true;
		}

		// return false;
	}

}
