package com.jaspnas.pregen.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Cancel implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;

	public Cancel(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Globals.canpregen = false;

		sender.sendMessage(ChatColor.GOLD + "All tasks cancelled");

		return true;
	}

}
