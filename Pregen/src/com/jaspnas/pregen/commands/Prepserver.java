package com.jaspnas.pregen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Prepserver implements CommandExecutor {

	private Main plugin;

	public Prepserver(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "You need to run this as console!");
			return true;
		}

		for (Player target : Bukkit.getServer().getOnlinePlayers()) {
			if (!(target.hasPermission("pregen.pregen.bypass"))
					&& !(plugin.getConfig().getString("exclude_pregen.pregen.bypass") == "true")) {
				target.kickPlayer("Server is in pregen mode.");
			}
		}

		sender.sendMessage(ChatColor.BLUE + "All players kicked. Reloading the server.");

		Globals.canpregen = true;

		Bukkit.getServer().reload();

		Globals.ispregenerating = true;

		sender.sendMessage("Do /cancel if you do not run /pregen or /legacypregen.");

		return false;
	}

}