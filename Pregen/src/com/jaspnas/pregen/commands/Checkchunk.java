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

import com.jaspnas.pregen.Main;

public class Checkchunk implements CommandExecutor, TabCompleter {

	@SuppressWarnings("unused")
	private Main plugin;

	public Checkchunk(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(args.length == 3)) {
			return false;
		}

		String worldname = args[0];
		// int worldnr = Integer.parseInt(args[0]);
		int xcord = Integer.parseInt(args[1]);
		int zcord = Integer.parseInt(args[2]);

		if (!(Bukkit.getServer().getWorlds().contains(Bukkit.getServer().getWorld(worldname)))) {
			return false;
		}

		int chunkx = xcord / 16;
		int chunkz = zcord / 16;

		if (Bukkit.getWorld(worldname).isChunkGenerated(chunkx, chunkz) == true) {
			sender.sendMessage(ChatColor.GREEN + "Chunk is generated!");
			return true;
		} else {
			sender.sendMessage(ChatColor.GOLD + "Chunk is not generated. You can try generating it with /genchunk.");
			return true;
		}

		// return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("checkchunk")) {

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
