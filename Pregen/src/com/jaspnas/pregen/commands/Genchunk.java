package com.jaspnas.pregen.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Genchunk implements CommandExecutor, TabCompleter {

	@SuppressWarnings("unused")
	private Main plugin;

	public Genchunk(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("pregen.pregen.use")) {

			if (!(args.length == 3)) {
				return false;
			}

			String worldname = args[0];
			// int world = Integer.parseInt(args[0]);
			int xcord = Integer.parseInt(args[1]);
			int zcord = Integer.parseInt(args[2]);

			int chunkx = xcord / 16;
			int chunkz = zcord / 16;

			if (!(Bukkit.getServer().getWorlds().contains(Bukkit.getServer().getWorld(worldname)))) {
				return false;
			}

			Globals.ispregenerating = true;

			Bukkit.getWorld(worldname).loadChunk(chunkx, chunkz, true);

			while (Bukkit.getWorld(worldname).isChunkGenerated(chunkx, chunkz) == false) {

			}

			Bukkit.getWorld(worldname).unloadChunk(chunkx, chunkz);
			sender.sendMessage(ChatColor.GREEN + "Chunk at " + xcord + ", " + zcord + " is generated!");
			Globals.ispregenerating = false;
			return true;

		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
			return true;
		}

		// return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("genchunk")) {

			List<String> autoCompletes = new ArrayList<>();

			if (args.length == 0) {
				return null;
			}

			if (args.length == 1) {

				for (World world : Bukkit.getServer().getWorlds()) {

					autoCompletes.add(world.getName());
				}

			}

			return autoCompletes;
		}

		return null;

	}

}
